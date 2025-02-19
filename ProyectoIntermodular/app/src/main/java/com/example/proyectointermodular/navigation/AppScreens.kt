package com.example.proyectointermodular.navigation

sealed class AppScreens(val ruta: String) {
     // Login
     object Login : AppScreens("Login")

     // Admin (Lista Global de Cartas)
     object Home : AppScreens("Home")
     object CartaAlta : AppScreens("CartaAlta")
     object CartasListar : AppScreens("CartasListar")

     // Usuario (Lista Personal)
     object HomeUser : AppScreens("HomeUser")
     object AgregarCarta : AppScreens("AgregarCarta")
     object MisCartas : AppScreens("MisCartas")
}
