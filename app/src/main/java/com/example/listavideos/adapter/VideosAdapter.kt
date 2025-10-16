package com.example.listavideos.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.listavideos.databinding.ItemVideoBinding
import com.example.listavideos.model.Videos

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

        // Ao clicar no item, abre o vídeo no YouTube
        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
            context.startActivity(intent)
        }

        return itemView
    }
}
