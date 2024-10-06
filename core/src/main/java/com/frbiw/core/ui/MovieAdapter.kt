package com.frbiw.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frbiw.core.databinding.ItemListMovieBinding
import com.frbiw.core.domain.model.Movie

class MovieAdapter(private val onItemClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemListMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class MovieViewHolder(
        private val binding: ItemListMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Movie) {
            binding.apply {
                tvName.text = result.name
                tvRating.text = result.voteAverage.toString()
                Glide.with(itemView).load(result.img).into(ivMovie)
                root.setOnClickListener {
                    onItemClickListener(result)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}