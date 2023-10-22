package com.notes.taskapp.baseApplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        lateinit var mInstance: BaseApplication
        fun getContext(): Context? {
            return mInstance.applicationContext
        }
    }
}