package com.frbiw.core.domain.usecase

import com.frbiw.core.data.source.Resource
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import com.frbiw.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val iMovieRepository: IMovieRepository): MovieUseCase {
    override fun getMovieDiscover(): Flow<Resource<List<Movie>>> =
        iMovieRepository.getMovieDiscover()

    override fun getMovieTrailerById(id: Int): Flow<Resource<List<MovieTrailer>>> =
        iMovieRepository.getMovieTrailerById(id)

    override suspend fun insertMovieToDb(movie: Movie) =
        iMovieRepository.insertMovieToDb(movie)

    override fun getAllMovieFavorite(): Flow<List<Movie>> =
        iMovieRepository.getAllMovieFavorite()

    override suspend fun deleteMovieFromDb(movie: Movie) =
        iMovieRepository.deleteMovieFromDb(movie)

    override fun isFavoriteMovie(id: Int): Flow<Boolean> =
        iMovieRepository.isFavoriteMovie(id)

}