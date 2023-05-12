package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest (
    val email:String,
    val name:String,
    val password:String
    )