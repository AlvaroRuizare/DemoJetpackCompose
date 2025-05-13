package com.example.apppracticasjc.Data.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// El Dao es donde se alojan las consultas y desde donde se llaman

@Dao
interface MultimediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(multimedia: MultimediaEntity)

    @Query("SELECT ruta from multimedia WHERE idUsuario = :idUsuario")
    fun getRuta(idUsuario: Int): String
}