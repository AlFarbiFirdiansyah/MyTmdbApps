package com.frbiw.core.data.source.remote.network

import com.frbiw.core.data.source.remote.response.GetMovieResponse
import com.frbiw.core.data.source.remote.response.GetMovieTrailerByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieDiscover(): GetMovieResponse

    @GET("movie/{id}/videos?api_key=$API_KEY")
    suspend fun getMovieTrailerById(
        @Path("id") id:Int
    ):GetMovieTrailerByIdResponse

    companion object {
        private const val API_KEY = "3a1e854a67588dbb0dbf1cc6b6b7acac"
    }
}