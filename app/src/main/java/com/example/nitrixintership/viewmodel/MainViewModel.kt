package com.example.nitrixintership.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nitrixintership.repository.Repository
import com.example.nitrixintership.repository.networkdata.RemoteDataSource

class MainViewModel constructor(
    private val repository: Repository,
    application: Application
):AndroidViewModel(application) {

}