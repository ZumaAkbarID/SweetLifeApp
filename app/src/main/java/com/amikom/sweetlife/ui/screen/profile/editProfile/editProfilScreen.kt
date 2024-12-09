package com.amikom.sweetlife.ui.screen.profile.editProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amikom.sweetlife.R
import com.amikom.sweetlife.data.model.ProfileModel
import com.amikom.sweetlife.domain.nvgraph.Route
import com.amikom.sweetlife.ui.component.ConfirmSave
import com.amikom.sweetlife.ui.component.CustomDialog
import com.amikom.sweetlife.ui.screen.assesment.NextDiabetesViewModel
import com.amikom.sweetlife.ui.screen.assesment.genderDropdownn
import com.amikom.sweetlife.ui.screen.profile.UserProfile
import com.amikom.sweetlife.util.countAgeFromDate
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onEditProfile: () -> Unit,
    navController: NavController
) {
    val updateStatus by viewModel.updateStatus.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    val userProfile by viewModel.userProfile.observeAsState()
val showDialog = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Avatar(
            image = R.drawable.bapak,
            onEditClick = onEditProfile,
        )

        // Nama Lengkap
        EditItem(
            icon = Icons.Default.Person,
            label = "Full Name",
            value = userProfile?.name ?: "",
            onValueChange = { viewModel.onNameChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        EditItemEmail(
            label = "Email",
            value = userProfile?.email ?: "",
            onValueChange = { viewModel.onEmailChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tanggal Lahir
        EditItem(
            icon = Icons.Default.DateRange,
            isDate = true,
            label = "Date of Birth",
            value = userProfile?.dateOfBirth ?: "",
            onDateChange = { viewModel.onDateChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        //gender
        genderDropdown(
            viewModel = viewModel,
            "Select Gender"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Button
        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Loading..." else "Save")
            if (!isLoading) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "")
            }
        }
        if (showDialog.value) {
            ConfirmSave(
                openDialogCustom = showDialog,
                title = "Confirm Save",
                message = "Are you sure you want to save changes?",
                buttons = listOf(
                    "Yes, Confirm" to {
                        showDialog.value = false
                        viewModel.updateProfile()
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
                            popUpTo<Route.EditProfileScreen> { inclusive = false }
                        }
                    }
                )
            )
        }
    }

    // Handle update status
    updateStatus?.let { success ->
        Text(
            text = if (success) "Profile updated successfully!" else "Failed to update profile. Please try again.",
            color = if (success) Color.Green else Color.Red
        )
    }
}
@Composable
private fun Avatar(
    image: Int,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Avatar Image
        Image(
            painter = painterResource(id = image),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        // Edit Icon
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(24.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Avatar", tint = Color.Gray)
        }
    }
}

@Composable
private fun EditItem(
    icon: ImageVector,
    label: String,
    value: String,
    isDate: Boolean = false,
    onDateChange: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var textValue by remember { mutableStateOf(value) }
    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        label = {
            if (value.isNotEmpty()) {
                Text(value)
            } else {
                Text(label)
            }
        },
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isDate) { showDialog = true }, // Aktifkan dialog
        shape = RoundedCornerShape(15.dp),
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        readOnly = isDate
    )

    if (isDate && showDialog) {
        android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                onDateChange(dateFormatter.format(selectedDate.time))
                showDialog = false // Tutup dialog
            },
            year,
            month,
            day
        ).show()
    }
}

@Composable
private fun EditItemEmail(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var errorMessage: String? = null

    // Fungsi untuk validasi email
    fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }

    // Validasi email setiap kali ada perubahan
    if (!validateEmail(value) && value.isNotEmpty()) {
        errorMessage = "Email tidak valid"
    }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        label = { Text(label) },
        isError = errorMessage != null,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon",
            )
        }
    )

    // Pesan error
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

//edit gender
@Composable
fun genderDropdown(viewModel: EditProfileViewModel, label: String) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = viewModel.GenderOptions
    val selectedGender by viewModel.selectedGender.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Person Icon",
                )
            },
            label = {
                Text(label)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
        ) {
            genderOptions.forEach { type ->
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

data class UserProfile(
    val name: String,
    val email: String,
val dateOfBirth : String,
    val gender : String
)
