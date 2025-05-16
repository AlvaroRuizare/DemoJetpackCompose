package com.example.apppracticasjc.Data.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// El Dao es donde se alojan las consultas y desde donde se llaman

@Dao
interface TipoUsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tipoUsuario: TipoUsuarioEntity)

    @Query("SELECT * from tiposusuario WHERE id = :id")
    fun getTipoUsuario(id: Int): Flow<TipoUsuarioEntity>

    @Query("SELECT tipoUsuario from tiposusuario WHERE id = :id")
    suspend fun getNombreTipoUsuario(id: Int): String

    @Query("SELECT tipoUsuario from tiposusuario")
    suspend fun getAllTiposUsuario(): List<String>
}