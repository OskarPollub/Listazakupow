package pl.uczelnia.listazakupow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform