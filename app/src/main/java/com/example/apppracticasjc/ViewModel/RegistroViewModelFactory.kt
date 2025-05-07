package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao

// ViewModelFactory permite que el viewModel sea global y sobreviva a cambios de configuración

class RegistroViewModelFactory(private val usuarioDao: UsuarioDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            return RegistroViewModel(usuarioDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}