package com.frbiw.core.data.source.remote

import com.frbiw.core.data.source.remote.network.ApiResponse
import com.frbiw.core.data.source.remote.network.ApiService
import com.frbiw.core.data.source.remote.response.GetMovieResponse
import com.frbiw.core.data.source.remote.response.GetMovieTrailerByIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovieDiscover(): Flow<ApiResponse<GetMovieResponse>>{
        return flow {
            try {
                val response = apiService.getMovieDiscover()
                emit(ApiResponse.Success(response))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieTrailerById(id:Int): Flow<ApiResponse<GetMovieTrailerByIdResponse>>{
        return channelFlow {
            try {
                val response = apiService.getMovieTrailerById(id)
                trySend(ApiResponse.Success(response))
            }catch (e:Exception){
                trySend(ApiResponse.Error(e.toString()))
            }
            awaitClose()
        }
    }
}