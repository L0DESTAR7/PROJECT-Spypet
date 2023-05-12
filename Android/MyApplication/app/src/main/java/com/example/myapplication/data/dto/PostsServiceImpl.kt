package com.example.myapplication.data.dto

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType


class PostsServiceImpl(
    private val client: HttpClient
) : PostsService {

    override suspend fun getPosts(): List<PostResponse> {
        return try {
            client.get{
            url(HttpRoutes.POSTS)
        }   }
            catch(e : RedirectResponseException){
                Log.d("redirect response Exception","${e.response.status.description}")
                emptyList()
            }
            catch(e : ClientRequestException){
                Log.d("client request Exception","${e.response.status.description}")
                emptyList()
            }
            catch(e : ServerResponseException){
                Log.d("Server Response Exception","${e.response.status.description}")
                emptyList()
        }
            catch(e : Exception){
                Log.d("Exception","${e.message}")
                emptyList()
            }
        }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse>{
                url(HttpRoutes.SignUp)
                contentType(ContentType.Application.Json)
                body = PostRequest
            }   }
        catch(e : RedirectResponseException){
            Log.d("redirect response Exception","${e.response.status.description}")
            null
        }
        catch(e : ClientRequestException){
            Log.d("client request Exception","${e.response.status.description}")
            null
        }
        catch(e : ServerResponseException){
            Log.d("Server Response Exception","${e.response.status.description}")
            null
        }
        catch(e : Exception){
            Log.d("Exception","${e.message}")
            null
        }
    }
}