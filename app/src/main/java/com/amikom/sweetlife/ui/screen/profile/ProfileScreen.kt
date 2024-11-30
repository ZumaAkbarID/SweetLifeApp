package com.amikom.sweetlife.ui.screen.profile

import android.text.TextUtils.TruncateAt
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.ui.component.BottomNavigationBar
import com.amikom.sweetlife.ui.component.getBottomNavButtons
import com.amikom.sweetlife.ui.component.rememberSelectedIndex
import com.amikom.sweetlife.ui.theme.MainBlue

@Composable
fun UserProfileScreen(
    navController: NavController,
    userProfile: UserProfile,
    onEditProfile: () -> Unit,
    onEditHealthData: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit
) {
    val selectedIndex = rememberSelectedIndex()

    val buttons = getBottomNavButtons(selectedIndex, navController)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(buttons = buttons)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                UserInfo(userProfile = userProfile)
                gmailBox(text = userProfile.email, onClick = { })
                UserHealthData(userProfile = userProfile)
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.Edit,
                        text = "Edit Profile",
                        onClick = { /* TODO: Handle Edit Profile */ }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        text = "Edit Health Data",
                        onClick = { /* TODO: Handle Edit Health Data */ }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        text = "Settings",
                        onClick = { /* TODO: Handle Settings */ }
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.LocationOn,
                        text = "Logout",
                        textColor = Color.Red,
                        onClick = { /* TODO: Handle Logout */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun UserInfo(
    userProfile: UserProfile,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Image(
            painter = painterResource(id = R.drawable.gendut),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userProfile.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
private fun gmailBox(
    text: String,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.primary,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .wrapContentWidth()
            .clickable(onClick = onClick)
            .padding(0.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

//wadah userHealthItem
@Composable
private fun UserHealthData(userProfile: UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserHealthItem(
                label = "Weight",
                value = "${userProfile.weight} kg"
            )
            UserHealthItem(
                label = "Height",
                value = "${userProfile.height} cm"
            )
            UserHealthItem(
                label = "Age",
                value = "${userProfile.age} years"
            )
            UserHealthItem(
                label = "Diabetes Risk",
                value = if (userProfile.isDiabetesRisk) "At risk" else "Not at risk", boolean = userProfile.isDiabetesRisk
            )
        }
    }
}

//isi dari UserHealthItem
@Composable
private fun UserHealthItem(
    label: String,
    value: String, boolean: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = if (boolean) Color.Red else Color.Black)
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
    trailingIcon: ImageVector = Icons.Default.KeyboardArrowRight
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

}

data class UserProfile(
    val name: String,
    val email: String,
    val weight: Int,
    val height: Int,
    val age: Int,
    val isDiabetesRisk: Boolean
)

