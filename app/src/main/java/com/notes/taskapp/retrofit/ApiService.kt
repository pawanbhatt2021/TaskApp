package com.notes.taskapp.retrofit

import com.notes.taskapp.models.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
companion object{
    const val BASE_URL="http://vasundharaapps.com/"
}

@GET("artwork_apps/api/AdvertiseNewApplications/17/com.hd.camera.apps.high.quality")
suspend fun getGameData():Response<ApiData>
}