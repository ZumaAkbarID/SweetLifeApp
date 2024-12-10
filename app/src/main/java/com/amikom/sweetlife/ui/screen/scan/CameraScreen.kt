package com.amikom.sweetlife.ui.screen.scan

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.amikom.sweetlife.R
import java.io.File
import java.util.concurrent.Executors

@Composable
fun CameraScreen(onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isFrontCamera by remember { mutableStateOf(false) }
    var isFlashOn by remember { mutableStateOf(false) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val previewView = remember { PreviewView(context) }

    var camera: Camera? by remember { mutableStateOf(null) }
    var showDialog by remember { mutableStateOf(false) }
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Bind Kamera ke Lifecycle Owner
    fun bindCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = if (isFrontCamera) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                preview.setSurfaceProvider(previewView.surfaceProvider)
                Log.d("CameraScreen", "Camera bound successfully: $camera")
            } catch (e: Exception) {
                Log.e("CameraScreen", "Failed to bind camera", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    DisposableEffect(Unit) {
        bindCamera()
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // Handle tombol back
    BackHandler(onBack = onBackPressed)

    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        // Preview Kamera
        if (capturedImageBitmap == null) {
            // Gunakan modifier untuk memastikan preview kamera memenuhi layar
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize().aspectRatio(0.75f, false)
            )
        } else {
            // Tampilkan Gambar yang diambil dengan ukuran penuh (fillMaxSize)
            Image(
                bitmap = capturedImageBitmap!!.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Gambar Panduan (Guide) di Tengah
        Image(
            painter = painterResource(id = R.drawable.scan_guide),
            contentDescription = "Camera Guide",
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp)
        )

        // Tombol Flash dan Flip Kamera
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                isFlashOn = !isFlashOn
                camera?.cameraControl?.enableTorch(isFlashOn)
            }) {
                Icon(
                    painter = painterResource(
                        if (isFlashOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off
                    ),
                    contentDescription = "Flash Toggle",
                    tint = Color.White
                )
            }

            IconButton(onClick = {
                isFrontCamera = !isFrontCamera
                bindCamera()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_flip_camera),
                    contentDescription = "Flip Camera",
                    tint = Color.White
                )
            }
        }

        // Tombol Capture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    imageCapture.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                // Konversi gambar ke Bitmap
                                val bitmap = imageProxyToBitmap(image)
                                capturedImageBitmap = bitmap
                                showDialog = true // Tampilkan dialog konfirmasi
                                image.close()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("CameraScreen", "Photo capture failed", exception)
                            }
                        }
                    )
                },
                modifier = Modifier.size(80.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.White, shape = CircleShape)
                )
            }
        }
    }

    // Dialog Konfirmasi
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Foto") },
            text = { Text("Apakah Anda ingin menggunakan foto ini?") },
            confirmButton = {
                TextButton(onClick = {
                    // Lakukan aksi untuk menyimpan foto
                    Log.d("CameraScreen", "Photo saved.")
                    capturedImageBitmap = null // Kembali ke kamera live preview
                    showDialog = false
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    capturedImageBitmap = null // Buang gambar dan kembali ke kamera
                    showDialog = false
                }) {
                    Text("Hapus")
                }
            }
        )
    }
}

private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val buffer = image.planes[0].buffer
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}
