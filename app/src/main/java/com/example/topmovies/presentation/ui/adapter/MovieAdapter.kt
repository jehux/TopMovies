package com.example.topmovies.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topmovies.databinding.ItemMovieCardBinding
import com.example.topmovies.domain.model.MovieWithImage
import java.io.File

class MovieAdapter(
    private val items: List<MovieWithImage>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position].movie
        val image = items[position].imageFile
        holder.binding.textMovieTitle.text = movie.title
        holder.binding.textRating.text = "${String.format("%.1f", movie.voteAverage)}/10 IMDb"
        image?.let {
            loadImageFromFile(holder.itemView.context, it, holder.binding.imagePoster)
        }
        holder.binding.root.setOnClickListener {
            onItemClick(movie.id)
        }

    }

    private fun loadImageFromFile(context: Context, imageFile: File, imageView: ImageView) {
        Glide.with(context)
            .load(imageFile)
            .into(imageView)
    }
}
