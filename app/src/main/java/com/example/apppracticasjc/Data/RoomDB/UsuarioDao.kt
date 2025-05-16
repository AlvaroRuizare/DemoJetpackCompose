package com.example.apppracticasjc.Data.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// El Dao es donde se alojan las consultas y desde donde se llaman

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: UsuarioEntity)

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("SELECT * from usuarios WHERE id = :id")
    fun getUsuario(id: Int): Flow<UsuarioEntity>

    @Query("SELECT * from usuarios")
    suspend fun getAllUsuarios(): List<UsuarioEntity>

    @Query("""
        SELECT * from usuarios 
        WHERE 
            nombre = :nombreRecibido and
            contrasena = :contrasenaRecibida""")
    suspend fun getUsuarioContrasena(nombreRecibido : String, contrasenaRecibida : String): UsuarioEntity?

    @Query("""
        SELECT nombre from usuarios 
        WHERE nombre = :nombreRecibido""")
    suspend fun getExisteNombre(nombreRecibido : String): String?

    @Query("""
        SELECT correo from usuarios 
        WHERE correo = :correoRecibido""")
    suspend fun getExisteCorreo(correoRecibido : String): String?

    @Query(
        """
        SELECT id from usuarios 
        WHERE nombre = :nombreRecibido""")
    suspend fun getIdUsuario(nombreRecibido : String): Int
}