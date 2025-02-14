package com.example.proyectointermodular.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Home(navController: NavHostController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Panel de Administrador", fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { navController.navigate(AppScreens.ProveedorAlta.ruta) }) {
            Text(text = "Agregar Proveedor")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { navController.navigate(AppScreens.ProveedoresListar.ruta) }) {
            Text(text = "Listar Proveedores")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            auth.signOut()
            navController.navigate(AppScreens.Login.ruta) {
                popUpTo(AppScreens.Home.ruta) { inclusive = true }
            }
        }) {
            Text(text = "Cerrar Sesión")
        }
    }
}
