package com.notes.taskapp.models

data class ApiData(
    val app_center: List<AppCenter>?=null,
    val `data`: List<Data>?=null,
    val home: List<Home>?=null,
    val message: String?=null,
    val more_apps: List<MoreApp>?=null,
    val native_add: NativeAdd?=null,
    val status: Int?=null,
    val translator_ads_id: TranslatorAdsId?=null
)