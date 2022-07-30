package com.example.nitrixintership.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var movieGetResponse: MutableLiveData<MovieResult> = MutableLiveData()

    fun getMovies() = viewModelScope.launch {
        moviesGet()
    }

    private suspend fun moviesGet() {
        val response = repository.remote.getMovies()
        val request: (Response<MovieResult>) -> MovieResult? = ::apiRequest
        movieGetResponse.value = request(response)
    }

    private fun apiRequest(response: Response<MovieResult>): MovieResult? {
        return response.body()
    }
}