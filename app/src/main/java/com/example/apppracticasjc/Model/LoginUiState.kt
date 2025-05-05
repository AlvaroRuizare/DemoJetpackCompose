package com.example.apppracticasjc.Model

data class LoginUiState(
    var valorCampoUsuario : String = "",
    var valorCampoContrasena : String = "",
    var botonHabilitado : Boolean = false,
    var textoError : String = ""
)
