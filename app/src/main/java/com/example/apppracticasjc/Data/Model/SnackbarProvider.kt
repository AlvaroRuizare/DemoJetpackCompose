package com.example.apppracticasjc.Data.Model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

// Este archivo se encarga de que el SnackBar pueda ser llamado desde toda la aplicación

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}