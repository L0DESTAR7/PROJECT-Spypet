package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                /*var code by remember { mutableStateOf("") }
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

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { granted ->
                        hasCamPermission = granted
                    }
                )
                LaunchedEffect(key1 = true) {
                    launcher.launch(Manifest.permission.CAMERA)
                }*/
                //A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
            /*Column(modifier = Modifier.fillMaxSize()) {

                    if (hasCamPermission) {
                        AndroidView(
                            factory = { context ->
                                val previewView = PreviewView(context)
                                val preview = Preview.Builder().build()
                                val selector = CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()
                                preview.setSurfaceProvider(previewView.surfaceProvider)
                                val imageAnalysis = ImageAnalysis.Builder().setTargetResolution(
                                    Size(
                                        previewView.width,
                                        previewView.height
                                    )
                                )
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()
                                imageAnalysis.setAnalyzer(
                                    ContextCompat.getMainExecutor(context),
                                    QrCodeAnalyzer { result ->
                                        run {
                                            code = result
                                        }
                                        try {
                                            cameraProviderFuture.get().bindToLifecycle(
                                                lifecycleOwner,
                                                selector,
                                                preview,
                                                imageAnalysis
                                            )
                                        } catch (e: Exception) {
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
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth().padding(32.dp)
                        )
                    }
                }
                }
            }*/
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MyApp() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "SignUp") {
            composable("SignIn") { SignIn(navController) }
            composable("SignUp") { SignUp(navController) }
            composable(
                "Devices/{token}",
                arguments = listOf(navArgument("token") { type = NavType.StringType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val token = arguments.getString("token")
                Devices(navController, token)
            }
            composable("FormWithDivider") { FormWithDivider(navController) }
            composable("main settings"){mainSettings(navController)}
        }



    }
}









