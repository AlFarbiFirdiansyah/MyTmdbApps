package com.frbiw.mytmdbapps.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.frbiw.core.data.source.Resource
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import com.frbiw.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieTrailerResponse = MutableStateFlow<Resource<List<MovieTrailer>>>(Resource.Loading())
    val movieTrailerResponse: StateFlow<Resource<List<MovieTrailer>>> = _movieTrailerResponse.asStateFlow()

    fun getMovieTrailerById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieUseCase.getMovieTrailerById(id).collect { result ->
            _movieTrailerResponse.value = result
        }
    }
    fun insertMovieToDb(movie:Movie) = viewModelScope.launch {
        movieUseCase.insertMovieToDb(movie)
    }
    fun deleteMovieFromDb(movie:Movie) = viewModelScope.launch {
        movieUseCase.deleteMovieFromDb(movie)
    }
    fun isFavoriteMovie(id:Int) = movieUseCase.isFavoriteMovie(id).asLiveData()
}