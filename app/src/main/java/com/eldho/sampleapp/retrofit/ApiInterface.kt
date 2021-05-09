package com.eldho.sampleapp.retrofit

import com.eldho.sampleapp.model.moviesmodel.Movies
import retrofit2.Call

import retrofit2.http.*

interface ApiInterface {


    @GET(".")
    fun getMovies(@Query("s") s:String,@Query("apikey") apikey:String): Call<Movies>


}