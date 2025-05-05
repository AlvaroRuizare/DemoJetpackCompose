package com.example.apppracticasjc.Navigation

// Diferentes pantallas entre las que podemos navegar
sealed class Pantallas(val route : String) { // Clase que recibe parámetro ruta
    object PantallaLogin : Pantallas("pantalla_login")
    object PantallaRegistro : Pantallas("pantalla_registro")
    object PantallaListado : Pantallas("pantalla_listado")
}
