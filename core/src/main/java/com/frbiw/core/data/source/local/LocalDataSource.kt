package com.frbiw.core.data.source.local

import com.frbiw.core.data.source.local.entity.MovieEntity
import com.frbiw.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertMovieToDb(movieEntity: MovieEntity) = movieDao.insertMovieToDb(movieEntity)
    fun getAllMovieFavorite(): Flow<List<MovieEntity>> = movieDao.getAllMovieFavorite()
    suspend fun deleteMovieFromDb(movieEntity: MovieEntity) = movieDao.deleteMovieFromDb(movieEntity)
    fun isFavoriteMovie(id:Int): Flow<Boolean> = movieDao.isFavoriteMovie(id)
}