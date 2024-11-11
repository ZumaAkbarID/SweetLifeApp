package com.amikom.sweetlife

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amikom.sweetlife.onboard.OnBoardingScreen
import com.amikom.sweetlife.onboard.OnboardingUtils
import com.amikom.sweetlife.ui.theme.SweetLifeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val onBoardingUtils by lazy { OnboardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            SweetLifeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (onBoardingUtils.isOnboardingCompleted()) {
                        ShowHomeScreen()
                    } else {
                        ShowOnboardingScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun ShowHomeScreen() {
        Column {
            Text(
                text = "Home Screen",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }

    @Composable
    fun ShowOnboardingScreen() {
        val context: Context = LocalContext.current

        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        ) {
            val scope: CoroutineScope = rememberCoroutineScope()

            OnBoardingScreen {
                onBoardingUtils.setOnboardingCompleted()

                scope.launch {
                    setContent {
                        ShowHomeScreen()
                    }
                }
            }
        }
    }
}