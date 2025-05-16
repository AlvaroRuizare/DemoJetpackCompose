package com.example.apppracticasjc.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.ViewModel.ListadoViewModel
import com.example.apppracticasjc.ViewModel.ListadoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Listado() {
    val context = LocalContext.current
    val listadoViewModel : ListadoViewModel = viewModel( // ViewModel global que sobrevive a cambios de configuracion
        factory = ListadoViewModelFactory(
            BaseDatos.getDatabase(context).usuarioDao(),
            BaseDatos.getDatabase(context).tipoUsuarioDao(),
            BaseDatos.getDatabase(context).multimediaDao())
    )
    val listadoUiState by listadoViewModel.estadoPublico.collectAsState()

    LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        items(listadoUiState.listaUsuarios) { usuario ->
            // Cargar la foto de forma segura usando produceState
            val fotoUsuario by produceState(initialValue = "") {
                value = listadoViewModel.obtenerFotoUsuario(usuario.id)
            }

            val nombreTipoUsuario by produceState(initialValue = "") {
                value = listadoViewModel.obtenerNombreTipoUsuario(usuario.idTipoUsuario)
            }

            ListadoItem(
                usuarioItem = usuario,
                fotoUsuario = fotoUsuario,
                tipoUsuario = nombreTipoUsuario,
                alSeleccionar = { u, seleccionado ->
                    listadoViewModel.actualizarSeleccion(u, seleccionado)
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // margen desde el borde
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { listadoViewModel.abrirBottomSheet() },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ) {
            Icon(
                Icons.Filled.KeyboardArrowUp,
                "Mostrar menu inferior"
            )
        }
    }


    val sheetState = rememberModalBottomSheetState()

    if (listadoUiState.mostrarBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { listadoViewModel.cerrarBottomSheet() },
            sheetState = sheetState

       ) { CarouselGaleria() }
    }
}