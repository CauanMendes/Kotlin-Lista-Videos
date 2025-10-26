package com.example.listavideos.model

data class Videos(
    val thumbnail: Int,      // imagem de capa do vídeo
    val titulo: String,      // título do vídeo
    val canal: String,       // nome do canal
    val duracao: String,     // duração do vídeo
    val descricao: String,   // descrição do vídeo
    val url: String          // link do vídeo no YouTube
)