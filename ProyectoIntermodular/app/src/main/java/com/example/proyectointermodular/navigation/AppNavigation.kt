package com.example.proyectointermodular.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectointermodular.ViewModel.CartaViewModel
import com.example.proyectointermodular.screens.CartasListar
import com.example.proyectointermodular.screens.Login
import com.example.proyectointermodular.screens.admin.CartaAlta
import com.example.proyectointermodular.screens.admin.Home
import com.example.proyectointermodular.screens.users.AgregarCarta
import com.example.proyectointermodular.screens.users.HomeUser
import com.example.proyectointermodular.screens.users.MisCartas
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    val cartaViewModel: CartaViewModel = viewModel() // ViewModel para manejar cartas

    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta) {
        // Pantalla de Login
        composable(AppScreens.Login.ruta) {
            Login(navigationController, auth)
        }

        // Pantallas del Administrador (Gestiona la lista global de cartas)
        composable(AppScreens.Home.ruta) {
            Home(navigationController, auth)
        }
        composable(AppScreens.CartaAlta.ruta) {
            CartaAlta(navigationController, auth, cartaViewModel)
        }
        composable(AppScreens.CartasListar.ruta) {
            CartasListar(navigationController, auth, cartaViewModel)
        }

        // Pantallas del Usuario (Maneja su propia lista de cartas)
        composable(AppScreens.HomeUser.ruta) {
            HomeUser(navigationController, auth)
        }
        composable(AppScreens.AgregarCarta.ruta) {
            AgregarCarta(navigationController, cartaViewModel, auth)
        }
        composable(AppScreens.MisCartas.ruta) {
            MisCartas(navigationController, auth)
        }
    }
}

