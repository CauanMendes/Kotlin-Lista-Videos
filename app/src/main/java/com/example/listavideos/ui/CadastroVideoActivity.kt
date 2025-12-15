package com.example.listavideos.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listavideos.R
import com.example.listavideos.data.db.VideoDatabase
import com.example.listavideos.databinding.ActivityCadastroVideoBinding
import com.example.listavideos.model.Videos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroVideoBinding
    private lateinit var launcherGaleria: ActivityResultLauncher<Array<String>>
    private var uriSelecionada: String = ""
    private lateinit var db: VideoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VideoDatabase.getInstance(this)
        setupLauncher()
        setupListeners()
    }

    private fun setupLauncher() {
        launcherGaleria = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                uriSelecionada = uri.toString()
                binding.imgThumbnail.setImageURI(uri)
            }
        }
    }

    private fun setupListeners() {
        binding.imgThumbnail.setOnClickListener {
            launcherGaleria.launch(arrayOf("image/*"))
        }

        binding.btnSalvar.setOnClickListener {
            val titulo = binding.edtTitulo.text.toString()
            val canal = binding.edtCanal.text.toString()
            val duracao = binding.edtDuracao.text.toString()
            val descricao = binding.edtDescricao.text.toString()
            val url = binding.edtUrl.text.toString()

            if (titulo.isNotBlank() && canal.isNotBlank() &&
                duracao.isNotBlank() && descricao.isNotBlank() &&
                url.isNotBlank() && uriSelecionada.isNotBlank()) {

                val novoVideo = Videos(
                    titulo = titulo,
                    canal = canal,
                    duracao = duracao,
                    descricao = descricao,
                    url = url,
                    thumbnailUri = uriSelecionada
                )
                salvarNoBanco(novoVideo)
            } else {
                Toast.makeText(
                    this,
                    "Preencha todos os campos e selecione uma imagem",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnVoltar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun salvarNoBanco(video: Videos) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.videoDao().inserir(video)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}