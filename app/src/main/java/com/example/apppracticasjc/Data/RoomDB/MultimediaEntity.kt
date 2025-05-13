package com.example.apppracticasjc.Data.RoomDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// El entity es donde se crea la tabla de base de datos

@Entity(tableName = "Multimedia",
    foreignKeys = [ForeignKey(entity = UsuarioEntity::class,
        parentColumns = ["id"],
        childColumns = ["idUsuario"],
        onDelete = ForeignKey.SET_NULL)])
data class MultimediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val idUsuario : Int,

    val ruta : String
)