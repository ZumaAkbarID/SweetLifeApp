package com.amikom.sweetlife.ui.screen.assesment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(navController: NavController, viewModel: AssessmentViewModel = viewModel()) {
    val pages = listOf<@Composable () -> Unit>(
        { PersonalDataScreen(viewModel) },
        { DiabetesStatusScreen(viewModel) },
        { ActivityScreen1(viewModel) },
        { ActivityScreen2(viewModel) },
        { DayGoalsScreen(viewModel) }
    )

    var currentPage by remember { mutableStateOf(0) }
    val isLastPage = currentPage == pages.lastIndex

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Assessment", style = MaterialTheme.typography.titleLarge) },
//                navigationIcon = {
//                    if (currentPage > 0) {
//                        IconButton(onClick = { currentPage-- }) {
//                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                        }
//                    }
//                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentPage > 0) {
                    Button(onClick = { currentPage-- }) {
                        Text(text = "Back")
                    }
                }
                Button(
                    onClick = {
                        if (isLastPage) {
                            viewModel.submitDataToServer() // Submit data to server when on the last page
                        } else {
                            currentPage++
                        }
                    }
                ) {
                    Text(text = if (isLastPage) "Save" else "Continue")
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Progress indicator
                LinearProgressIndicator(
                    progress = (currentPage + 1) / pages.size.toFloat(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Displaying the current page
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentPage) {
                        0 -> pages[0]()
                        1 -> pages[1]()
                        2 -> pages[2]()
                        3 -> pages[3]()
                        4 -> pages[4]()
                    }
                }
            }
        }
    )
}

@Composable
fun PersonalDataScreen(viewModel: AssessmentViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Fill in Your Personal Data", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = viewModel.personalData.fullName,
            onValueChange = { viewModel.personalData = viewModel.personalData.copy(fullName = it) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.personalData.dateOfBirth,
            onValueChange = { viewModel.personalData = viewModel.personalData.copy(dateOfBirth = it) },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.personalData.gender,
            onValueChange = { viewModel.personalData = viewModel.personalData.copy(gender = it) },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DiabetesStatusScreen(viewModel: AssessmentViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Diabetes Status?", style = MaterialTheme.typography.headlineMedium)
        val options = listOf("Yes, I have diabetes", "No, I'm not diabetic", "I don't know")
        options.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = viewModel.diabetesStatus.isDiabetic == it, onClick = {
                    viewModel.diabetesStatus = viewModel.diabetesStatus.copy(isDiabetic = it)
                })
                Text(text = it)
            }
        }
    }
}

@Composable
fun ActivityScreen1(viewModel: AssessmentViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "How is your activity?", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = viewModel.activityData1.height,
            onValueChange = { viewModel.activityData1 = viewModel.activityData1.copy(height = it) },
            label = { Text("Height") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.activityData1.weight,
            onValueChange = { viewModel.activityData1 = viewModel.activityData1.copy(weight = it) },
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.activityData1.physicalActivity,
            onValueChange = { viewModel.activityData1 = viewModel.activityData1.copy(physicalActivity = it) },
            label = { Text("Physical Activity") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ActivityScreen2(viewModel: AssessmentViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "How is your activity?", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = viewModel.activityData2.smokingHistory,
            onValueChange = { viewModel.activityData2 = viewModel.activityData2.copy(smokingHistory = it) },
            label = { Text("Smoking History") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.activityData2.heartDiseaseHistory,
            onValueChange = { viewModel.activityData2 = viewModel.activityData2.copy(heartDiseaseHistory = it) },
            label = { Text("Heart Disease History") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DayGoalsScreen(viewModel: AssessmentViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Day Goals", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = viewModel.dayGoals.calories,
            onValueChange = { viewModel.dayGoals = viewModel.dayGoals.copy(calories = it) },
            label = { Text("Calories") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.dayGoals.sugarIntake,
            onValueChange = { viewModel.dayGoals = viewModel.dayGoals.copy(sugarIntake = it) },
            label = { Text("Sugar Intake") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}