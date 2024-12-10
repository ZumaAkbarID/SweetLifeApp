package com.amikom.sweetlife.ui.screen.assesment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun NextDiabetes() {
    // Parent container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress Bar
        LinearProgressIndicator(
            progress = {
                0.5f
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MainBlue,
            trackColor = Color.LightGray,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title and Subtitle
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Fill in Your Personal Data",
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Complete your data to find out your assessment results",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var expanded by remember { mutableStateOf(false) }
            var selectedDiabet by remember { mutableStateOf("") }

            Text(
                text = "Diabetes Status?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                inputDiabet(viewModel = NextDiabetesViewModel())
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(
                        "Insulin Level",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date of Birth
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = "Blood Sugar Level",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
            )
        }
        //Button
        Spacer(modifier = Modifier.height(32.dp))
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
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        }
    }
}

@Composable
fun inputDiabet(viewModel: NextDiabetesViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val selectedType by viewModel.selectedDiabetType.collectAsState()
    val diabetesTypes by viewModel.diabetType.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    "Type",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            diabetesTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        viewModel.updateSelectedType(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NextDiabetPreview() {
    MaterialTheme {
        NextDiabetes()
    }
}