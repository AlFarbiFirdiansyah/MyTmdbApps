package com.frbiw.core.domain.repository

import com.frbiw.core.data.source.Resource
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovieDiscover(): Flow<Resource<List<Movie>>>
    fun getMovieTrailerById(id:Int): Flow<Resource<List<MovieTrailer>>>
    suspend fun insertMovieToDb(movie:Movie)
    fun getAllMovieFavorite(): Flow<List<Movie>>
    suspend fun deleteMovieFromDb(movie: Movie)
    fun isFavoriteMovie(id:Int): Flow<Boolean>
}