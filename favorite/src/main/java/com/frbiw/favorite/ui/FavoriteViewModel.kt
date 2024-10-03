package com.frbiw.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.frbiw.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    val getAllMovieFavorite = movieUseCase.getAllMovieFavorite().asLiveData()
}