package pl.uczelnia.listazakupow

data class Produkt(
    val id: Int,
    val nazwa: String,
    val kategoriaId: Int,
    val ilosc: Double,
    val jednostka: String,
    val czyKupione: Boolean
)

data class Kategoria(
    val id: Int,
    val nazwa: String,
    val kolorHex: Long
)

val Kategorie = listOf(
    Kategoria(1, "Spożywcze", 0xFF4CAF50),
    Kategoria(2, "Chemia", 0xFF2196F3),
    Kategoria(3, "Inne", 0xFF9E9E9E),
    Kategoria (id =4, nazwa = "nabial", kolorHex = 0xFF9E9E2E),
)

val Produkty = listOf(
    Produkt(1, "Mleko", 1, 1.0, "l", false),
    Produkt(2, "Chleb", 1, 1.0, "szt", true),
    Produkt(3, "Płyn do naczyń", 2, 1.0, "szt", false),
    Produkt(4, "Jabłka", 1, 2.0, "kg", false),
    Produkt(5, "Papier toaletowy", 2, 8.0, "rolki", true)
)