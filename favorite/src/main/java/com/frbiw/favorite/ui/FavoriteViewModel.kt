package com.frbiw.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.frbiw.core.domain.usecase.MovieUseCase
import javax.inject.Inject


class FavoriteViewModel @Inject constructor( movieUseCase: MovieUseCase): ViewModel() {
    val getAllMovieFavorite = movieUseCase.getAllMovieFavorite().asLiveData()
}