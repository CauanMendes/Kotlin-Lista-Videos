package com.example.listavideos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listavideos.R
import com.example.listavideos.adapter.VideosAdapter
import com.example.listavideos.databinding.ActivityMainBinding
import com.example.listavideos.model.Videos

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listaVideos: List<Videos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        listaVideos = listOf(
            Videos(
                thumbnail = R.drawable.thumb1,
                titulo = "MENTIRAM PRA VOCÊ SOBRE INTELIGÊNCIA ARTIFICIAL",
                canal = "Flow PodCast",
                duracao = "04:40:54",
                descricao = "Podcast #1228 Inteligência Ltda",
                url = "https://www.youtube.com/watch?v=sf4Gxf0LiKo"
            ),
            Videos(
                thumbnail = R.drawable.thumb2,
                titulo = "Look for what you Love... JUST DON'T!",
                canal = "Fábio Akita",
                duracao = "00:15:10",
                descricao = "RANT: Programação NÃO É Fácil",
                url = "https://www.youtube.com/watch?v=A2-yU3YjB1U"
            ),
            Videos(
                thumbnail = R.drawable.thumb3,
                titulo = "Como desenvolver boas práticas de programação?",
                canal = "Fábio Akita",
                duracao = "00:36:12",
                descricao = "Boas práticas de programação",
                url = "https://www.youtube.com/watch?v=GUanHEGlje4"
            ),
            Videos(
                thumbnail = R.drawable.thumb4,
                titulo = "You know nothing about Enterprise. Get to know SAP!",
                canal = "Fábio Akita",
                duracao = "00:36:22",
                descricao = "Enterprise / SAP",
                url = "https://www.youtube.com/watch?v=FXhcfJnlD2k"
            ),
            Videos(
                thumbnail = R.drawable.thumb5,
                titulo = "FÁBIO AKITA. Comece pelo básico. Fora da Norma Podcast",
                canal = "Fora da Norma",
                duracao = "01:07:19",
                descricao = "Entrevista no podcast Fora da Norma",
                url = "https://www.youtube.com/watch?v=C3tiSE1QJQ4"
            ),
            Videos(
                thumbnail = R.drawable.thumb6,
                titulo = "Basic Knowledge for Beginners in Programming.",
                canal = "Fábio Akita",
                duracao = "00:21:08",
                descricao = "Vídeo para iniciantes em programação",
                url = "https://www.youtube.com/watch?v=sx4hAHhO9CY"
            )
        )
        // Se quiser, pode ordenar ou filtrar por visualizações se tiver esse dado
    }

    private fun setupViews() {
        val adapter = VideosAdapter(this, listaVideos)
        binding.listViewVideos.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewVideos.setOnItemClickListener { _, _, pos, _ ->
            val video = listaVideos[pos]
            Toast.makeText(this, "Vídeo: ${video.titulo}", Toast.LENGTH_SHORT).show()
            // Opcionalmente, já lançar o vídeo:
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
            startActivity(intent)
        }
    }
}
