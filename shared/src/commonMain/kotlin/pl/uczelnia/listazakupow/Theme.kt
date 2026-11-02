package pl.uczelnia.listazakupow

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val JasneKolory = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA5D6A7),
    onPrimaryContainer = Color(0xFF003300),
    secondary = Color(0xFF2196F3),
    background = Color(0xFFF1F8E9),
    onBackground = Color(0xFF1B1B1F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1F)
)

private val CiemneKolory = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF003300),
    primaryContainer = Color(0xFF2E7D32),
    onPrimaryContainer = Color(0xFFC8E6C9),
    secondary = Color(0xFF64B5F6),
    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE4E1E6),
    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE4E1E6)
)

val Typografia = Typography(
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
    bodyLarge = TextStyle(fontSize = 16.sp)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) CiemneKolory else JasneKolory,
        typography = Typografia,
        content = content
    )
}