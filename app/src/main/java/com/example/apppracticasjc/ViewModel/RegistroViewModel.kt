package com.example.apppracticasjc.ViewModel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.apppracticasjc.Model.RegistroUiState
import com.example.apppracticasjc.Navigation.Pantallas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel : ViewModel() {
    private val _estadoPrivado = MutableStateFlow(RegistroUiState())
    val estadoPublico : StateFlow<RegistroUiState> = _estadoPrivado.asStateFlow()

    private fun usuarioValido(usuario: String): Boolean = usuario.length > 4
    private fun contrasenaValida(contrasena: String): Boolean = contrasena.length > 4
    private fun emailValido(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()



    // Al pulsar el botón de login...
    fun pulsarLogin(navController: NavController) {
        navController.navigate(Pantallas.PantallaLogin.route) // Se navega
    }

    // Navegar hacia el login
    fun navegarLogin(navController: NavController) {
        navController.navigate(Pantallas.PantallaLogin.route) // Se navega
    }

    // Función que se ejecuta al modificar el Registro
    fun alEditarRegistro(usuario: String, contrasena1: String, contrasena2: String, email: String, fecha: String, tipoUsuario: String) {
        _estadoPrivado.update { estadoActual -> // Se actualizan todos los datos dinámicamente
            estadoActual.copy(
                valorCampoUsuario = usuario,
                valorCampoContrasena = contrasena1,
                valorCampoContrasena2 = contrasena2,
                valorCampoCorreo = email,
                valorCampoFecha = fecha,
                valorCampoTipo = tipoUsuario
            )
        }

        // Si todos los campos son válidos...
        if(camposValidos(usuario, contrasena1, contrasena2, email, fecha, tipoUsuario)){
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = true, // Se deshabilita el botón
                    textoError = ""
                )
            }
        }
    }


    /**
     * Comprobar si los campos son válidos
     */
    fun camposValidos(
        usuario: String, contrasena1: String, contrasena2: String,
        email: String, fecha: String, tipoUsuario: String
    ) : Boolean{
        var todoValido = true

        if (!usuarioValido(usuario)) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "El usuario debe superar los 4 caracteres"
                )
            }
        } else if (!contrasenaValida(contrasena1) || contrasena1 != contrasena2) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "Contraseñas iguales y de más de 4 caracteres"
                )
            }
        } else if (!emailValido(email)) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "El email debe ser válido"
                )
            }
        } else if (fecha.isEmpty()) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "La fecha no debe estar vacía"
                )
            }
        } else if (tipoUsuario.isEmpty()) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "El tipo de usuario debe estar seleccionado"
                )
            }
        }

        return todoValido
    }
}