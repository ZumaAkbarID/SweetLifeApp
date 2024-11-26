package com.amikom.sweetlife.ui.screen.auth.login

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.CustomDialog
import com.amikom.sweetlife.ui.theme.MainBlue
import com.amikom.sweetlife.util.showToastMessage


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
    event: (LoginEvent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isErrorEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isErrorPassword by remember { mutableStateOf(false) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    val loginResult by viewModel.loginResult.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    val context = LocalContext.current
    var hasShownError by remember { mutableStateOf(false) }
    var hasShownSuccess by remember { mutableStateOf(false) }

    val showDialog = remember { mutableStateOf(false) }
    var icon by remember { mutableStateOf(R.drawable.baseline_info_outline_24) }
    var title by remember { mutableStateOf("Failed to Login") }
    var message by remember { mutableStateOf("Account not found!") }
    var buttons by remember { mutableStateOf(emptyList<Pair<String, () -> Unit>>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(
                text = "Login To ",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "sweetLife",
                color = MainBlue,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
        }
        // Email Address
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isErrorEmail = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = MaterialTheme.shapes.small,
            label = {
                Text(
                    "Email address",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isErrorEmail) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = if (isErrorEmail) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            isError = isErrorEmail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        if (isErrorEmail) {
            Text(
                text = "Invalid email address",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isErrorPassword = it.length < 8
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = MaterialTheme.shapes.small,
            label = {
                Text(
                    "Password",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isErrorPassword) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = if (isErrorPassword) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Warning else Icons.Outlined.Lock,
                        contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            isError = isErrorPassword,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        if (isErrorPassword) {
            Text(
                text = "Password must be at least 8 characters",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        // Sign Up Button
        Button(
            onClick = {
                if (email.isEmpty()) {
                    isErrorEmail = true
                    return@Button
                } else if (password.isEmpty()) {
                    isErrorPassword = true
                    return@Button
                } else if (isErrorEmail || isErrorPassword) {
                    showToastMessage(
                        context,
                        "Please provide correct information!",
                        Toast.LENGTH_LONG
                    )
                    return@Button
                }

                // Memulai proses login
                event(LoginEvent.LoginProcess(email = email, password = password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBlue
            ),
            enabled = loginResult !is Result.Loading // Menonaktifkan tombol saat loading
        ) {
            if (loginResult is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login")
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
            }
        }

        when (loginResult) {
            is Result.Success -> {
                if (!hasShownSuccess) {
                    showToastMessage(context, "Welcome!", Toast.LENGTH_LONG)
                    hasShownSuccess = true
                }
            }

            is Result.Error -> {
                if (!hasShownError) {
                    val errorMessage = (loginResult as Result.Error).error

                    if (errorMessage == "user not verified") {
                        showDialog.value = true
                        message =
                            "We have sent a verify instruction to your email address. Please check and follow the instruction."
                        buttons = listOf(
                            "Ok, I'll do it later" to { showDialog.value = false },
                            "Open Gmail App" to {
                                val emailIntent = Intent(Intent.ACTION_MAIN).apply {
                                    addCategory(Intent.CATEGORY_APP_EMAIL)
                                }

                                if (emailIntent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(emailIntent)
                                } else {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://gmail.com")
                                        )
                                    )
                                }
                                showDialog.value = false
                            }
                        )
                    } else if (errorMessage == "invalid email or password") {
                        showDialog.value = true
                        message = "Account not found!"
                        buttons = listOf(
                            "Ok" to { showDialog.value = false }
                        )
                    }
                    hasShownError = true
                }
            }

            else -> {
                // Loading sudah ditangani di button
            }
        }

        if (showDialog.value) {
            CustomDialog(
                openDialogCustom = showDialog,
                icon = icon,
                title = title,
                message = message,
                buttons = buttons
            )
        }

        // google sign up
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(48.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBlue
            )
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
            Text("  Sign Up with Google Account")
        }
        Row(
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp),
        ) {
            Text(
                text = "Already have an account? ",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sign In",
                color = MainBlue,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    // Navigate to Sign In Screen
                }
            )
        }
        Text(
            text = "Forgot Password?",
            color = MainBlue,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable {
                // Navigate to Forgot Password Screen
            })
    }

    LaunchedEffect(loginResult) {
        if (loginResult !is Result.Error) hasShownError = false
        if (loginResult !is Result.Success) hasShownSuccess = false
    }

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate(Route.HomeScreen) {
                popUpTo(Route.HomeScreen) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}