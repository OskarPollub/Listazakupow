package pl.uczelnia.listazakupow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun App() {
    AppTheme {
        var aktywnyEkran by remember { mutableStateOf("lista") }
        var wybranyProduktId by remember { mutableStateOf<Int?>(null) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = aktywnyEkran == "lista", onClick = { aktywnyEkran = "lista" }, icon = { Text("L") }, label = { Text("Lista") })
                    NavigationBarItem(selected = aktywnyEkran == "statystyki", onClick = { aktywnyEkran = "statystyki" }, icon = { Text("S") }, label = { Text("Statystyki") })
                    NavigationBarItem(selected = aktywnyEkran == "ustawienia", onClick = { aktywnyEkran = "ustawienia" }, icon = { Text("U") }, label = { Text("Ustawienia") })
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                when (aktywnyEkran) {
                    "lista" -> EkranListy(onDodaj = { aktywnyEkran = "formularz" }, onSzczegoly = { id -> wybranyProduktId = id; aktywnyEkran = "szczegoly" })
                    "formularz" -> EkranFormularza(onWstecz = { aktywnyEkran = "lista" })
                    "szczegoly" -> wybranyProduktId?.let { EkranSzczegolow(produktId = it, onWstecz = { aktywnyEkran = "lista" }) }
                    "statystyki" -> EkranStatystyk()
                    "ustawienia" -> EkranUstawien()
                }
            }
        }
    }
}