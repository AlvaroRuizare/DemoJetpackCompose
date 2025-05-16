package com.example.apppracticasjc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppracticasjc.Data.Model.ListadoUiState
import com.example.apppracticasjc.Data.RoomDB.MultimediaDao
import com.example.apppracticasjc.Data.RoomDB.TipoUsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioDao
import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListadoViewModel(
    private val usuarioDao: UsuarioDao,
    private val tipoUsuarioDao: TipoUsuarioDao,
    private val multimediaDao: MultimediaDao
) : ViewModel(){
    private val _estadoPrivado = MutableStateFlow(ListadoUiState())
    val estadoPublico : StateFlow<ListadoUiState> = _estadoPrivado.asStateFlow()

    init {
        viewModelScope.launch {
            _estadoPrivado.update { estadoActual ->
                estadoActual.copy(
                    listaUsuarios = usuarioDao.getAllUsuarios()
                )
            }
        }
    }

    suspend fun obtenerFotoUsuario(idUsuario : Int) : String {
        return multimediaDao.getRuta(idUsuario)
    }

    suspend fun obtenerNombreTipoUsuario(idTipoUsuario : Int) : String {
        return tipoUsuarioDao.getNombreTipoUsuario(idTipoUsuario)
    }

    fun actualizarSeleccion(usuario : UsuarioEntity, seleccionado : Boolean) {
        _estadoPrivado.update { estadoActual ->
            val nuevaLista = estadoActual.listaUsuarios.map { u ->
                if (u.id == usuario.id) {
                    u.apply { estaSeleccionado = seleccionado }
                } else u
            }
            estadoActual.copy(listaUsuarios = nuevaLista)
        }
    }

    fun cerrarBottomSheet() {
        _estadoPrivado.update { estadoActual ->
            estadoActual.copy(
                mostrarBottomSheet = false
            )
        }
    }

    fun abrirBottomSheet() {
        _estadoPrivado.update { estadoActual ->
            estadoActual.copy(
                mostrarBottomSheet = true
            )
        }
    }
}