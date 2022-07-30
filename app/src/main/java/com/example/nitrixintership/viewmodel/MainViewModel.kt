package com.example.nitrixintership.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.repository.Repository
import com.example.nitrixintership.repository.localdata.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    //Room
    val readMovies: LiveData<List<MovieEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertMovies(movieEntity: MovieEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMovies(movieEntity)
        }

    //Retrofit
    var movieGetResponse: MutableLiveData<MovieResult> = MutableLiveData()

    fun getMovies() = viewModelScope.launch {
        moviesGet()
    }

    private suspend fun moviesGet() {
        val response = repository.remote.getMovies()
        val request: (Response<MovieResult>) -> MovieResult? = ::apiRequest
        movieGetResponse.value = request(response)
        val movieResult = movieGetResponse.value
        if(movieResult != null){
            cacheMovies(movieResult)
        }
    }

    private fun cacheMovies(movieResult: MovieResult){
        val movieEntity = MovieEntity(movieResult)
        insertMovies(movieEntity)
    }

    private fun apiRequest(response: Response<MovieResult>): MovieResult? {
        return response.body()
    }
}