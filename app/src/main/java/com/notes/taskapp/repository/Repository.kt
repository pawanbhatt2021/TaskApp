package com.notes.taskapp.repository

import com.notes.taskapp.retrofit.ApiService
import com.notes.taskapp.retrofit.utils.result
import javax.inject.Inject

class Repository
@Inject
constructor(private val apiService: ApiService){

    fun getGameData()= result {
        apiService.getGameData()
    }
}