package com.example.apppracticasjc.Data.Model

import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ListadoUiState(
    var listaUsuarios : Flow<List<UsuarioEntity>> = flowOf(/*emptyList()*/)
)