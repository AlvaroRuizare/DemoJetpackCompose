package com.example.apppracticasjc.Data.Model

import androidx.compose.ui.focus.FocusState

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
    var focoCampoFecha : FocusState? = null

    )
