package com.example.nitrixintership.repository

import com.example.nitrixintership.repository.localdata.LocalDataSource
import com.example.nitrixintership.repository.networkdata.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val local = localDataSource
    val remote = remoteDataSource
}