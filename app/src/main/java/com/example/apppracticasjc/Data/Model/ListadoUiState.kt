package com.example.apppracticasjc.Data.Model

import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity

data class ListadoUiState(
    var listaUsuarios : List<UsuarioEntity> = emptyList(),
    var mostrarBottomSheet : Boolean = false
)