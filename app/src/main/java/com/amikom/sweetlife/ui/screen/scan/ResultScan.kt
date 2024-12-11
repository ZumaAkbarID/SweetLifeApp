package com.amikom.sweetlife.ui.screen.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.CustomDialog

@Composable
fun ResultAndAdditionalScreen(viewModel: ResultScanViewModel, navController: NavController) {

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        // Section Result
        Text(
            text = "Result",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ItemList(items = listOf("Tempe", "Tahu", "Tahu"))

        Spacer(modifier = Modifier.height(16.dp))

        // Section Additional
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
            IconButton(onClick = { /* Handle Add */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add Additional"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        ItemList(items = listOf("Tempe", "Tahu"))

        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Button(
            onClick = { /* Handle Save */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ItemList(items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            ExpandableItem(name = item)
        }
    }
}

@Composable
fun ExpandableItem(name: String) {
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
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "1 unit", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(
                        if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                    ), // Tambahkan resource ikon panah
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
                    .background(Color(0xFFD8D8D8)) // Warna latar detail
                    .padding(16.dp)
            ) {
                Text(text = "Calorie\n120kcal", style = MaterialTheme.typography.bodySmall)
                Text(text = "Fat\n10 G", style = MaterialTheme.typography.bodySmall)
                Text(text = "Carbs\n10 G", style = MaterialTheme.typography.bodySmall)
                Text(text = "Sugar\n10 G", style = MaterialTheme.typography.bodySmall)
                Text(text = "Protein\n10 G", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
