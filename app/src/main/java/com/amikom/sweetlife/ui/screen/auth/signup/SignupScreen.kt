package com.amikom.sweetlife.ui.screen.auth.signup

import android.util.Log
import android.util.Patterns
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.CustomDialog
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController,
    event: (SignUpEvent) -> Unit
) {
    val context = LocalContext.current

    val signUpResult by viewModel.signUpResult.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    var email by remember { mutableStateOf("") }
    var isErrorEmail by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    val showDialog = remember { mutableStateOf(false) }
    var icon by remember { mutableStateOf(R.drawable.baseline_info_outline_24) }
    var title by remember { mutableStateOf("Failed to Register") }
    var message by remember { mutableStateOf("Internal Error!") }
    var buttons by remember { mutableStateOf(emptyList<Pair<String, () -> Unit>>()) }

    var hasShownError by remember { mutableStateOf(false) }
    var hasShownSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(
                text = "Sign Up To ",
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
            shape = RoundedCornerShape(15.dp),
            label = { Text("Email address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
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
                isPasswordError = it.length < 8
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Lock,
                        contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = isPasswordError
        )
        if (isPasswordError) {
            Text(
                text = "Password must be at least 8 characters",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                isConfirmPasswordError = it != password
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            label = { Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Default.Lock else Icons.Default.Lock,
                        contentDescription = if (isConfirmPasswordVisible) "Hide Confirm Password" else "Show Confirm Password"
                    )
                }
            },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = isConfirmPasswordError
        )
        if (isConfirmPasswordError) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        // Sign Up Button
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                    !isErrorEmail && !isPasswordError && !isConfirmPasswordError
                ) {
                    event(SignUpEvent.LoginProcess(email, password, confirmPassword))
                }
            },
            enabled = signUpResult !is Result.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBlue
            )
        ) {
            if (signUpResult is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Sign Up")
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
            }
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
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Already have an account? ",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Sign In",
                color = MainBlue,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    navController.navigate(Route.LoginScreen) {
                        popUpTo<Route.LoginScreen> { inclusive = true }
                    }
                }
            )
        }

        when (signUpResult) {
            is Result.Success -> {
                if (!hasShownSuccess) {
                    showDialog.value = true
                    icon = R.drawable.baseline_check_circle_outline_24
                    title = "Success!"
                    message = "Congratulations! now you can login"
                    buttons = listOf(
                        "Ok" to {
                            navController.navigate(Route.LoginScreen) {
                                popUpTo<Route.LoginScreen> { inclusive = true }
                            }

                            showDialog.value = false
                        },
                    )
                    hasShownSuccess = true
                }
            }

            is Result.Error -> {
                if (!hasShownError) {
                    val errorMessage = (signUpResult as Result.Error).error

                    if (errorMessage.contains("Key:", ignoreCase = true)) {
                        showDialog.value = true
                        message =
                            "Make sure fill all field!"
                        buttons = listOf(
                            "Ok" to { showDialog.value = false },
                        )
                    } else if (errorMessage == "email already registered") {
                        showDialog.value = true
                        message = "Email already registered!"
                        buttons = listOf(
                            "Ok" to { showDialog.value = false },
                            "Forgot password" to {
                                // Navigasi ke forgot password

                                showDialog.value = false
                            }
                        )
                    } else if (errorMessage == "Passwords do not match") {
                        showDialog.value = true
                        message = "Passwords do not match"
                        buttons = listOf("Ok" to { showDialog.value = false })
                    }
                    hasShownError = true
                }
            }

            else -> {
                // Loading sudah ditangani di button
            }
        }
    }

    if (showDialog.value) {
        CustomDialog(
            openDialogCustom = showDialog,
            icon = icon,
            title = title,
            message = message,
            buttons = buttons,
            dismissOnBackdropClick = signUpResult !is Result.Success
        )
    }

    LaunchedEffect(signUpResult) {
        if (signUpResult !is Result.Error) hasShownError = false
        if (signUpResult !is Result.Success) hasShownSuccess = false
    }

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate(Route.HomeScreen) {
                popUpTo<Route.HomeScreen> { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}
