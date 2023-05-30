package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MyApp() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "SignIn") {
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
            composable(
                "main settings/{token}",
                arguments = listOf(navArgument("token") { type = NavType.StringType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val token = arguments.getString("token")
                if (token != null) {
                    mainSettings(navController, token)
                }
            }
            composable("test"){ test()}
        }



    }
}









