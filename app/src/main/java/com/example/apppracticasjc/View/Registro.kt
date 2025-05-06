package com.example.apppracticasjc.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.ViewModel.RegistroViewModel
import com.example.apppracticasjc.ViewModel.RegistroViewModelFactory

@Composable
fun Registro(navController: NavHostController) {
    val context = LocalContext.current
    val registroViewModel : RegistroViewModel = viewModel(
        factory = RegistroViewModelFactory(BaseDatos.getDatabase(context).usuarioDao())
    )
    val registroUiState by registroViewModel.estadoPublico.collectAsState()

    Column(
        modifier = Modifier.padding(10.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenLogo()
        Spacer(modifier = Modifier.height(10.dp))

        TextoErrorLogin(registroUiState.textoError)
        Spacer(modifier = Modifier.height(5.dp))

        CampoFormulario(
            "Usuario",
            {registroViewModel.alEditarRegistro(
                    it,
                    registroUiState.valorCampoContrasena,
                    registroUiState.valorCampoContrasena2,
                    registroUiState.valorCampoCorreo,
                    registroUiState.valorCampoFecha,
                    registroUiState.valorCampoTipo
            )}, // Función al editar
            registroUiState.valorCampoUsuario
        )

        CampoFormulario(
            "Contraseña",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                it,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                registroUiState.valorCampoTipo
            )}, // Función al editar
            registroUiState.valorCampoContrasena
        )

        CampoFormulario(
            "Repetir contraseña",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                it,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                registroUiState.valorCampoTipo
            )}, // Función al editar
            registroUiState.valorCampoContrasena2
        )

        CampoFormulario(
            "Correo",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                it,
                registroUiState.valorCampoFecha,
                registroUiState.valorCampoTipo
            )}, // Función al editar
            registroUiState.valorCampoCorreo
        )

        CampoFormulario(
            "Fecha de nacimiento",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                it,
                registroUiState.valorCampoTipo
            )}, // Función al editar
            registroUiState.valorCampoFecha
        )

        CampoFormulario(
            "Tipo de usuario",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                it,
            )}, // Función al editar
            registroUiState.valorCampoTipo
        )

        TextoBoton(
            "Ya tengo cuenta",
            {registroViewModel.navegarLogin(navController)} // Función al pulsar texto
        )

        BotonSiguiente(
            "Crear cuenta",
            {registroViewModel.pulsarCrearCuenta(navController)}, // Función al pulsar botón
            registroUiState.botonHabilitado
        )
    }
}