package com.example.apppracticasjc.Data.Model

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.ImageBitmap

data class RegistroUiState(
    var valorCampoUsuario : String = "",
    var valorCampoContrasena : String = "",
    var valorCampoContrasena2 : String = "",
    var valorCampoCorreo : String = "",
    var valorCampoFecha : String = "",
    var valorTipoUsuario : String = "",

    var botonHabilitado : Boolean = false,
    var textoError : String = "",

    var listaTiposUsuario : List<String> = mutableListOf(),

    var mostrarDatePicker : Boolean = false,
    var focoCampoFecha : FocusState? = null,

    // FOTOS
    var mostrarAlertDialog : Boolean = false,
    var uriTemporal: Uri = Uri.EMPTY,
    var fotoSeleccionada : ImageBitmap? = null,
    var origenFoto : String = "Ninguno"
)
