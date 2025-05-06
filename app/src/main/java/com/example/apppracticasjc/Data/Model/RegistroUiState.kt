package com.example.apppracticasjc.Data.Model

data class RegistroUiState(
    var valorCampoUsuario : String = "",
    var valorCampoContrasena : String = "",
    var valorCampoContrasena2 : String = "",
    var valorCampoCorreo : String = "",
    var valorCampoFecha : String = "",
    var valorCampoTipo : String = "",

    var botonHabilitado : Boolean = false,
    var textoError : String = ""

)
