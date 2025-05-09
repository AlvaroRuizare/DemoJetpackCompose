package com.example.apppracticasjc.Navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppracticasjc.View.Listado
import com.example.apppracticasjc.View.Login
import com.example.apppracticasjc.View.Registro

// Aquí es donde se gestiona la navegación

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navegacion() {
    val navController = rememberNavController() // Creamos variable con el NavController por defecto para enviarsela al NavHost

    // NavHost permite crear flujos de navegación entre distintos Composables
    NavHost(navController = navController,
        startDestination = Pantallas.PantallaLogin.route) // Se define la pantalla con la que iniciar por defecto
    {
        composable(route = Pantallas.PantallaLogin.route) { // Indicamos ruta definida en Pantallas.kt
            Login(navController) // Indicamos Composable
        }

        composable(route = Pantallas.PantallaRegistro.route) {
            Registro(navController)
        }

        composable(route = Pantallas.PantallaListado.route) {
            Listado(navController, {  })
        }
    }
}