package com.example.proyectointermodular.navigation

import HomeUser
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectointermodular.ViewModel.ProveedorViewModel
import com.example.proyectointermodular.screens.AgregarProveedor
import com.example.proyectointermodular.screens.Home
import com.example.proyectointermodular.screens.Login
import com.example.proyectointermodular.screens.MisProveedores
import com.example.proyectointermodular.screens.ProveedorAlta
import com.example.proyectointermodular.screens.ProveedoresListar
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    val proveedorViewModel: ProveedorViewModel = viewModel() // ViewModel compartido

    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta) {

        // Pantalla de Login
        composable(AppScreens.Login.ruta) {
            Login(navigationController, auth)
        }

        // Pantallas del Administrador
        composable(AppScreens.Home.ruta) {
            Home(navigationController, auth)
        }
        composable(AppScreens.ProveedorAlta.ruta) {
            ProveedorAlta(navigationController, auth, proveedorViewModel)
        }

        composable(AppScreens.ProveedoresListar.ruta) {
            ProveedoresListar(navigationController, auth, proveedorViewModel)
        }


        // Pantallas del Usuario
        composable(AppScreens.HomeUser.ruta) {
            HomeUser(navigationController, auth)
        }
        composable(AppScreens.AgregarProveedor.ruta) {
            AgregarProveedor(navigationController, proveedorViewModel, auth)
        }
        composable(AppScreens.MisProveedores.ruta) {
            MisProveedores(navigationController, auth)
        }
    }
}

