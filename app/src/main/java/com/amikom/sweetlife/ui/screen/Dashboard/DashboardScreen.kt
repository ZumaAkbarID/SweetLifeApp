package com.amikom.sweetlife.ui.screen.Dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amikom.sweetlife.R

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val dashboardData by viewModel.dashboardData.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.fetchDashboard()
    }
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        dashboardData?.data?.let { data ->
            DashboardScreenUI(data)
        } ?: Text("Failed to load data", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun DashboardScreenUI(data: Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        UserHeader(
            name = data.user?.name.orEmpty(),
            diabetesType = data.user?.diabetesType.orEmpty()
        )

        if (data.dailyProgress != null) {
            DailyProgressCard(
                glucose = data.dailyProgress.glucose ?: Glucose(),
                calorie = data.dailyProgress.calorie ?: Calorie()
            )
        } else {
            Text("Daily progress data is not available.")
        }

        if (data.status != null) {
            StatusCard(
                satisfaction = data.status.satisfaction.orEmpty()
            )
        } else {
            Text("Status data is not available.")
        }
    }
}


// nama dan tipe diabetes
@Composable
private fun UserHeader(
    name: String,
    diabetesType: String
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
        Text(
            text = "Diabetes $diabetesType",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

// daily progress
@Composable
private fun DailyProgressCard(
    glucose: Glucose,
    calorie: Calorie
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
                text = "Your Daily Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            ProgressItem(
                title = "Calorie",
                current = calorie.current ?: 0,
                target = calorie.target ?: 0,
                percentage = calorie.percentage ?: 0,
                satisfaction = calorie.satisfaction.orEmpty()
            )
            ProgressItem(
                title = "Glucose",
                current = glucose.current ?: 0,
                target = glucose.target ?: 0,
                percentage = glucose.percentage ?: 0,
                satisfaction = glucose.satisfaction.orEmpty()
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
        modifier = Modifier.fillMaxWidth(),
        progress = percentage / 100f,
        //belom tau nilai satisfactionya apa
        color = when (satisfaction) {
            "Good" -> Color.Green
            "Satisfactory" -> Color.Yellow
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
                "Stable" -> R.drawable.idle
                "Improving" -> R.drawable.gendut
                else -> R.drawable.kurus
            }
        ),
        contentDescription = "Status Icon",
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreenUI(
        data = Data(
            dailyProgress = DailyProgress(
                glucose = Glucose(
                    current = 120,
                    percentage = 60,
                    satisfaction = "Stable",
                    target = 200
                ),
                calorie = Calorie(
                    current = 1500,
                    percentage = 75,
                    satisfaction = "Impoving",
                    target = 2000
                )
            ),
            user = User(
                name = "John Doe",
                diabetesType = "Type 3"
            ),
            status = Status(
                satisfaction = "Stable",
                message = "Keep up the good work"
            )
        )
    )
}