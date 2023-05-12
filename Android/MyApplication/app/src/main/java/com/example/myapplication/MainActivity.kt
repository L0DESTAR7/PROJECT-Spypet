package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.dto.PostRequest
import com.example.myapplication.data.dto.PostResponse
import com.example.myapplication.data.dto.PostsService
import kotlinx.coroutines.async


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                    }
                }
            }
        }
    }

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "SignUp") {
        composable("SignIn") { SignIn(navController) }
        composable("SignUp") { SignUp(navController) }
    }

    val coroutineScope = rememberCoroutineScope()
    val service = PostsService.create()

    val postsState = produceState<PostResponse?>(initialValue = PostResponse(success = false, msg = "")) {
        val result = coroutineScope.async { service.createPost(PostRequest( name = "walidox", password = "allo", email = "walid.lam09@gmail.com"))}
        value = result.await()
    }

}





