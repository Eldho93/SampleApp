package com.eldho.sampleapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldho.sampleapp.model.moviesmodel.Movies
import com.eldho.sampleapp.repository.MainActivityRepository


import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {


    var repo: MainActivityRepository=MainActivityRepository()


    fun getMoviesApi()  {
        viewModelScope.launch {
            repo.getMoviesList()
        }

    }


    fun getMoviesApiObservable(): MutableLiveData<Movies> {
        return repo.moviesSetterGetter
    }



    fun getProgressObservable(): MutableLiveData<Boolean> {
        return repo.showProgress
    }
}