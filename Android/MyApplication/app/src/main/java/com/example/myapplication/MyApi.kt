package com.example.myapplication

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {
    @POST("signup")
    suspend fun registerUser(@Body requestBody: RequestBody): Response<RegistrationResponse>
    @POST("signin")
    suspend fun checkUser(@Body requestBody: RequestBody):Response<TokenResponse>
}

data class RegistrationResponse(
    val success: Boolean,
    val msg: String
)

data class TokenResponse(
    val success:Boolean,
    val token:String
)