package com.example.apppracticasjc.Data.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

// El entity es donde se crea la tabla de base de datos

@Entity(tableName = "Usuarios",
    foreignKeys = [ForeignKey(entity = TipoUsuarioEntity::class,
                            parentColumns = ["id"],
                            childColumns = ["idTipoUsuario"],
                            onDelete = ForeignKey.SET_NULL)])
data class UsuarioEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val nombre : String,

    val contrasena : String,

    val correo : String,

    val fechaNacimiento : String,

    val idTipoUsuario : Int,

    @ColumnInfo(defaultValue = "0")
    val deBaja : Int = 0,

) {
    @Ignore
    var estaSeleccionado : Boolean = false
}
