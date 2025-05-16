package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apppracticasjc.Data.RoomDB.MultimediaDao
import com.example.apppracticasjc.Data.RoomDB.TipoUsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao

// ViewModelFactory permite que el viewModel sea global y sobreviva a cambios de configuración

class ListadoViewModelFactory(
    private val usuarioDao: UsuarioDao,
    private val tipoUsuarioDao: TipoUsuarioDao,
    private val multimediaDao: MultimediaDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListadoViewModel::class.java)) {
            return ListadoViewModel(usuarioDao, tipoUsuarioDao, multimediaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}