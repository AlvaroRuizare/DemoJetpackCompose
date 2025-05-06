package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.apppracticasjc.Data.Model.LoginUiState
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import com.example.apppracticasjc.Navigation.Pantallas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(usuarioDao: UsuarioDao) : ViewModel(){
    private val _estadoPrivado = MutableStateFlow(LoginUiState())
    val estadoPublico : StateFlow<LoginUiState> = _estadoPrivado.asStateFlow()

    private fun contrasenaValida(contrasena: String): Boolean = contrasena.length > 4
    private fun usuarioValido(usuario: String): Boolean = usuario.length > 4

    // Función que se ejecuta cuando se pulsa el botón de login
    fun pulsarLogin(navController: NavController) {
        navController.navigate(Pantallas.PantallaRegistro.route) // Se navega
    }

    // Navegar al registro
    fun navegarRegistro(navController: NavController) {
        navController.navigate(Pantallas.PantallaRegistro.route) // Se navega
    }


    // Al modificar alguno de los campos...
    fun alEditarLogin(usuario: String, contrasena: String) { // Recibe usuario y contraseña de los campos
        // El email privado adopta el valor del email recibido
        _estadoPrivado.update { estadoActual ->
            estadoActual.copy(
                valorCampoUsuario = usuario
            )
        }

        // Igual con la contraseña
        _estadoPrivado.update { estadoActual ->
            estadoActual.copy(
                valorCampoContrasena = contrasena
            )
        }

        // Si el email y la contraseña son válidos...
        if(usuarioValido(usuario) &&
            contrasenaValida(contrasena)){
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = true, // Se habilita el botón
                    textoError = ""
                )
            }
        }else{ // Si o email o contraseña son inválidos...
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "Usuario y contraseña mayor que 4 caracteres"
                )
            }
        }
    }
}