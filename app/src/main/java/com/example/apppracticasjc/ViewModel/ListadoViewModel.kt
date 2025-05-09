package com.example.apppracticasjc.ViewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.apppracticasjc.Data.Model.ListadoUiState
import com.example.apppracticasjc.Data.Model.RegistroUiState
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity
import com.example.apppracticasjc.Navigation.Pantallas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListadoViewModel(private val usuarioDao: UsuarioDao) : ViewModel(){
    private val _estadoPrivado = MutableStateFlow(ListadoUiState(listaUsuarios = usuarioDao.getAllUsuarios()))
    val estadoPublico : StateFlow<ListadoUiState> = _estadoPrivado.asStateFlow()

    init {
        _estadoPrivado.value.listaUsuarios = usuarioDao.getAllUsuarios()
    }
}