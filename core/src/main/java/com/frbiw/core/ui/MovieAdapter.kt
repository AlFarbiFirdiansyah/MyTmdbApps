package com.frbiw.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frbiw.core.databinding.ItemListMovieBinding
import com.frbiw.core.domain.model.Movie

class MovieAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieDiscoverViewHolder>() {


    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDiscoverViewHolder {
        val binding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDiscoverViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieDiscoverViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MovieDiscoverViewHolder(
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