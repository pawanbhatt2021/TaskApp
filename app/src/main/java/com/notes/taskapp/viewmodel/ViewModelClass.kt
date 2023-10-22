package com.notes.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import com.notes.taskapp.models.ApiData
import com.notes.taskapp.repository.Repository
import com.notes.taskapp.retrofit.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class ViewModelClass
@Inject
constructor(private val repository: Repository) : ViewModel() {
    fun getGameData(): Flow<ApiResponse<ApiData>> = repository.getGameData()
}