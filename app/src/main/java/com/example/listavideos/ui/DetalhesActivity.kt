package com.example.listavideos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.listavideos.R
import com.example.listavideos.databinding.ActivityDetalhesBinding
import com.example.listavideos.model.Videos

class DetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesBinding
    private lateinit var video: Videos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        // Recebe o objeto Video da intent
        video = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("video", Videos::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("video") as Videos
        }
    }

    private fun setupViews() {
        // Preenche os dados do vídeo nas views
        binding.tvTitulo.text = video.titulo
        binding.tvCanal.text = video.canal
        binding.tvDuracao.text = video.duracao
        binding.tvDescricao.text = video.descricao

        // Carrega a imagem do thumbnail (se tiver URI)
        if (video.thumbnailUri.isNotEmpty()) {
            binding.imgThumbnail.setImageURI(video.thumbnailUri.toUri())
        } else {
            // Usar placeholder se não tiver imagem
            binding.imgThumbnail.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    private fun setupListeners() {
        // Botão para assistir o vídeo (abrir URL)
        binding.btnAssistir.setOnClickListener {
            Toast.makeText(this, "Abrindo vídeo...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = video.url.toUri()
            startActivity(intent)
        }


        // Botão voltar
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}