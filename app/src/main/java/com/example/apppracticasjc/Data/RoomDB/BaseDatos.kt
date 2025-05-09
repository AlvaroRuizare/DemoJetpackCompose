package com.example.apppracticasjc.Data.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsuarioEntity::class, TipoUsuarioEntity::class], version = 2, exportSchema = false)
abstract class BaseDatos : RoomDatabase() {
    // Para que la BD tenga los DAO
    abstract fun usuarioDao(): UsuarioDao
    abstract fun tipoUsuarioDao(): TipoUsuarioDao

    // Permite el acceso a los métodos de la clase (que estén dentro de los corchetes del companion object) sin crear un objeto
    // Por ejemplo (BaseDatos.funcion())
    companion object {
        @Volatile
        private var InstanciaBD: BaseDatos? = null

        fun getDatabase(context: Context): BaseDatos {
            // Si la instancia de BD no es nula, se devuelve
            return InstanciaBD ?: synchronized(this) { // Si es nula, se crea una nueva instancia (de manera sincronizada para que no se creen varias instancias a la vez)
                Room.databaseBuilder(context, BaseDatos::class.java, "GestorUsuariosBD")
                    .fallbackToDestructiveMigration(false)
                    .build() // Crear instancia de base de datos
                    .also { InstanciaBD = it }
            }
        }
    }
}