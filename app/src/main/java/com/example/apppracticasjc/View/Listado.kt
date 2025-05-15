package com.example.apppracticasjc.View

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity
import com.example.apppracticasjc.ViewModel.ListadoViewModel
import com.example.apppracticasjc.ViewModel.ListadoViewModelFactory

@Composable
fun Listado(navController: NavHostController, navegarAUsuario: (UsuarioEntity) -> Unit) {
    val context = LocalContext.current
    val listadoViewModel : ListadoViewModel = viewModel( // ViewModel global que sobrevive a cambios de configuracion
        factory = ListadoViewModelFactory(
            BaseDatos.getDatabase(context).usuarioDao(),
            BaseDatos.getDatabase(context).multimediaDao())
    )
    val listadoUiState by listadoViewModel.estadoPublico.collectAsState()

    // Colectar el Flow si no es nulo
    val usuarios by listadoUiState.listaUsuarios.collectAsState(initial = emptyList())

    LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        items(usuarios) { usuario ->
            // Cargar la foto de forma segura usando produceState
            val fotoUsuario by produceState(initialValue = "") {
                value = listadoViewModel.obtenerFotoUsuario(usuario.id)
            }

            ListadoItem(
                usuario = usuario,
                fotoUsuario = fotoUsuario,
                navegarAUsuario = navegarAUsuario
            )
        }
    }
}