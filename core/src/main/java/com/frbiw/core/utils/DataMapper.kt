package com.frbiw.core.utils

import com.frbiw.core.data.source.local.entity.MovieEntity
import com.frbiw.core.data.source.remote.response.Result
import com.frbiw.core.data.source.remote.response.ResultsItem
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    private const val IMG_PATH = "https://image.tmdb.org/t/p/w500"

    fun mapListResponseToDomain(movieDiscoverResponse:List<Result>): Flow<List<Movie>> {
        val movieDiscoverList = ArrayList<Movie>()
        movieDiscoverResponse.map {
            val movieDiscover = Movie(
                IMG_PATH+it.posterPath,
                it.title,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount.toDouble(),
                it.runtime,
                it.releaseDate,
                false
            )
            movieDiscoverList.add(movieDiscover)
        }
        return flowOf(movieDiscoverList)
    }

    fun mapListMovieTrailerToDomain(movieVideoResponse: List<ResultsItem>): Flow<List<MovieTrailer>>{
        val movieVideoList = ArrayList<MovieTrailer>()
        movieVideoResponse.map {
            val movieVideo = MovieTrailer(
                it.iso6391,
                it.iso31661,
                it.name,
                it.key,
                it.site,
                it.size,
                it.type,
                it.official,
                it.publishedAt,
                it.id
            )
            movieVideoList.add(movieVideo)
        }
        return flowOf(movieVideoList)
    }

    fun mapDomainToEntity(movie:Movie) =
        MovieEntity(
            movie.id,
            movie.img,
            movie.name,
            movie.overview,
            movie.voteAverage,
            movie.voteCount,
            movie.runtime,
            movie.releaseDate,
            movie.isFavorite
        )
    fun mapListEntityToDomain(listMovieDiscoverEntity: List<MovieEntity>): List<Movie> =
        listMovieDiscoverEntity.map {
            Movie(
                it.img,
                it.name,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount,
                it.runtime,
                it.releaseDate,
                it.isFavorite
            )
        }

}