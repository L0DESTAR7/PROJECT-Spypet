package com.example.myapplication

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MyApi {

    //Authentication
    @POST("signup")
    suspend fun registerUser(@Body requestBody: RequestBody): Response<RegistrationResponse>
    @POST("signin")
    suspend fun checkUser(@Body requestBody: RequestBody):Response<TokenResponse>

    //Getting devices
    @GET("devices")
    suspend fun getDevices(@Header("Authorization") token: String): Response<DevicesResponse>

    //Posting an order
    @POST("orders")
    suspend fun sendOrder(@Body requestBody: RequestBody, @Header("Authorization") token: String): Response<OrderResponse>

}

data class RegistrationResponse(
    val success: Boolean,
    val msg: String
)

data class TokenResponse(
    val success:Boolean,
    val token:String
)

data class DevicesResponse(
    val devices: List<Device>
)

data class Device(
    val id: String,
    val name: String,
    val model: String,
    val user_id: String
)

data class OrderResponse(
    val type: String,
    val params: Int,
    val programmedAt: String,
    val periodicity: String
)
