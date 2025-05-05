package com.example.apppracticasjc.Navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppracticasjc.View.Login
import com.example.apppracticasjc.View.Registro
import com.example.apppracticasjc.ViewModel.LoginViewModel
import com.example.apppracticasjc.ViewModel.RegistroViewModel


// Aquí es donde se usa la librería de navegación que metemos en el build.gradle
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navegacion() {
    val navController = rememberNavController() // Creamos variable con el NavController por defecto para enviarsela al NavHost

    // NavHost permite crear flujos de navegación entre distintos Composables
    NavHost(navController = navController,
        startDestination = Pantallas.PantallaLogin.route) // Se define la pantalla con la que iniciar por defecto
    {
        composable(route = Pantallas.PantallaLogin.route) { // Indicamos ruta definida en Pantallas.kt
            // Enviamos instancia del LoginViewModel para que la View reciba datos del ViewModel
            Login(navController, LoginViewModel()) // Indicamos Composable
        }

        composable(route = Pantallas.PantallaRegistro.route) {
            // Enviamos instancia del Registro ViewModel para que la View reciba datos del ViewModel
            Registro(navController, RegistroViewModel())
        }
    }
}