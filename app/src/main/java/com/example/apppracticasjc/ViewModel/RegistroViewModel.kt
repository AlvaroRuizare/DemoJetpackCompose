package com.example.apppracticasjc.ViewModel

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.apppracticasjc.Data.Model.RegistroUiState
import com.example.apppracticasjc.Data.RoomDB.MultimediaDao
import com.example.apppracticasjc.Data.RoomDB.MultimediaEntity
import com.example.apppracticasjc.Data.RoomDB.TipoUsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity
import com.example.apppracticasjc.Navigation.Pantallas
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroViewModel(
    private val usuarioDao: UsuarioDao,
    private val tipoUsuarioDao: TipoUsuarioDao,
    private val multimediaDao: MultimediaDao
) : ViewModel() {

    private val _estadoPrivado = MutableStateFlow(RegistroUiState())
    val estadoPublico: StateFlow<RegistroUiState> = _estadoPrivado.asStateFlow()

    // Eventos para el Snackbar
    private val _eventosUI = MutableSharedFlow<String>()
    val eventosUI = _eventosUI.asSharedFlow()

    /**
     * Al iniciar el ViewModel...
     */
    init {
        // Obtener datos desplegable tiposUsuario
        viewModelScope.launch {
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    listaTiposUsuario = tipoUsuarioDao.getAllTiposUsuario()
                )
            }
        }
    }


    // Funciones pequeñas
    private fun usuarioValido(usuario: String): Boolean = usuario.length > 4
    private fun contrasenaValida(contrasena: String): Boolean = contrasena.length > 4
    private fun emailValido(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun navegarLogin(navController: NavController) = navController.navigate(Pantallas.PantallaLogin.route) // Se navega


    /**
     * Al pulsar botón de crear cuenta...
     */
    fun pulsarCrearCuenta() {
        viewModelScope.launch {
            if(!camposValidos(
                    _estadoPrivado.value.uriFotoPerfil,
                    _estadoPrivado.value.valorCampoUsuario,
                    _estadoPrivado.value.valorCampoContrasena,
                    _estadoPrivado.value.valorCampoContrasena2,
                    _estadoPrivado.value.valorCampoCorreo,
                    _estadoPrivado.value.valorCampoFecha,
                    _estadoPrivado.value.valorTipoUsuario
            )){
                _estadoPrivado.update { estadoActual ->
                    estadoActual.copy(
                        textoError = "Alguno de los campos no es válido"
                    )
                }
            } else if(yaExisteNombre(_estadoPrivado.value.valorCampoUsuario)) {
                _estadoPrivado.update { estadoActual ->
                    estadoActual.copy(
                        textoError = "El nombre del usuario ya existe en BD"
                    )
                }
            } else if(yaExisteCorreo(_estadoPrivado.value.valorCampoCorreo)) {
                _estadoPrivado.update { estadoActual ->
                    estadoActual.copy(
                        textoError = "El correo del usuario ya existe en BD"
                    )
                }
            } else {
                usuarioDao.insert(
                    UsuarioEntity(
                        0,
                        _estadoPrivado.value.valorCampoUsuario,
                        _estadoPrivado.value.valorCampoContrasena,
                        _estadoPrivado.value.valorCampoCorreo,
                        _estadoPrivado.value.valorCampoFecha,
                        tipoUsuarioAInt(_estadoPrivado.value.valorTipoUsuario)
                    )
                )

                multimediaDao.insert(
                    MultimediaEntity(
                        0,
                        usuarioDao.getIdUsuario(_estadoPrivado.value.valorCampoUsuario),
                        _estadoPrivado.value.uriFotoPerfil.toString()
                    )
                )

                mostrarSnackbar("Usuario " + _estadoPrivado.value.valorCampoUsuario + " creado correctamente")
            }
        }
    }


    /**
     * Comprobar si ya existe nombre en BD
     */
    private suspend fun yaExisteNombre(nombreRecibido: String): Boolean {
        var yaExiste = false

        val nombreBD : String? = usuarioDao.getExisteNombre(nombreRecibido)

        if (nombreBD != null){
            yaExiste = true
        }

        return yaExiste
    }


    /**
     * Comprobar si ya existe correo en BD
     */
    private suspend fun yaExisteCorreo(correoRecibido: String): Boolean {
        var yaExiste = false

        val correoBD : String? = usuarioDao.getExisteCorreo(correoRecibido)

        if (correoBD != null){
            yaExiste = true
        }

        return yaExiste
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
     * Al escribir en el formulario de registro...
     */
    fun alEditarRegistro(uriFoto : Uri, usuario: String, contrasena1: String, contrasena2: String, email: String, fecha: String, tipoUsuario: String) {
        _estadoPrivado.update { estadoActual -> // Se actualizan todos los datos dinámicamente
            estadoActual.copy(
                uriFotoPerfil = uriFoto,
                valorCampoUsuario = usuario,
                valorCampoContrasena = contrasena1,
                valorCampoContrasena2 = contrasena2,
                valorCampoCorreo = email,
                valorCampoFecha = fecha,
                valorTipoUsuario = tipoUsuario
            )
        }

        // Si todos los campos son válidos...
        if(camposValidos(uriFoto, usuario, contrasena1, contrasena2, email, fecha, tipoUsuario)){
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = true, // Se deshabilita el botón
                    textoError = ""
                )
            }
        }
    }


    /**
     * Convertir tipo usuario de string a int
     */
    fun tipoUsuarioAInt(cadenaRecibida : String) : Int {
        var usuarioDevolver = 0

        if (cadenaRecibida == "Usuario"){
            usuarioDevolver = 1
        } else if (cadenaRecibida == "Administrador") {
            usuarioDevolver = 2
        }

        return usuarioDevolver
    }


    /**
     * Comprobar si los campos son válidos
     */
    fun camposValidos(
        uriFoto: Uri, usuario: String, contrasena1: String, contrasena2: String,
        email: String, fecha: String, tipoUsuario: String
    ) : Boolean{
        var todoValido = true

        // AÑADIR IF AQUI CON EL MENSAJE DE QUE PASA SI NO HAY FOTO
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
        } else if (uriFoto == Uri.EMPTY) {
            todoValido = false
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    botonHabilitado = false, // Se deshabilita el botón
                    textoError = "La foto de perfil no puede estar vacía"
                )
            }
        }

        return todoValido
    }


    /**
     * Convierte milisegundos en Fecha
     */
    fun milisegundosAFecha(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}