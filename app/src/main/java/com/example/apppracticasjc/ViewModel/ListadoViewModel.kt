package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import com.example.apppracticasjc.Data.Model.ListadoUiState
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListadoViewModel(private val usuarioDao: UsuarioDao) : ViewModel(){
    private val _estadoPrivado = MutableStateFlow(ListadoUiState(listaUsuarios = usuarioDao.getAllUsuarios()))
    val estadoPublico : StateFlow<ListadoUiState> = _estadoPrivado.asStateFlow()

    init {
        _estadoPrivado.value.listaUsuarios = usuarioDao.getAllUsuarios()
    }
}