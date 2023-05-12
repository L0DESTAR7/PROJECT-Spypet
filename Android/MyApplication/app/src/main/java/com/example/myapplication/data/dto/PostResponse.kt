package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse (
    val success:Boolean,
    val msg:String
)