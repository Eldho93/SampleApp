package com.eldho.sampleapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eldho.sampleapp.model.moviesmodel.Movies
import com.eldho.sampleapp.retrofit.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityRepository {


    val moviesSetterGetter = MutableLiveData<Movies>()
    val showProgress = MutableLiveData<Boolean>()

    fun getMoviesList() {

        val call = RetrofitClient.apiInterface.getMovies("guardian","f468437c")

        showProgress.value = true

        call.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())

                showProgress.value = false
            }

            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {

                if (response.code() == 200) {

                    val dataRes = response.body()


                    moviesSetterGetter.value = response.body()

                }

                showProgress.value = false
            }
        })

    }


}

