package com.example.myfirstapp.api.retrofit

import com.example.myfirstapp.api.model.Film
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApiInterface {
    @GET("/search")
    suspend fun searchFilms(@Query("query") query: String,
                            @Query("type") type: String): Response<List<Film>>
}