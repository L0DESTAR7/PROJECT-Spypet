package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.QRformColor
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.strokeQR


class FormDevice {

    var name by mutableStateOf("")


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun onSubmit(navController: NavHostController) {
       //
    }



    // The famous button


    @Composable
    fun ScanButton(navController: NavHostController) {

        var clicked by remember { mutableStateOf(false) }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp)
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(132.dp, 26.dp)
                    .clip(shape = RoundedCornerShape(6.dp)),
                colors = ButtonDefaults.buttonColors(Fulvous),
                onClick = {
                    clicked = true
                },
                contentPadding = PaddingValues(top = 0.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.qr),
                    contentDescription = "My Image",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 10.dp),
                )
                Text(
                    text = "SCAN QR",
                    fontSize = 16.sp,
                    letterSpacing = 0.8.sp,
                    color = ColumbiaBlue,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
            }
        }

        if(clicked){
            clicked = false
            onSubmit(navController)
        }


    }


    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormWithDivider(navController: NavHostController) {

    val registrationDevice = remember { FormDevice() }

    /*var code by remember { mutableStateOf("")}
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { granted ->
        hasCamPermission = granted
    }
    )
    LaunchedEffect(key1 = true){
        launcher.launch(Manifest.permission.CAMERA)
    }*/

    Box(
        modifier = Modifier
            .blur(13.dp)
            .size(291.dp, 230.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .border(BorderStroke(1.dp, strokeQR), shape = RoundedCornerShape(25.dp))
            .background(color = QRformColor)
        ,
    ){
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxSize()
                .padding(end = 10.dp),
            verticalArrangement = Arrangement.spacedBy((-10).dp)
        ) {
            Text(
                text = "Set Device Name :",
                color = ColumbiaBlue,
                fontFamily = notosans_regular,
                letterSpacing = 1.sp,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 15.dp)
            )
            OutlinedTextField(
                value = registrationDevice.name,
                onValueChange = { registrationDevice.name = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = ColumbiaBlue,
                    textColor = ColumbiaBlue,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Enter Device Name",
                        color = CadetBlue,
                        fontFamily = notosans_regular,
                        fontSize = 16.sp
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp)
            )

            Divider(color = ColumbiaBlue, thickness = 0.2.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp))

            Text(
                text = "Scan QR code:",
                letterSpacing = 1.sp,
                color = ColumbiaBlue,
                fontFamily = notosans_regular,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 15.dp, top = 15.dp)
            )


            registrationDevice.ScanButton(navController = navController)

            Divider(color = ColumbiaBlue, thickness = 0.2.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp)
            )

            /*AndroidView(factory = {context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder().setTargetResolution(Size(
                    previewView.width,
                    previewView.height
                ))
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer {
                        result -> run {
                        code = result
                    }
                        try {
                           cameraProviderFuture.get().bindToLifecycle(
                             lifecycleOwner,
                               selector,
                               preview,
                               imageAnalysis
                           )
                        } catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                )
                        previewView
            },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = code,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(32.dp)
            )*/
        }



    }

}
