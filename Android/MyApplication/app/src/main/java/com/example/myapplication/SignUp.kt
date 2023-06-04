package com.example.myapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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


class SignUp  {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")


    // The famous button
    @Composable
    fun MyButton(navController: NavHostController) {
        var clicked by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    clicked = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = gradientTop)
            ) {
                Text(
                    text = "Register",
                    fontSize = 19.sp,
                    color = Fulvous,
                    fontFamily = orbitron_medium
                )
            }
        }

        if(clicked){
            clicked = false
            onSubmit(navController)
        }


    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun onSubmit(navController: NavHostController) {
        // Do something with the form data
        val validationResult = validateForm(email, password, confirmPassword, name)

        // Use the returned object
        if (validationResult.showToast) {
            showToast(validationResult.toastMessage)
        } else {
            name = name.capitalize()
            // Proceed with the registration logic

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.100.10:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val myApi = retrofit.create(MyApi::class.java)

            val requestBody = JSONObject().apply {
                put("name", name)
                put("email", email)
                put("password", password)
            }

            val requestBodyObject =
                RequestBody.create(MediaType.parse("application/json"), requestBody.toString())

            GlobalScope.launch {
                val response = withContext(Dispatchers.IO) {
                    myApi.registerUser(requestBodyObject)
                }
                if (response.code() == 200) {
                    Log.d("success", "${response.body()}")
                    withContext(Dispatchers.Main){
                        navController.navigate("SignIn")
                    }
                }
                if (response.code() == 409 || response.code() == 500) {
                    //showToast(response.errorBody()?.string() ?: "An error occurred")
                    //navController.navigate("Devices")
                }
            }
        }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavHostController) {


    val registrationData = remember { SignUp() }
    var passwordVisibility by remember { mutableStateOf(false) }

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
                    text = "Create an account",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(
                    text = "Connect with your pets today !",
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
                    value = registrationData.email,
                    onValueChange = { registrationData.email = it },
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
                    text = "Name",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = registrationData.name,
                    onValueChange = { registrationData.name = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Fulvous,
                        textColor = CadetBlue,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Enter your name",
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
                    value = registrationData.password,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Fulvous,
                        textColor = CadetBlue,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    onValueChange = { registrationData.password = it},
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
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(end = 80.dp),
                verticalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Text(
                    text = "Re-enter password",
                    color = ColumbiaBlue,
                    fontFamily = notosans_semibold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = registrationData.confirmPassword,
                    onValueChange = { registrationData.confirmPassword = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Fulvous,
                        textColor = CadetBlue,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Confirm Password",
                            color = CadetBlue,
                            fontFamily = notosans_light,
                            fontSize = 16.sp
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColorInput, RoundedCornerShape(14.dp))
                        .size(55.dp)
                        .padding(0.dp)
                )
            }
            registrationData.MyButton(navController)
            // Container with two texts

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 55.dp, bottom = 27.dp),
        ) {
            Text(
                text = "Already have an account?",
                color = botGray,
                fontFamily = notosans_semibold,
                fontSize = 16.sp
            )
            Text(
                text = "Sign In",
                color = Fulvous,
                fontFamily = notosans_semibold,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        // Navigate to SignIn screen
                        navController.navigate("SignIn")
                    }
                    .padding(start = 10.dp)
            )
        }

    }

}






