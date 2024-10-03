package com.frbiw.mytmdbapps.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.frbiw.core.data.source.Resource
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import com.frbiw.core.ui.MovieTrailerAdapter
import com.frbiw.mytmdbapps.R
import com.frbiw.mytmdbapps.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var movie: Movie
    private var isFavoriteMovie by Delegates.notNull<Boolean>()
    private val movieTrailerAdapter: MovieTrailerAdapter by lazy {
        MovieTrailerAdapter{}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(MOVIE_KEY)?.let { data ->
            movie = data
            setupUI(movie)
            observeFavorite(data.id)
            data.id?.let {
                viewModel.getMovieTrailerById(it)
                observeMovieTrailer()
                setupRv()
            }
            favoriteOnClick()
        }
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRv() {
        binding.rvMovieTrailer.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = movieTrailerAdapter
        }
    }

    private fun observeMovieTrailer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.movieTrailerResponse.collect{
                    when(it){
                        is Resource.Success ->{
                            binding.rvMovieTrailer.visibility = View.VISIBLE
                            binding.pbMovieTrailer.visibility = View.GONE
                            it.data?.let { movieTrailers ->
                                movieTrailerAdapter.setItems(movieTrailers)
                            }
                        }
                        is Resource.Loading -> {
                            binding.rvMovieTrailer.visibility = View.GONE
                            binding.pbMovieTrailer.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            binding.rvMovieTrailer.visibility = View.GONE
                            binding.pbMovieTrailer.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    private fun observeFavorite(movie: Int?) {
        if (movie != null) {
            viewModel.isFavoriteMovie(movie).observe(viewLifecycleOwner){favorite ->
                isFavoriteMovie = favorite
                updateFavoriteButtonIcon()
            }
        }
    }

    private fun favoriteOnClick() {
        binding.cvFavoriteDetail.setOnClickListener {
            if (isFavoriteMovie){
                viewModel.deleteMovieFromDb(movie)
            }else{
                viewModel.insertMovieToDb(movie)
            }
            isFavoriteMovie = !isFavoriteMovie
            updateFavoriteButtonIcon()
        }
    }

    private fun updateFavoriteButtonIcon() {
        val iconResource = if (isFavoriteMovie){
            R.drawable.baseline_favorite_24
        }else{
            R.drawable.baseline_favorite_border_24
        }
        binding.ivFavorite.setImageResource(iconResource)
    }


    private fun setupUI(movie: Movie) {
        binding.apply {
            Glide.with(requireContext()).load(movie?.img).into(ivMoviePoster)
            tvRelease.text = movie.releaseDate?.substring(0,4)
            tvRating.text = movie.voteAverage.toString()
            tvNameDetail.text = movie.name
            tvAboutDescription.text = movie.overview
        }
    }

    companion object{
        const val MOVIE_KEY = "MOVIE"
    }
}