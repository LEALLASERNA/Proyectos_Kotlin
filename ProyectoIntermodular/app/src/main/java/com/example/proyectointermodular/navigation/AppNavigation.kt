package com.example.proyectointermodular.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectointermodular.screens.Home
import com.example.proyectointermodular.screens.Login
import com.example.proyectointermodular.screens.ProveedorAlta
import com.example.proyectointermodular.screens.ProveedoresEliminar
import com.example.proyectointermodular.screens.ProveedoresListar

import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation (auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta)
    {
        composable(AppScreens.Login.ruta) { Login(navigationController, auth)  }
        composable(AppScreens.Home.ruta) { Home(navigationController, auth, viewModel()) }
        composable(AppScreens.ProveedorAlta.ruta) { ProveedorAlta(navigationController, auth, viewModel()) }
        composable(AppScreens.ProveedoresEliminar.ruta) { ProveedoresEliminar(navigationController, auth, viewModel()) }
        composable(AppScreens.ProveedoresListar.ruta) { ProveedoresListar(navigationController, auth, viewModel()) }

    }
}

