package com.example.listavideos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listavideos.adapter.VideosAdapter
import com.example.listavideos.data.db.VideoDatabase
import com.example.listavideos.databinding.ActivityMainBinding
import com.example.listavideos.model.Videos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VideosAdapter
    private lateinit var db: VideoDatabase
    private lateinit var launcherCadastro: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VideoDatabase.getInstance(this)
        setupRecyclerView()
        setupListeners()
        setupActivityLauncher()
        carregarVideos()
    }

    private fun setupRecyclerView() {
        adapter = VideosAdapter(
            videos = emptyList(),
            onClick = { video ->

                val intent = Intent(this, DetalhesActivity::class.java)
                intent.putExtra("video", video)
                startActivity(intent)
            }
        )
        binding.recyclerViewVideos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVideos.adapter = adapter
    }

    private fun setupListeners() {
        // Botão flutuante para adicionar vídeo
        binding.fabAdicionarVideo.setOnClickListener {

            val intent = Intent(this, CadastroVideoActivity::class.java)
            launcherCadastro.launch(intent)
        }

        // Filtro em tempo real (se tiver EditText no layout)
        binding.edtBusca?.addTextChangedListener { texto ->
            filtrarVideos(texto.toString())
        }
    }

    private fun setupActivityLauncher() {
        launcherCadastro = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Recarregar lista quando voltar do cadastro
                carregarVideos()
                Toast.makeText(this, "Vídeo salvo com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun carregarVideos() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Verificar se é a primeira execução (banco vazio)
                val totalVideos = db.videoDao().listarTodos().size

                // Carregar todos os vídeos
                val videos = db.videoDao().listarTodos()

                withContext(Dispatchers.Main) {
                    adapter.updateLista(videos)

                    // Mostrar mensagem se lista estiver vazia
                    if (videos.isEmpty()) {
                        binding.tvListaVazia?.visibility = android.view.View.VISIBLE
                        binding.recyclerViewVideos.visibility = android.view.View.GONE
                    } else {
                        binding.tvListaVazia?.visibility = android.view.View.GONE
                        binding.recyclerViewVideos.visibility = android.view.View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Erro ao carregar vídeos: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun filtrarVideos(filtro: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val videosFiltrados = if (filtro.isBlank()) {
                    db.videoDao().listarTodos()
                } else {
                    db.videoDao().filtrarPorTitulo("%$filtro%")
                }

                withContext(Dispatchers.Main) {
                    adapter.updateLista(videosFiltrados)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Erro ao filtrar vídeos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarregar dados sempre que a activity voltar ao foco
        carregarVideos()
    }
}