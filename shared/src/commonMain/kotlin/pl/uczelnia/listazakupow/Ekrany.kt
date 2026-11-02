package pl.uczelnia.listazakupow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkranListy(modifier: Modifier = Modifier, onDodaj: () -> Unit, onSzczegoly: (Int) -> Unit) {
    var wyszukiwanie by remember { mutableStateOf("") }
    var pokazKupione by remember { mutableStateOf(true) }

    val przefiltrowane = Produkty.filter {
        it.nazwa.contains(wyszukiwanie, ignoreCase = true) && (pokazKupione || !it.czyKupione)
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Lista Zakupów") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onDodaj) { Text("+") }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = wyszukiwanie,
                onValueChange = { wyszukiwanie = it },
                label = { Text("Szukaj") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Kupione")
                Switch(checked = pokazKupione, onCheckedChange = { pokazKupione = it })
            }

            if (przefiltrowane.isEmpty()) {
                PustyStan("Brak produktów spełniających kryteria")
            } else {
                BoxWithConstraints {
                    if (maxWidth < 600.dp) {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(przefiltrowane, key = { it.id }) { p ->
                                KartaProduktu(p, Kategorie.find { it.id == p.kategoriaId }, { onSzczegoly(p.id) })
                            }
                        }
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(przefiltrowane, key = { it.id }) { p ->
                                KartaProduktu(p, Kategorie.find { it.id == p.kategoriaId }, { onSzczegoly(p.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkranSzczegolow(produktId: Int, onWstecz: () -> Unit, modifier: Modifier = Modifier) {
    val produkt = Produkty.find { it.id == produktId }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły") },
                navigationIcon = { TextButton(onClick = onWstecz) { Text("Wstecz") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            if (produkt != null) {
                Text("Nazwa: ${produkt.nazwa}", style = MaterialTheme.typography.headlineMedium)
                Text("Ilość: ${produkt.ilosc} ${produkt.jednostka}")
                Text("Stan: ${if(produkt.czyKupione) "Kupione" else "Do kupienia"}")
                Spacer(Modifier.weight(1f))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Zmień stan") }
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("Usuń") }
            } else {
                Text("Nie znaleziono produktu")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkranFormularza(modifier: Modifier = Modifier, onWstecz: () -> Unit) {
    var nazwa by remember { mutableStateOf("") }
    var ilosc by remember { mutableStateOf("") }
    var jednostka by remember { mutableStateOf("szt") }
    var wazne by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Nowy Produkt") },
                navigationIcon = { TextButton(onClick = onWstecz) { Text("Wstecz") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = nazwa, onValueChange = { nazwa = it }, label = { Text("Nazwa (wymagane)") }, modifier = Modifier.fillMaxWidth())
            if (nazwa.isBlank()) { Text("Błąd: Nazwa nie może być pusta", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            OutlinedTextField(value = ilosc, onValueChange = { ilosc = it }, label = { Text("Ilość (liczba)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = jednostka, onValueChange = { jednostka = it }, label = { Text("Jednostka") }, modifier = Modifier.fillMaxWidth())
            WierszZPrzelacznikiem("Oznacz jako ważne", wazne, { wazne = it })

            Spacer(Modifier.weight(1f))
            Button(onClick = onWstecz, enabled = nazwa.isNotBlank(), modifier = Modifier.fillMaxWidth()) { Text("Zapisz") }
        }
    }
}

@Composable
fun EkranStatystyk(modifier: Modifier = Modifier) {
    val total = Produkty.size
    val kupione = Produkty.count { it.czyKupione }
    Column(modifier.padding(16.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Podsumowanie", style = MaterialTheme.typography.headlineMedium)
        Row {
            KartaStatystyki("Wszystkie", total.toString(), Modifier.weight(1f))
            KartaStatystyki("Kupione", kupione.toString(), Modifier.weight(1f))
        }
        LinearProgressIndicator(progress = { if(total > 0) kupione.toFloat()/total else 0f }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
    }
}

@Composable
fun EkranUstawien(modifier: Modifier = Modifier) {
    var powiadomienia by remember { mutableStateOf(true) }
    var widokKompaktowy by remember { mutableStateOf(false) }
    var trybCiemny by remember { mutableStateOf(false) }
    Column(modifier.fillMaxSize()) {
        Text("Ustawienia", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        WierszZPrzelacznikiem("Włącz powiadomienia", powiadomienia, { powiadomienia = it })
        WierszZPrzelacznikiem("Widok kompaktowy listy", widokKompaktowy, { widokKompaktowy = it })
        WierszZPrzelacznikiem("Tryb ciemny", trybCiemny, { trybCiemny = it })
    }
}