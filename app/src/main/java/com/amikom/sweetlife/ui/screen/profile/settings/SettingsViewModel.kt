import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserChoice(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isEnabled: Boolean
)

class SettingsViewModel : ViewModel() {
    private val _userChoice = MutableStateFlow(
        listOf(
            UserChoice(1, "Notification", "lorem Ipsum dolor sit amet adipisfdlsjaflkflasdjkf", Icons.Default.Notifications, true),
            UserChoice(2, "Dark Mode", "lorem Ipsum dolor sit amet adipisfdlsjaflkflasdjkf", Icons.Default.Face, false),
        )
    )
    val userChoice = _userChoice.asStateFlow()

    fun updateUserChoice(id: Int, isEnabled: Boolean) {
        _userChoice.value = _userChoice.value.map {
            if (it.id == id) it.copy(isEnabled = isEnabled) else it
        }
    }
}
