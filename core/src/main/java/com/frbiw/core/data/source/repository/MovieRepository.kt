package com.frbiw.core.data.source.repository

import android.provider.ContactsContract.Data
import com.frbiw.core.data.source.NetworkBoundResource
import com.frbiw.core.data.source.Resource
import com.frbiw.core.data.source.local.LocalDataSource
import com.frbiw.core.data.source.remote.RemoteDataSource
import com.frbiw.core.data.source.remote.network.ApiResponse
import com.frbiw.core.data.source.remote.response.GetMovieResponse
import com.frbiw.core.data.source.remote.response.GetMovieTrailerByIdResponse
import com.frbiw.core.domain.model.Movie
import com.frbiw.core.domain.model.MovieTrailer
import com.frbiw.core.domain.repository.IMovieRepository
import com.frbiw.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource,
private val localDataSource: LocalDataSource): IMovieRepository {

    override fun getMovieDiscover(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, GetMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieResponse>> =
                remoteDataSource.getMovieDiscover()

            override fun loadFromNetwork(data: GetMovieResponse): Flow<List<Movie>> =
                DataMapper.mapListResponseToDomain(data.results)
        }.asFlow()

    override fun getMovieTrailerById(id: Int): Flow<Resource<List<MovieTrailer>>> =
        object : NetworkBoundResource<List<MovieTrailer>, GetMovieTrailerByIdResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieTrailerByIdResponse>> =
                remoteDataSource.getMovieTrailerById(id)

            override fun loadFromNetwork(data: GetMovieTrailerByIdResponse): Flow<List<MovieTrailer>> =
                DataMapper.mapListMovieTrailerToDomain(data.results)

        }.asFlow()

    override suspend fun insertMovieToDb(movie: Movie) =
        localDataSource.insertMovieToDb(DataMapper.mapDomainToEntity(movie))

    override fun getAllMovieFavorite(): Flow<List<Movie>> =
        localDataSource.getAllMovieFavorite().map {
            DataMapper.mapListEntityToDomain(it)
        }

    override suspend fun deleteMovieFromDb(movie: Movie) =
        localDataSource.deleteMovieFromDb(DataMapper.mapDomainToEntity(movie))

    override fun isFavoriteMovie(id: Int): Flow<Boolean> =
        localDataSource.isFavoriteMovie(id)
}