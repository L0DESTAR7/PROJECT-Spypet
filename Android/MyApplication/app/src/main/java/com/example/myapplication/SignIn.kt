package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.botGray
import com.example.myapplication.ui.theme.gradientBottom
import com.example.myapplication.ui.theme.gradientTop
import com.example.myapplication.ui.theme.montserrat
import com.example.myapplication.ui.theme.notosans_light
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.notosans_semibold
import com.example.myapplication.ui.theme.orbitron_medium
import com.example.myapplication.ui.theme.textColorInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Handler

class SignIn  {

    var email by mutableStateOf("")
    var password by mutableStateOf("")


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun onSubmit(navController: NavHostController, context: Context) {

        GlobalScope.launch { checkUserTest(navController,email,password, context = context) }

    }


    @Composable
    fun MyButton(navController: NavHostController, context: Context) {
        var clicked by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)

        ) {
            OutlinedButton(
                modifier = Modifier.align(Alignment.Center).clip(shape = RoundedCornerShape(20.dp)).background(brush = Brush.verticalGradient(colors = listOf(
                    gradientTop,
                    gradientBottom))),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                onClick = {
                    clicked = true
                },
                //brush = Brush.verticalGradient(colors = listOf(gradientTop, gradientBottom)),

            ) {
                Text(
                    text = "Login",
                    fontSize = 19.sp,
                    fontFamily = orbitron_medium,
                    color = Fulvous,
                    textAlign = TextAlign.Center
                )
            }
        }

        if(clicked){
            clicked = false
            onSubmit(navController, context = context)
        }


    }

        }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavHostController) {


    val sign = remember { SignIn() }
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientTop,
                        gradientBottom
                    )
                )
            )
    ) {
        // Outer container with margin
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp)
        ) {
            // Row containing image and text
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ellipse component with margin
                //Ellipse(modifier = Modifier.padding(end = 16.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "My Image",
                    modifier = Modifier
                        .size(40.dp)
                )
                // Text component with margin
                Text(
                    text = "Spypet",
                    color = ColumbiaBlue,
                    fontSize = 24.sp,
                    //fontWeight = FontWeight.W300,
                    fontFamily = montserrat,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            // Container with two texts
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy((-12).dp)
            ) {
                Text(
                    text = "Welcome Back !",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(
                    text = "Hello again, you've been missed!",
                    color = CadetBlue,
                    fontFamily = notosans_regular,
                    fontSize = 20.sp
                )
            }
            // Container with text and text field
            Column(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth()
                    .padding(end = 80.dp),
                verticalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Text(
                    text = "Email Address",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = sign.email,
                    onValueChange = { sign.email = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Fulvous,
                        textColor = CadetBlue,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Enter your email",
                            color = CadetBlue,
                            fontFamily = notosans_regular,
                            fontSize = 16.sp
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColorInput, RoundedCornerShape(14.dp))
                        .size(55.dp)
                        .padding(0.dp)

                )
            }
            // Container with text and text field
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(end = 80.dp),
                verticalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Text(
                    text = "Password",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = sign.password,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Fulvous,
                        textColor = CadetBlue,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    onValueChange = { sign.password = it},
                    placeholder = {
                        Text(
                            text = "Enter a password",
                            color = CadetBlue,
                            fontFamily = notosans_light,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )

                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(if (passwordVisibility) R.drawable.eye_close else R.drawable.eye_open),
                                contentDescription = "Password visibility",
                                tint = if (passwordVisibility) ColumbiaBlue else Fulvous
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColorInput, RoundedCornerShape(14.dp))
                        .size(55.dp)
                        .padding(0.dp)
                )
            }
          sign.MyButton(navController = navController, context = context)

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 60.dp, bottom = 27.dp),
        ) {
            Text(
                text = "Don't have an account?",
                color = botGray,
                fontFamily = notosans_semibold,
                fontSize = 16.sp
            )
            Text(
                text = "Sign Up",
                color = Fulvous,
                fontFamily = notosans_semibold,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        // Navigate to SignIn screen
                        navController.navigate("SignUp")
                    }
                    .padding(start = 10.dp)
            )
        }
    }
}

suspend fun checkUserTest(navController: NavHostController, email: String, password: String, context: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.10:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApi = retrofit.create(MyApi::class.java)

    val requestBody = JSONObject().apply {
        put("email", email)
        put("password", password)
    }

    val requestBodyObject =
        RequestBody.create(MediaType.parse("application/json"), requestBody.toString())

    val response = withContext(Dispatchers.IO) {
        myApi.checkUser(requestBodyObject)
    }

    if (response.code() == 200) {
        val tokenResponse = response.body()
        tokenResponse?.let { token ->
            Log.d("success", "Token: ${token.token}")
            withContext(Dispatchers.Main) {
                val tokenValue = token.token
                navController.navigate("Devices/${tokenValue}")
            }
        }
    } else if (response.code() == 409 || response.code() == 500 || response.code() == 403) {
        // Handle specific error codes (e.g., display an error message)
                val errorMessage = response.errorBody()?.string() ?: "An error occurred"
        android.os.Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, errorMessage.split(",")[1].split(":")[1].substring(1), Toast.LENGTH_SHORT).show()
        }
                //navController.navigate("Devices")
            }

    }


