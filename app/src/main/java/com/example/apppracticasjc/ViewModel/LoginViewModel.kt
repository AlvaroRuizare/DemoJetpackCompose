package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.apppracticasjc.Data.Model.LoginUiState
import com.example.apppracticasjc.Data.RoomDB.TipoUsuarioDao
import com.example.apppracticasjc.Data.RoomDB.TipoUsuarioEntity
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import com.example.apppracticasjc.Data.VariablesGlobales
import com.example.apppracticasjc.Navigation.Pantallas
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val usuarioDao: UsuarioDao, private val tiposUsuarioDao: TipoUsuarioDao) : ViewModel(){
    private val _estadoPrivado = MutableStateFlow(LoginUiState())
    val estadoPublico : StateFlow<LoginUiState> = _estadoPrivado.asStateFlow()

    // Eventos para el Snackbar
    private val _eventosUI = MutableSharedFlow<String>()
    val eventosUI = _eventosUI.asSharedFlow()

    /**
     * Al iniciar el ViewModel...
     */
    init {
        viewModelScope.launch {
            if (tiposUsuarioDao.getAllTiposUsuario().isEmpty()){
                tiposUsuarioDao.insert(TipoUsuarioEntity(0, "Usuario"))
                tiposUsuarioDao.insert(TipoUsuarioEntity(0, "Administrador"))
                mostrarSnackbar("Se han inicializado los tipos de usuario")
            }
        }
    }


    // Funciones pequeñas
    private fun contrasenaValida(contrasena: String): Boolean = contrasena.length > 4
    private fun usuarioValido(usuario: String): Boolean = usuario.length > 4
    fun navegarRegistro(navController: NavController) = navController.navigate(Pantallas.PantallaRegistro.route)


    /**
     * Al modificar alguno de los campos...
     */
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


    /**
     * Al pulsar el botón de login...
     */
    fun pulsarLogin(navController: NavController) {
        viewModelScope.launch {
            if (credencialesCorrectas(
                _estadoPrivado.value.valorCampoUsuario.trim(),
                _estadoPrivado.value.valorCampoContrasena.trim(),

                // PARA INICIAR SESION AUTOMATICAMENTE
                // "admin",
                // "admin"
            )){
                navController.navigate(Pantallas.PantallaListado.route)
            } else {
                _estadoPrivado.update { estadoActual ->
                    estadoActual.copy(
                        textoError = "Credenciales incorrectas"
                    )
                }
            }
        }
    }


    /**
     * Mostrar el snackbar
     */
    fun mostrarSnackbar(mensaje: String) {
        viewModelScope.launch {
            _eventosUI.emit(mensaje)
        }
    }


    /**
     * Comprueba si las credenciales introducidas existen
     */
    suspend fun credencialesCorrectas(usuarioRecibido: String, contrasenaRecibida: String) : Boolean {
        var credencialesCorrectas = false

        val usuario = usuarioDao.getUsuarioContrasena(usuarioRecibido, contrasenaRecibida)

        if (usuario != null){
            VariablesGlobales.usuarioLogueado = usuario // Se guarda el usuario que ha iniciado sesión en un objeto global
            credencialesCorrectas = true
        }

        return credencialesCorrectas
    }
}