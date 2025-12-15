package com.example.listavideos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "video")
data class Videos(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val thumbnailUri: String,      // URI da imagem (substitui 'foto')
    val duracao: String,           // Substitui 'peso'
    val url: String,               // Novo campo para URL do vídeo
    val titulo: String,            // Substitui 'nome'
    val canal: String,             // Novo campo para nome do canal
    val descricao: String          // Mantém 'descricao'
) : Serializable