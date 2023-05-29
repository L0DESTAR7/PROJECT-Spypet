package com.example.myapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.gradientBottom
import com.example.myapplication.ui.theme.gradientTop
import com.example.myapplication.ui.theme.montserrat
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.notosans_semibold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Devices {

    companion object {
        var stateVariable = 0
        val maxContainers = 6 // Maximum number of containers in the list

        var containerList: List<String> by mutableStateOf(emptyList())
    }


    @Composable
    fun ContainerItem(text: String, navController: NavHostController) {

        var showBox by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .border(BorderStroke(0.1.dp, CadetBlue), shape = RoundedCornerShape(5.dp))
                .clickable {
                    if (text == "Empty Slot" && Devices.stateVariable == 0) {
                        showBox = true // Show the box when clicking on an empty slot
                        Devices.stateVariable += 1
                    } else {
                        // Handle container click
                    }
                }
                .padding(16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color = ColumbiaBlue,
                fontFamily = notosans_regular,
                fontSize = 16.sp,
            )
            if (text != "Empty Slot") {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow",
                    tint = ColumbiaBlue,
                    modifier = Modifier.clickable {
                        // Handle arrow click
                        navController.navigate("main settings")
                    }
                )
            }
        }

        if (showBox) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 30.dp)
                    .clickable {
                        showBox = false // Hide the box when clicking on it
                        Devices.stateVariable -= 1
                    }
            ) {
                FormWithDivider(navController = navController)
            // Display the FormWithDivider inside the box
            }
        }
    }

}


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun Devices(navController: NavHostController, token: String?) {

        val devices = remember { Devices() }

        GlobalScope.launch {
            if (token != null) {
                getDevicesTest(navController,token)
            }
        }

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
                        .padding(top = 25.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy((-12).dp)
                ) {
                    Text(
                        text = "Your Devices",
                        color = ColumbiaBlue,
                        fontFamily = notosans_semibold,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = "Owned Spypet devices",
                        color = CadetBlue,
                        fontFamily = notosans_regular,
                        fontSize = 20.sp
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(35.dp)) // Add space above the first item
                        }
                        items(Devices.maxContainers) { index ->
                            val deviceName = if (index < Devices.containerList.size) {
                                Devices.containerList[index]
                            } else {
                                "Empty Slot"
                            }
                            devices.ContainerItem(deviceName, navController)
                            Spacer(modifier = Modifier.height(30.dp)) // Add spacing between items
                        }
                    }
                }
            }
        }
    }

suspend fun getDevicesTest(navController: NavHostController, token: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.10:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val myApi = retrofit.create(MyApi::class.java)

    val response = withContext(Dispatchers.IO) {
        myApi.getDevices("Bearer $token")
    }

    if (response.isSuccessful) {
        val devicesResponse = response.body()
        devicesResponse?.let { devices ->
            withContext(Dispatchers.Main) {
                // Process the devices data as needed
                val deviceNames = devices.devices.map { it.name }
                val emptySlots = List(Devices.maxContainers - deviceNames.size) { "Empty Slot" }
                Devices.containerList = deviceNames + emptySlots
                //navController.navigate("Devices")
            }
        }
    } else {
        // Handle error response
        Log.e("error", "Failed to retrieve devices: ${response.code()}")
    }
}



