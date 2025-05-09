package com.example.apppracticasjc.Data.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// El entity es donde se crea la tabla de base de datos

@Entity(tableName = "TiposUsuario")
data class TipoUsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val tipoUsuario : String
)