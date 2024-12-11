package com.amikom.sweetlife.ui.screen.scan

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.model.AdditionalInfo
import com.amikom.sweetlife.data.model.AdditionalItem
import com.amikom.sweetlife.data.model.FoodRequest
import com.amikom.sweetlife.data.model.ScanItem
import com.amikom.sweetlife.data.remote.Result
import com.amikom.sweetlife.data.remote.dto.scan.FindFoodResponse
import com.amikom.sweetlife.data.remote.dto.scan.FoodListItem
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.CustomDialog
import com.google.gson.Gson
import java.util.Locale
import kotlin.math.ceil

@Composable
fun ResultAndAdditionalScreen(
    foodList: String,
    viewModel: ResultScanViewModel,
    navController: NavController
) {
    val foodListItems = remember(foodList) {
        Gson().fromJson(foodList, Array<FoodListItem>::class.java).toList()
    }

    var additionalList by remember { mutableStateOf(mutableListOf<AdditionalItem>()) }
    var showPopup by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var weightText by remember { mutableStateOf("") }
    val findFoodData by viewModel.findFoodData.observeAsState()
    val saveFoodData by viewModel.saveFoodData.observeAsState()

    LaunchedEffect(foodListItems) {
        if (foodListItems.isEmpty()) {
            navController.navigate(Route.DashboardScreen) {
                popUpTo(Route.DashboardScreen) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(saveFoodData) {
        when (saveFoodData) {
            is Result.Success -> {
                showSuccessDialog = true // Tampilkan dialog sukses
            }
            is Result.Error -> {
                showErrorDialog = true // Tampilkan dialog gagal
            }
            else -> {
                showSuccessDialog = false
                showErrorDialog = false
            }
        }
    }

    if (showSuccessDialog) {
        CustomDialog(
            icon = R.drawable.baseline_check_circle_outline_24,
            title = "Success",
            message = "Your food data has been saved!",
            openDialogCustom = remember { mutableStateOf(true) },
            buttons = listOf(
                "Ok" to {
                    showSuccessDialog = false // Tutup dialog
                    navController.navigate(Route.DashboardScreen) {
                        launchSingleTop = true
                    }
                }
            ),
            dismissOnBackdropClick = false
        )
    }

    if (showErrorDialog) {
        CustomDialog(
            icon = R.drawable.baseline_info_outline_24,
            title = "Failed",
            message = "Can't save your food data",
            openDialogCustom = remember { mutableStateOf(true) },
            buttons = listOf(
                "Try again" to {
                    showErrorDialog = false // Tutup dialog
                }
            ),
            dismissOnBackdropClick = false
        )
    }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp, start = 16.dp, top = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header - Result
            item {
                Text(
                    text = "Result",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // List of Food Items (Result Section)
            items(foodListItems) { item ->
                ExpandableItem(foodListItem = item)
            }

            // Header - Additional
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Additional",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    IconButton(onClick = { if(saveFoodData !is Result.Loading) {
                        viewModel.resetFindState()
                        showPopup = true
                    } }) { // Tampilkan popup
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Add Additional",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            // List of Additional Items
            items(additionalList) { item ->
                ExpandableAdditionalItem(additionalItem = item)
            }
        }

        // Sticky Save Button
        Button(
            onClick = {
                val foodRequest = FoodRequest(
                    scan = foodListItems.map {
                        ScanItem(
                            it.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                                ?: "",
                            it.unit ?: 1
                        )
                    },
                    additionall = additionalList
                )

                viewModel.saveFood(foodRequest)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .align(Alignment.BottomCenter) // Sticky di bawah
                .padding(horizontal = 16.dp, vertical = 8.dp), // Padding untuk estetika
            shape = MaterialTheme.shapes.small,
            enabled = saveFoodData !is Result.Loading
        ) {
            if (saveFoodData is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = "Save")
            }
        }
    }

    // Popup for Additional Item Search
    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Search Additional Food") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText =
                                it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        label = {
                            Text(
                                "Food Name",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { weightText = it.filter { char -> char.isDigit() } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        label = {
                            Text(
                                "Weight (gram)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFD8D8D8))
                            .padding(16.dp)
                    ) {
                        if (findFoodData is Result.Success) {
                            val foodListItem =
                                (findFoodData as Result.Success<FindFoodResponse>).data.data

                            Text(
                                text = "Calorie\n${foodListItem?.calories?.let { ceil(it).toInt() }} kcal",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Fat\n${foodListItem?.fat?.let { ceil(it).toInt() }} g",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Carbs\n${foodListItem?.carbohydrates?.let { ceil(it).toInt() }} g",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Sugar\n${foodListItem?.sugar?.let { ceil(it).toInt() }} g",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Protein\n${foodListItem?.protein?.let { ceil(it).toInt() }} g",
                                style = MaterialTheme.typography.bodySmall
                            )
                        } else if (findFoodData is Result.Loading) {
                            Text(text = "Loading...", style = MaterialTheme.typography.bodySmall)
                        } else if (findFoodData is Result.Error) {
                            Text("Food $searchText is not found!", textAlign = TextAlign.Center)
                        } else {
                            Text(
                                text = "Calorie\n0 kcal",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(text = "Fat\n0 g", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Carbs\n0 g", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Sugar\n0 g", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Protein\n0 g", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val weight = weightText.toIntOrNull() ?: 0

                    if (findFoodData !is Result.Loading) {
                        if (searchText.isNotBlank() && weight > 0) {
                            viewModel.findFood(searchText, weight)
                        }
                    }

                    if (findFoodData is Result.Success) {
                        val result = (findFoodData as Result.Success).data.data
                        additionalList.add(
                            AdditionalItem(
                                name = searchText,
                                weight = weight,
                                info = AdditionalInfo(
                                    calories = result?.calories ?: 0.0,
                                    fat = result?.fat ?: 0.0,
                                    carbohydrates = result?.carbohydrates ?: 0.0,
                                    sugar = result?.sugar ?: 0.0,
                                    protein = result?.protein ?: 0.0
                                )
                            )
                        )
                        searchText = ""
                        weightText = ""
                        Handler(Looper.getMainLooper()).postDelayed({
                            viewModel.resetFindState()
                        }, 100)
                        showPopup = false
                    }
                }) {
                    when (findFoodData) {
                        is Result.Loading -> Text("Loading...")
                        is Result.Success -> Text("Save")
                        else -> Text("Search")
                    }
                }
            },
            dismissButton = {
                if (findFoodData !is Result.Loading) {
                    TextButton(onClick = { showPopup = false }) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

@Composable
fun ExpandableItem(foodListItem: FoodListItem) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = foodListItem.name ?: "Unknown",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${foodListItem.unit ?: 0} unit",
                style = MaterialTheme.typography.bodySmall
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(
                        if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                    ),
                    contentDescription = "Expand/Collapse"
                )
            }
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD8D8D8))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Calorie\n${foodListItem.calories?.let { ceil(it).toInt() }} kcal",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Fat\n${foodListItem.fat?.let { ceil(it).toInt() }} g",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Carbs\n${foodListItem.carbohydrate?.let { ceil(it).toInt() }} g",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Sugar\n${foodListItem.sugar?.let { ceil(it).toInt() }} g",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Protein\n${foodListItem.protein?.let { ceil(it).toInt() }} g",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ExpandableAdditionalItem(additionalItem: AdditionalItem) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = additionalItem.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${additionalItem.weight} g",
                style = MaterialTheme.typography.bodySmall
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(
                        if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                    ),
                    contentDescription = "Expand/Collapse"
                )
            }
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            if (additionalItem.info == null) {
                Text(text = "No additional details available.", textAlign = TextAlign.Center)
            } else {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD8D8D8))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Calorie\n${additionalItem.info.calories.let { ceil(it).toInt() }} kcal",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Fat\n${additionalItem.info.fat.let { ceil(it).toInt() }} g",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Carbs\n${additionalItem.info.carbohydrates.let { ceil(it).toInt() }} g",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Sugar\n${additionalItem.info.sugar.let { ceil(it).toInt() }} g",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Protein\n${additionalItem.info.protein.let { ceil(it).toInt() }} g",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}