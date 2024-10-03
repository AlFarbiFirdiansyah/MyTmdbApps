package com.frbiw.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.frbiw.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovieToDb(movieEntity: MovieEntity)
    @Query("SELECT * FROM movie_favorite")
    fun getAllMovieFavorite(): Flow<List<MovieEntity>>
    @Delete
    suspend fun deleteMovieFromDb(movieEntity: MovieEntity)
    @Query("SELECT EXISTS(SELECT * FROM movie_favorite WHERE id = :id)")
    fun isFavoriteMovie(id: Int): Flow<Boolean>
}