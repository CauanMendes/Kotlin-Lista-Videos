package com.example.listavideos.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.listavideos.model.Videos

@Dao
interface VideoDao {
    @Insert
    suspend fun inserir(video: Videos)

    @Query("SELECT * FROM video ORDER BY titulo ASC")
    suspend fun listarTodos(): List<Videos>

    @Query("SELECT * FROM video WHERE titulo LIKE :filtro ORDER BY titulo ASC")
    suspend fun filtrarPorTitulo(filtro: String): List<Videos>

    @Delete
    suspend fun deletar(video: Videos)

    @Update
    suspend fun atualizar(video: Videos)
}