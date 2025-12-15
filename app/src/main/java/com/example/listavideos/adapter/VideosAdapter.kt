package com.example.listavideos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listavideos.databinding.ItemVideoBinding
import androidx.core.net.toUri
import com.example.listavideos.model.Videos

/*
A classe VideosAdapter é responsável por conectar os dados da lista de vídeos
(List<Videos>) com os itens visuais exibidos dentro do RecyclerView.
*/

class VideosAdapter(
    private var videos: List<Videos>,
    private val onClick: (Videos) -> Unit
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    fun updateLista(novosVideos: List<Videos>) {
        this.videos = novosVideos
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Videos) {
            // Carregar imagem da URI (thumbnail)
            binding.imgThumbnail.setImageURI(video.thumbnailUri.toUri())

            // Definir os textos
            binding.tvTitulo.text = video.titulo
            binding.tvCanal.text = video.canal
            binding.tvDuracao.text = video.duracao
            binding.tvDescricao.text = video.descricao

            // Clique no item
            binding.root.setOnClickListener { onClick(video) }
        }
    }
}