package com.notes.taskapp.models

data class MoreApp(
    val catgeory: String?=null,
    val id: Int?=null,
    val is_active: Int?=null,
    val name: String?=null,
    val position: Int?=null,
    val sub_category: List<SubCategoryXX>?=null
)