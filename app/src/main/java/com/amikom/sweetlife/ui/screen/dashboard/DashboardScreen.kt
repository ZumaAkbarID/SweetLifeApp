package com.amikom.sweetlife.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val dashboardData by viewModel.dashboardState.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    if (!isUserLoggedIn) {
        CustomDialog(
            icon = R.drawable.baseline_info_outline_24,
            title = "Info",
            message = "You'r session is ended. Please login again",
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
            Text(
                text = (dashboardData as Result.Error).error,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textAlign = TextAlign.Center
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
            BottomNavigationBar(buttons = buttons)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Log.d("DashboardScreenUI", "data: $data")
            UserHeader(
                name = data.user.name,
                diabetesType = data.user.diabetesType.toString(),
                isDiabete = data.user.diabetes
            )

            DailyProgressCard(
                glucose = data.dailyProgress.glucose,
                calorie = data.dailyProgress.calorie,
                carbohydrate = data.dailyProgress.carbs
            )

            StatusCard(
                satisfaction = data.status.satisfaction
            )
        }
    }
}


// nama dan tipe diabetes
@Composable
private fun UserHeader(
    name: String,
    diabetesType: String,
    isDiabete: String
) {
    var expanded by remember { mutableStateOf(false) }

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
        if (isDiabete == "true") {
            expanded = true
            Text(
                text = "Diabetes Type: $diabetesType",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// daily progress
@Composable
private fun DailyProgressCard(
    glucose: ProgressDetail,
    calorie: ProgressDetail,
    carbohydrate: ProgressDetail,
) {
    Log.d("DailyProgressCard", "glucose: $glucose, calorie: $calorie, carbohydrate: $carbohydrate")
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
                current = calorie.current,
                target = calorie.target,
                percentage = calorie.percentage,
                satisfaction = calorie.satisfaction
            )
            ProgressItem(
                title = "🧋 Glucose",
                current = glucose.current,
                target = glucose.target,
                percentage = glucose.percentage,
                satisfaction = glucose.satisfaction
            )
            ProgressItem(
                title = "🍚 Carbohydrate",
                current = carbohydrate.current,
                target = carbohydrate.target,
                percentage = carbohydrate.percentage,
                satisfaction = carbohydrate.satisfaction
            )
        }
    }
}

// progress bar
@Composable
private fun ProgressItem(
    title: String,
    current: Double,
    target: Double,
    percentage: Double,
    satisfaction: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Log.d("ProgressItem", "satisfaction: ${ProgressDetail(current, percentage, satisfaction, target)}")
        Text(text = title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Text(
            text = if (title == "🔥 Calorie") "$current / $target Kcal" else if (title == "🧋 Glucose") "$current / $target mg" else "$current / $target g",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth().height(8.dp),
        progress = percentage.toFloat() / 100,
        //belom tau nilai satisfactionya apa
        color = when (satisfaction) {
            "PAS" -> Color.Green
            "UNDER" -> Color.Yellow
            else -> Color.Red
        }
    )
}

// gambar laknat
@Composable
private fun StatusCard(
    satisfaction: String,
) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxSize(),
        painter = painterResource(
            //belom tau nilai satisfac apa
            id = when (satisfaction) {
                "PAS" -> R.drawable.idle
                "OVER" -> R.drawable.gendut
                "MODERATE"-> R.drawable.kurus
                else -> R.drawable.idle
            }
        ),
        contentDescription = "Status Icon",
    )
}