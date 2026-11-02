package pl.uczelnia.listazakupow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KartaProduktu(produkt: Produkt, kategoria: Kategoria?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(produkt.nazwa, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text("${produkt.ilosc} ${produkt.jednostka}", style = MaterialTheme.typography.bodyMedium)
            }
            kategoria?.let { ZnacznikKategorii(it) }
        }
    }
}

@Composable
fun ZnacznikKategorii(kategoria: Kategoria, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(kategoria.kolorHex), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(kategoria.nazwa, color = Color.White, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun PustyStan(komunikat: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(komunikat, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun KartaStatystyki(tytul: String, wartosc: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(tytul, style = MaterialTheme.typography.titleSmall)
            Text(wartosc, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun WierszZPrzelacznikiem(etykieta: String, zaznaczony: Boolean, onZmiana: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(etykieta, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = zaznaczony, onCheckedChange = onZmiana)
    }
}