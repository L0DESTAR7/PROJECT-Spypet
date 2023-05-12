package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.gradientBottom
import com.example.myapplication.ui.theme.gradientTop
import com.example.myapplication.ui.theme.montserrat
import com.example.myapplication.ui.theme.notosans_light
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.notosans_semibold
import com.example.myapplication.ui.theme.textColorInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavHostController) {


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
            registrationData.MyButton()
        }
    }
}