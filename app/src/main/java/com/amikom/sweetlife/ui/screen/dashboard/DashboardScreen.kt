package com.amikom.sweetlife.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.model.DashboardModel
import com.amikom.sweetlife.data.model.Data
import com.amikom.sweetlife.data.model.ProgressDetail
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.BottomNavigationBar
import com.amikom.sweetlife.ui.component.CustomDialog
import com.amikom.sweetlife.ui.component.getBottomNavButtons
import com.amikom.sweetlife.ui.component.rememberSelectedIndex
import com.amikom.sweetlife.util.Constants
import java.util.Locale
import kotlin.math.ceil

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val dashboardData by viewModel.dashboardData.observeAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    if (!isUserLoggedIn) {
        CustomDialog(
            icon = R.drawable.baseline_info_outline_24,
            title = "Info",
            message = "Your session is ended. Please login again",
            openDialogCustom = remember { mutableStateOf(true) },
            buttons = listOf(
                "Ok" to {
                    navController.navigate(Route.LoginScreen) {
                        launchSingleTop = true
                    }
                }
            ),
            dismissOnBackdropClick = false
        )
    }

    when (dashboardData) {
        is Result.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Result.Success -> {
            val data = (dashboardData as Result.Success<DashboardModel>).data
            DashboardScreenUI(data.data, navController)
        }

        is Result.Error -> {
//            val data = (dashboardData as Result.Success<DashboardModel>).data
//            DashboardScreenUI(data.data, navController)
            val errorText = (dashboardData as Result.Error).error
            if(errorText == "Unauthorized") {
                navController.navigate(Route.LoginScreen) {
                    launchSingleTop = true
                }
            }

            Text(
                text = errorText,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }

        else -> {
            Text(
                text = "Unknown error",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DashboardScreenUI(data: Data, navController: NavController) {
    Constants.CURRENT_BOTTOM_BAR_PAGE_ID = rememberSelectedIndex()

    val buttons = getBottomNavButtons(Constants.CURRENT_BOTTOM_BAR_PAGE_ID, navController)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(buttons = buttons, navController = navController, currentScreen = Route.DashboardScreen)
        },
        modifier = Modifier.fillMaxSize().navigationBarsPadding(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserHeader(
                name = data.user.name,
                isDiabetes = data.user.diabetes
            )

            DailyProgressCard(
                calories = data.dailyProgress.calories,
                carbs = data.dailyProgress.carbs,
                sugar = data.dailyProgress.sugar,
            )

            StatusCard(
                satisfaction = data.status.satisfaction,
                message = data.status.message
            )
        }
    }
}


// nama dan tipe diabetes
@Composable
private fun UserHeader(
    name: String,
    isDiabetes: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Hi, $name!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        if(isDiabetes) {
            Text(
                text = "Has Diabetes",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// daily progress
@Composable
private fun DailyProgressCard(
    calories: ProgressDetail,
    carbs: ProgressDetail,
    sugar: ProgressDetail,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🧋 Your Daily Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            ProgressItem(
                title = "🔥 Calorie",
                current = ceil(calories.current).toInt(),
                target = ceil(calories.target).toInt(),
                percentage = ceil(calories.percent).toInt(),
                satisfaction = calories.satisfaction
            )
            ProgressItem(
                title = "🍚 Carb",
                current = ceil(carbs.current).toInt(),
                target = ceil(carbs.target).toInt(),
                percentage = ceil(carbs.percent).toInt(),
                satisfaction = carbs.satisfaction
            )
            ProgressItem(
                title = "🧋 Sugar",
                current = ceil(sugar.current).toInt(),
                target = ceil(sugar.target).toInt(),
                percentage = ceil(sugar.percent).toInt(),
                satisfaction = sugar.satisfaction
            )
        }
    }
}

// progress bar
@Composable
private fun ProgressItem(
    title: String,
    current: Int,
    target: Int,
    percentage: Int,
    satisfaction: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Text(
            text = "$current / $target Ccal",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth().height(16.dp),
        progress = percentage / 100f,
        //belom tau nilai satisfactionya apa
        color = when (satisfaction) {
            "PASS" -> MaterialTheme.colorScheme.onBackground
            "OVER" -> MaterialTheme.colorScheme.onBackground
            else -> Color.Yellow
        }
    )
}

// gambar laknat
@Composable
private fun StatusCard(
    satisfaction: String,
    message: String
) {
    Text(
        text = message,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().
        padding(16.dp, 16.dp, 16.dp, 0.dp),
        color = when(satisfaction) {
            "PASS" -> MaterialTheme.colorScheme.onBackground
            "UNDER" -> MaterialTheme.colorScheme.onBackground
            else -> Color.Red
        },
        style = MaterialTheme.typography.titleMedium
    )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxSize(),
        painter = painterResource(
            //belom tau nilai satisfac apa
            id = when (satisfaction) {
                "PASS" -> R.drawable.idle
                "OVER" -> R.drawable.gendut
                else -> R.drawable.kurus
            }
        ),
        contentDescription = "Status Icon",
    )
}