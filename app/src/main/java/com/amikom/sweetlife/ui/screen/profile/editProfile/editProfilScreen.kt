package com.amikom.sweetlife.ui.screen.profile.editProfile

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.GrammaticalInflectionManagerCompat.GrammaticalGender
import androidx.core.content.PermissionChecker.PermissionResult
import com.amikom.sweetlife.R
import com.amikom.sweetlife.ui.screen.profile.UserProfile
import com.amikom.sweetlife.ui.theme.MainBlue
import kotlinx.serialization.json.Json
import java.util.Calendar
import java.util.Locale

@Composable
fun EditProfileScreen(
    userProfile: EditProfileState,
    onEditProfile: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Avatar(image = userProfile.imageVector, onEditClick = onEditProfile)

        // Nama Lengkap
        EditItem(
            Icon = Icons.Default.Person,
            label = "Full Name",
            value = userProfile.fullName
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        EditItem(
            Icon = Icons.Default.Email,
            label = "Email",
            value = userProfile.email
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tanggal Lahir
        EditItem(
            Icon = Icons.Default.DateRange,
            label = "Date of Birth",
            isDate = true,
            value = userProfile.dateOfBirth,
            onDateChange = { newDate ->
                userProfile.dateOfBirth = newDate
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gender
        EditItem(
            Icon = Icons.Default.Face,
            label = "Gender",
            value = userProfile.gender
        )
        //button
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
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

@Composable
private fun Avatar(
    image: Int,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center // Menjaga semua konten tetap di tengah
    ) {
        // Avatar Image
        Image(
            painter = painterResource(id = image),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        // Edit Icon (FloatingActionButton kecil di sudut kanan bawah avatar)
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-130).dp, y = (-15).dp)
                .size(20.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Avatar",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
private fun EditItem(
    Icon: ImageVector,
    label: String,
    value: String,
    isDate: Boolean = false,
    onDateChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dateFormatter = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    OutlinedTextField(
        value = value,
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isDate) {
                    android.app
                        .DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                val selectedDate = Calendar.getInstance()
                                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                                onDateChange(dateFormatter.format(selectedDate.time))
                            },
                            year,
                            month,
                            day
                        )
                        .show()
                }
            },
        shape = RoundedCornerShape(15.dp),
        leadingIcon = {
            Icon(
                imageVector = Icon,
                contentDescription = "Icon",
                tint = Color.Gray
            )
        },
        singleLine = true,
        readOnly = true // Agar input tidak bisa diubah manual
    )
}


data class EditProfileState(
    val imageVector: Int = 0,
    val fullName: String = "",
    var dateOfBirth: String = "",
    val email: String = "",
    val gender: String = "",
)

@Preview(showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(
        userProfile = EditProfileState(
            imageVector = R.drawable.bapak,
            fullName = "John Doe",
            dateOfBirth = "01/01/2000",
            email = "jokowi",
            gender = "Male"
        ),
        onEditProfile = { }
    )
}
