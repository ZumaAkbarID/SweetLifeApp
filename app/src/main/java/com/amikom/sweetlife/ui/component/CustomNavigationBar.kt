package com.amikom.sweetlife.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.domain.nvgraph.Route
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation

object BottomNavItems {
    val items = listOf(
        R.drawable.home,
        R.drawable.log,
        R.drawable.barbel,
        R.drawable.gear
    )

    val labels = listOf(
        "Home",
        "Food History",
        "Activity",
        "Settings"
    )

    val routes = listOf(
        Route.DashboardScreen,
        Route.HomeScreen,
        Route.HomeScreen,
        Route.ProfileScreen
    )
}

@Composable
fun rememberSelectedIndex(): MutableState<Int> {
    return rememberSaveable { mutableIntStateOf(0) }
}

@Composable
fun getBottomNavButtons(
    selectedIndex: MutableState<Int>,
    navController: NavController
): List<RioBottomNavItemData> {
    return BottomNavItems.items.mapIndexed { index, iconData ->
        RioBottomNavItemData(
            imageVector = ImageVector.vectorResource(iconData),
            selected = index == selectedIndex.value,
            onClick = {
                selectedIndex.value = index

                navController.navigate(BottomNavItems.routes[index])
            },
            label = BottomNavItems.labels[index]
        )
    }
}

@Composable
fun BottomNavigationBar(
    buttons: List<RioBottomNavItemData>,
    fabIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.scan),
    fabSize: Dp = 70.dp,
    barHeight: Dp = 70.dp,
    selectedItemColor: Color = MaterialTheme.colorScheme.primary,
    fabBackgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    RioBottomNavigation(
        fabIcon = fabIcon,
        buttons = buttons,
        fabSize = fabSize,
        barHeight = barHeight,
        selectedItemColor = selectedItemColor,
        fabBackgroundColor = fabBackgroundColor
    )
}