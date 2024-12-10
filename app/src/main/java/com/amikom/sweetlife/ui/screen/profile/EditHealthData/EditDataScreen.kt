package com.amikom.sweetlife.ui.screen.profile.EditHealthData

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AssistChipDefaults.Height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.remote.json_request.EditHealthRequest
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.ConfirmSave
import com.amikom.sweetlife.ui.component.CustomDialog
import com.amikom.sweetlife.ui.screen.profile.ProfileViewModel
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun EditDataScreen(
    viewModel: EditDataScreenViewModel,
    navController: NavController
) {
    val healthDataState by viewModel.healthDataState.collectAsState()
    val showSuccessDialog = remember { mutableStateOf(false) }
    val healthData by viewModel.healthData.observeAsState()
    var expandedDiabet by remember { mutableStateOf(false) }
    var showDiabetesDetails by remember { mutableStateOf(false) }

    // State untuk form
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var diabetesStatus by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    var smokingStatus by remember { mutableStateOf("") }
    var diabetesType by remember { mutableStateOf("") }
    var insulinLevel by remember { mutableStateOf(0) }
    var bloodPressure by remember { mutableStateOf(0) }
    var heartDisease by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchHealthData()
    }

    LaunchedEffect(healthData) {
        when (val data = healthData) {
            is Result.Success -> {
                val healthProfile = data.data
                Log.d("EditDataScreen", "Health Profile: $healthProfile")
                height = healthProfile.height?.toString() ?: ""
                weight = healthProfile.weight?.toString() ?: ""
                diabetesStatus = if (healthProfile.isDiabetic == true) "Yes" else "No"
                smokingStatus = healthProfile.smokingHistory ?: ""
                heartDisease = if (healthProfile.hasHeartDisease == true) "Yes" else "No"
                activityLevel = healthProfile.activityLevel ?: ""
                if (healthProfile.isDiabetic == true) {
                    Log.d("EditDataScreen", "Diabetic Type: ${healthProfile.diabeticType}")
                    showDiabetesDetails = true
                    diabetesType = healthProfile.diabeticType ?: ""
                    insulinLevel = healthProfile.insulinLevel?: 0
                    bloodPressure = healthProfile.bloodPressure?: 0
                }
            }

            is Result.Error -> {
                // Handle error, misalnya tampilkan pesan
                Log.e("EditDataScreen", "Error loading health data: ${"Error"}")
            }

            is Result.Loading -> {
                // Bisa tambahkan loading indicator
            }

            null -> {}
            Result.Empty -> {
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        // Height & Weight
        HeightWeightSection(
            height = height,
            weight = weight,
            onHeightChange = { height = it },
            onWeightChange = { weight = it }
        )
        // Diabetes Status
        // Tambahkan state untuk mengontrol visibilitas

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Diabetes Status",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        CustomDropdown(
            label = "Diabetes Status",
            options = listOf("Yes", "No"),
            value = diabetesStatus,
            onValueChange = {
                diabetesStatus = it
                showDiabetesDetails = it == "Yes"
                if (it == "No") {
                    diabetesType = ""
                    insulinLevel = 0
                    bloodPressure = 0
                }
            }
        )

        AnimatedVisibility(visible = showDiabetesDetails) {
            Column(
            ) {
                //Diabetic Type
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Diabetic Type",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                CustomDropdown(
                    label = "Diabetic Type",
                    options = listOf("Type 1", "Type 2", "Gestational"),
                    value = diabetesType,
                    onValueChange = { diabetesType = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Insulin Level
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Insulin Level",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        InsulinLevel(
                            label = "Insulin Level",
                            value = insulinLevel.toString(),
                            onValueChange = { insulinLevel = insulinLevel }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Blood Pressure
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Blood Pressure",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        BloodSugarLevel(
                            label = "Blood Pressure",
                            value = bloodPressure.toString(),
                            onValueChange = { bloodPressure = bloodPressure }
                        )
                    }
                }
            }
        }
        // Smoking Status
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Smoking Status",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        CustomDropdown(
            label = "Smoking Status",
            options = listOf("Current", "Never", "Former", "Ever"),
            value = smokingStatus,
            onValueChange = { smokingStatus = it }
        )
        // Heart Disease
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Heart Disease",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        CustomDropdown(
            label = "Heart Disease",
            options = listOf("Yes", "No"),
            value = heartDisease,
            onValueChange = { heartDisease = it }
        )

        // Activity Level
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Activity Level",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        CustomDropdown(
            label = "Activity Level",
            options = listOf("Sedentary", "Light", "Moderate", "Active", "Ex"),
            value = activityLevel,
            onValueChange = { activityLevel = it }
        )

        // Save Button
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                showDialog.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
        ) {
            Text("Continue")
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
        }


        if (showDialog.value) {
            ConfirmSave(
                openDialogCustom = showDialog,
                title = "Confirm Save",
                message = "Are you sure you want to save changes?",
                buttons = listOf(
                    "Yes, Confirm" to {
                        showDialog.value = false
                        val heightValue = height.toFloatOrNull()
                        val weightValue = weight.toFloatOrNull()
                        // Kirim data ke ViewModel
                        if (heightValue != null && weightValue != null) {
                            viewModel.updateHealth(
                                EditHealthRequest(
                                    height = heightValue,
                                    weight = weightValue,
                                    isDiabetic = diabetesStatus == "Yes",
                                    smokingHistory = smokingStatus,
                                    diabeticType = diabetesType,
                                    insulinLevel = insulinLevel,
                                    bloodPressure = bloodPressure,
                                    hasHeartDisease = heartDisease == "Yes",
                                    activityLevel = activityLevel
                                )
                            )
                        }
                        showSuccessDialog.value = true
                    },
                    "No, Cancel" to {
                        showDialog.value = false
                    }
                )
            )
        }

        if (showSuccessDialog.value) {
            CustomDialog(
                openDialogCustom = showSuccessDialog,
                icon = R.drawable.baseline_check_circle_outline_24,
                title = "Success!",
                message = "Your data has been successfully saved.",
                buttons = listOf(
                    "Okay" to {
                        showSuccessDialog.value = false
                        navController.navigate(Route.DashboardScreen) {
                            popUpTo<Route.EditHealthScreen> { inclusive = false }
                        }
                    }
                )
            )
        }
    }

    // Handle State
    when (healthDataState) {
        is HealthDataState.Loading -> {
            // Show Loading
        }

        is HealthDataState.Success -> {
            // Navigate or Show Success
        }

        is HealthDataState.Error -> {
            // Show Error Message
        }

        else -> {}
    }
}

@Composable
fun InsulinLevel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Filter hanya angka
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        label = { Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun BloodSugarLevel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Filter hanya angka
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        label = { Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun HeightWeightSection(
    height: String,
    weight: String,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Height
        OutlinedTextField(
            value = height,
            shape = RoundedCornerShape(15.dp),
            onValueChange = onHeightChange,
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        // Weight
        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}


//    @Composable
//    fun CustomDropdown(
//        label: String,
//        options: List<String>,
//        value: String,
//        onValueChange: (String) -> Unit
//    ) {
//            var expanded by remember { mutableStateOf(false) }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable { expanded = !expanded }
//            .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
//            .padding(16.dp)
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(
//                text = value.ifEmpty { label },
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.Gray
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Icon(
//                imageVector = Icons.Default.ArrowDropDown,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSurface
//            )
//        }
//    }
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                options.forEach { option ->
//                    DropdownMenuItem(
//                        text = { Text(option) },
//                        onClick = {
//                            onValueChange(option)
//                            expanded = false
//                        },
//                        modifier = Modifier.fillMaxWidth() // Pastikan item penuh
//                    )
//                }
//        }
//    }
@Composable
fun CustomDropdown(
    options: List<String>,
    label: String = "Pilih opsi",
    value: String = "",
    modifier: Modifier = Modifier,

    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent, // Gunakan warna background yang diberikan
            ),
            modifier = modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
                .border(0.5.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(15.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (value.isNotEmpty()) value else label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (value.isEmpty()) Color.Gray else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Letakkan DropdownMenu di luar Card
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}