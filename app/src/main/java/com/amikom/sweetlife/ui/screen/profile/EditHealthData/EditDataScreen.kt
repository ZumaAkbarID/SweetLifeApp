package com.amikom.sweetlife.ui.screen.profile.EditHealthData

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amikom.sweetlife.ui.screen.assesment.HowActivityVIewModel
import com.amikom.sweetlife.ui.screen.assesment.inputText
import com.amikom.sweetlife.ui.screen.profile.editProfile.EditProfileViewModel
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun EditDataScreen(
//    viewModel: EditProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(150.dp)
        ) {
            Text(
                text = "Height",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = "Weight",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        label = {
                            Text(
                                "Cm",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        label = {
                            Text(
                                "Kg",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        singleLine = true
                    )
                }
                item {
                    //Diabetes
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
                        value = "",
                        onValueChange = { }
                    )
                }

                item {

//smoking status
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Smoking Status",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDropdown(
                        label = "Smoking Status",
                        options = listOf("Current", "never", "former", "ever"),
                        value = "",
                        onValueChange = { }
                    )

                }

                item {
                    // blood sugar level
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Blood Sugar Level",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BloodSugarLevel(
//            viewModel = viewModel,
                        label = "Blood Sugar Level",
                        value = "",
                        onValueChange = { }
                    )
                }
                item {
                    // heart disease
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
                        value = "",
                        onValueChange = { }
                    )
                }

                item {
                    // activity
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Heart Disease",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDropdown(
                        label = "Activity",
                        options = listOf("Sedentary", "Light", "Moderate", "Active", "Very Active"),
                        value = "",
                        onValueChange = { }
                    )
                }
                item {
                    //button save
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainBlue
                        )
                    ) {
                        Text("Continue")
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
                    }
                }
            }
        }
    }
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
fun CustomDropdown(
    label: String,
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }
            .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value.ifEmpty { label },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    onValueChange(option)
                    expanded = false
                }
            )
        }
    }
}
