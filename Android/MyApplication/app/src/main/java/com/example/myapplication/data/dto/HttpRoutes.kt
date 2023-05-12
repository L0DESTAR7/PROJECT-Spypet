package com.example.myapplication.data.dto

object HttpRoutes {
    private const val BASE_URL = "http://localhost:5000"
    const val POSTS = "$BASE_URL/posts"
    const val SignUp = "$BASE_URL/signup"
    const val SignIn = "$BASE_URL/signin"
}