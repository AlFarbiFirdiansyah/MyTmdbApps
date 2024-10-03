package com.frbiw.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frbiw.core.databinding.ItemListFavoriteBinding
import com.frbiw.core.databinding.ItemListMovieBinding
import com.frbiw.core.domain.model.Movie

class FavoriteAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class FavoriteViewHolder(
        private val binding: ItemListMovieBinding,
        val itemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Movie) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvName.text = name
                    tvRating.text = voteAverage.toString()
                    Glide.with(itemView).load(item.img).into(ivMovie)
                }
            }

        }
    }

}