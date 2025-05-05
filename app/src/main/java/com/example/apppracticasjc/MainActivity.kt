package com.example.apppracticasjc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.example.apppracticasjc.Navigation.Navegacion
import com.example.compose.AppPracticasJCTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPracticasJCTheme {
                Scaffold {
                    Navegacion() // Navegacion() se encarga del flujo de navegación
                }
            }
        }
    }
}