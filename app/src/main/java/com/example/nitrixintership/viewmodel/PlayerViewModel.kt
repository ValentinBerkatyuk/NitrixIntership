package com.example.nitrixintership.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nitrixintership.model.ListOfVideos
import com.example.nitrixintership.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val listVideos: MutableLiveData<ListOfVideos> = MutableLiveData()

    fun getVideos() = viewModelScope.launch(Dispatchers.IO) {
        videosGet()
    }

    private suspend fun videosGet() {
        val response = repository.remote.getVideos()
        val request: (Response<ListOfVideos>) -> ListOfVideos? = ::apiRequest
        listVideos.postValue(request(response))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
    }

    private fun apiRequest(response: Response<ListOfVideos>): ListOfVideos? {
        return response.body()
    }

}