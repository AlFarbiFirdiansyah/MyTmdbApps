package com.frbiw.mytmdbapps.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.frbiw.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    val getMovieDiscover = movieUseCase.getMovieDiscover().asLiveData()
}