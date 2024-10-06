package com.frbiw.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frbiw.core.databinding.ItemListFavoriteBinding
import com.frbiw.core.databinding.ItemListMovieBinding
import com.frbiw.core.domain.model.Movie

class FavoriteAdapter(private val onItemClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            ItemListMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class FavoriteViewHolder(
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