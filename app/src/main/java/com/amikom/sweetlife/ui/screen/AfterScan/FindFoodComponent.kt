package com.amikom.sweetlife.ui.screen.AfterScan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun FindFoodComponent(
    foodName: String,
    quantity: String,
    onSearchClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFoodNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    calorie: String,
    fat: String,
    carbs: String,
    sugar: String,
    protein: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White ,shape = RoundedCornerShape(15.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = foodName,
                onValueChange = onFoodNameChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Food Name") },
                singleLine = true,
                shape = RoundedCornerShape(15.dp)
            )

            TextField(
                value = quantity,
                onValueChange = onQuantityChange,
                modifier = Modifier
                    .width(80.dp)
                    .padding(end = 8.dp),
                placeholder = { Text("Qty") },
                singleLine = true,
                shape = RoundedCornerShape(15.dp)
            )

            Button(
                onClick = onSearchClick,
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(MainBlue)
            ) {
                Text("Search", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Nutrition Facts", fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Calorie")
                Text(calorie, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Fat")
                Text(fat)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Carbs")
                Text(carbs)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sugar")
                Text(sugar)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Protein")
                Text(protein)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(MainBlue)
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
@Preview
fun FindFoodComponentPreview() {
    FindFoodComponent(
        foodName = "Nasi Goreng",
        quantity = "1",
        onSearchClick = {},
        onSaveClick = {},
        onFoodNameChange = {},
        onQuantityChange = {},
        calorie = "100",
        fat = "10",
        carbs = "20",
        sugar = "5",
        protein = "5"
    )
}