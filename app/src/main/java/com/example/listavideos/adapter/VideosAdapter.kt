package com.example.listavideos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.listavideos.databinding.ItemVideoBinding
import com.example.listavideos.model.Videos
import com.example.listavideos.ui.DetalhesActivity // ajuste o pacote conforme o nome real da pasta

class VideosAdapter(
    context: Context,
    private val lista: List<Videos>
) : ArrayAdapter<Videos>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemVideoBinding
        val itemView: View

        if (convertView == null) {
            binding = ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false)
            itemView = binding.root
            itemView.tag = binding
        } else {
            itemView = convertView
            binding = itemView.tag as ItemVideoBinding
        }

        val video = lista[position]
        binding.imgThumbnail.setImageResource(video.thumbnail)
        binding.tvTitulo.text = video.titulo
        binding.tvCanal.text = video.canal
        binding.tvDuracao.text = video.duracao
        binding.tvDescricao.text = video.descricao

        // Clique no item → abre a página de detalhes com os dados do vídeo
        itemView.setOnClickListener {
            val intent = Intent(context, DetalhesActivity::class.java)
            intent.putExtra("thumbnail", video.thumbnail)
            intent.putExtra("titulo", video.titulo)
            intent.putExtra("canal", video.canal)
            intent.putExtra("duracao", video.duracao)
            intent.putExtra("descricao", video.descricao)
            intent.putExtra("url", video.url)

            context.startActivity(intent)
        }

        return itemView
    }
}
