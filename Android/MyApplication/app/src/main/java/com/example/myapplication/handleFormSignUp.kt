package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Fulvous
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern


data class ValidationResult(val showToast: Boolean, val toastMessage: String)

val emailRegexPattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

val nameRegexPattern = Pattern.compile("^[a-zA-Z0-9]+\$")


// Define Validators
fun validateEmail(email: String): Boolean {
    return emailRegexPattern.matcher(email).matches()
}
fun validateName(name: String) : Boolean{
    return nameRegexPattern.matcher(name).matches()
}



fun validateForm(email: String, password: String, confirmPassword: String, name: String): ValidationResult {
    var showToast = false
    var toastMessage = ""

    if (password != confirmPassword) {
        showToast = true
        toastMessage = "Passwords do not match"
    }

    if (!validateEmail(email)) {
        showToast = true
        toastMessage = "Email address is not valid"
    }

    if(!validateName(name)){
        showToast = true
        toastMessage = "Name is not Valid"
    }


    // Perform the registration logic here
    // ...

    return ValidationResult(showToast, toastMessage)
}

@Composable
fun showToast(message: String?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(message) {
        coroutineScope.launch {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


