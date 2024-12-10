package com.amikom.sweetlife.ui.screen.AfterScan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amikom.sweetlife.domain.repository.AfterScanRepository
import com.amikom.sweetlife.ui.screen.profile.editProfile.EditProfileViewModel

@Composable
fun AfterScanScreen(
    viewModel: AfterScanViewModel
) {

    val foodItem by viewModel.afterScan.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Nutrition Facts", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

//        foodItem.forEach { foodItem ->
//            ExpandableCard(
//                title = foodItem.name,
//                unit = "${foodItem.unit} unit",
//                calories = "${foodItem.calories} kcal",
//                fat = "${foodItem.fat} g",
//                carbs = "${foodItem.carbohydrate} g",
//                sugar = "${foodItem.sugar} g",
//                protein = "${foodItem.protein} g"
//            )
//        }
    }
}


@Composable
fun ExpandableCard(
    title: String,
    unit: String,
    calories: String,
    fat: String,
    carbs: String,
    sugar: String,
    protein: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(15.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            // Header (Judul dan Icon)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(4f)
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
            // Detail (Kalori, Lemak, Karbohidrat, Gula, Protein)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(8.dp)
                ) {
                    Column {
                        Text("Calorie: $calories")
                        Text("Fat: $fat")
                        Text("Carbs: $carbs")
                        Text("Sugar: $sugar")
                        Text("Protein: $protein")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewExpandableCard() {
    ExpandableCard(
        title = "Nasi Goreng",
        unit = "100g",
        calories = "200",
        fat = "10",
        carbs = "20",
        sugar = "5",
        protein = "5"
    )
}


