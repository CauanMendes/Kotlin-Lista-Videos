package com.example.listavideos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listavideos.databinding.ActivityDetalhesBinding

class DetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carrega dados vindos do adapter
        val thumbnail = intent.getIntExtra("thumbnail", 0)
        val titulo = intent.getStringExtra("titulo")
        val canal = intent.getStringExtra("canal")
        val duracao = intent.getStringExtra("duracao")
        val descricao = intent.getStringExtra("descricao")
        val url = intent.getStringExtra("url")

        // Exibe os dados na tela
        binding.imgThumbnail.setImageResource(thumbnail)
        binding.tvTitulo.text = titulo
        binding.tvCanal.text = canal
        binding.tvDuracao.text = duracao
        binding.tvDescricao.text = descricao

        // Botão para assistir no YouTube
        binding.btnAssistir.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // Botão para voltar
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
