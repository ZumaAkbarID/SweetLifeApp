package com.amikom.sweetlife.ui.screen.profile.EditHealthData

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amikom.sweetlife.ui.screen.assesment.HowActivityVIewModel
import com.amikom.sweetlife.ui.screen.assesment.inputText
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun EditDataScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

//Carbohydtrate
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Carbohydrate",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = { },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(15.dp),
//            label = {
//                Text(
//                    "Carbohydrate",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.Gray
//                )
//            },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Person Icon",
//                    tint = Color.Gray
//                )
//            },
//            singleLine = true
//        )

        // blood sugar

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Blood Sugar Level",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            label = {
                Text(
                    "Blood Sugar Level",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Person Icon",
                    tint = Color.Gray
                )
            },
            singleLine = true
        )


        // Glucose
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Glucose",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = { },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(15.dp),
//            label = {
//                Text(
//                    "Glucose",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.Gray
//                )
//            },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Person Icon",
//                    tint = Color.Gray
//                )
//            },
//            singleLine = true
//        )
//
//        // Calories form
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Calories",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = { newValue ->
////                if (newValue.all { it.isDigit() }) {
////                    inputText = newValue
////                }
//            },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(15.dp),
//            label = {
//                Text(
//                    "Calories",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.Gray
//                )
//            },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Person Icon",
//                    tint = Color.Gray
//                )
//            },
//            singleLine = true
//        )

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditDataScreenPreview() {
    EditDataScreen()
}