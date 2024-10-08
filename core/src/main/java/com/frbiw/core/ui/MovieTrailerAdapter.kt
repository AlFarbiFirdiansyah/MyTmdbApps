package com.frbiw.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frbiw.core.databinding.ItemTrailerMovieBinding
import com.frbiw.core.domain.model.MovieTrailer
import com.frbiw.core.utils.commonYoutubeUrl

class MovieTrailerAdapter(private val onItemClickListener: (MovieTrailer) -> Unit) :
    RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder>() {

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrailerViewHolder =
        MovieTrailerViewHolder(
            ItemTrailerMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MovieTrailerViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class MovieTrailerViewHolder(
        private val binding: ItemTrailerMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(result: MovieTrailer) {
            binding.apply {
                wvTrailer.apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webChromeClient = WebChromeClient()
                    loadUrl(result.key.commonYoutubeUrl())
                }
                root.setOnClickListener {
                    onItemClickListener(result)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTrailer>() {
            override fun areItemsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean =
                oldItem == newItem
        }
    }
}