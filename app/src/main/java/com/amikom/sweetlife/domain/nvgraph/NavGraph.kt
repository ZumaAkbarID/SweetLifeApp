package com.amikom.sweetlife.domain.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.amikom.sweetlife.ui.presentation.onboarding.OnBoardingScreen
import com.amikom.sweetlife.ui.presentation.onboarding.OnBoardingViewModel
import com.amikom.sweetlife.ui.screen.auth.login.LoginScreen
import com.amikom.sweetlife.ui.screen.auth.login.LoginViewModel
import com.amikom.sweetlife.ui.screen.auth.signup.SignUpViewModel
import com.amikom.sweetlife.ui.screen.auth.signup.SignupScreen
import com.amikom.sweetlife.ui.screen.home.HomeScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Route.OnboardingScreen> {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(event = viewModel::onEvent)
        }

        composable<Route.HomeScreen> {
            HomeScreen()
        }

        composable<Route.LoginScreen> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(event = loginViewModel::onEvent, navController = navController)
        }

        composable<Route.SignUpScreen> {
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignupScreen(event = signUpViewModel::onEvent, navController = navController)
        }
    }
}