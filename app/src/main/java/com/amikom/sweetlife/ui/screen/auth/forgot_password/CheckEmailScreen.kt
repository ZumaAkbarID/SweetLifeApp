package com.amikom.sweetlife.ui.screen.auth.forgot_password

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun CheckEmailScreen(
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Check Your Email",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "we have sent a password recover instruction\n" +
                    "to your email",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(0.dp, 10.dp)
        )

        // Continue Button
        Button(
            onClick = {
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
            },
            modifier = Modifier.padding(top = 16.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBlue
            )
        ) {
            Text("Open Email App")
        }
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable(onClick = {
                    navController.navigate(Route.LoginScreen) {
                        popUpTo<Route.LoginScreen> { inclusive = true }
                        launchSingleTop = true
                    }
                }),
            text = "Skip, I’ll confirm later",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}