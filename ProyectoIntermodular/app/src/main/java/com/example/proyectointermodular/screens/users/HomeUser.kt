package com.example.proyectointermodular.screens.users
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeUser(navController: NavHostController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Título del Panel de Usuario
        Text(
            text = "Panel de Usuario",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        // Botón para Agregar Carta a Mi Lista
        Button(
            onClick = { navController.navigate(AppScreens.AgregarCarta.ruta) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Ícono de agregar
                contentDescription = "Agregar Carta a Mi Lista",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre ícono y texto
            Text(
                text = "Agregar Carta a Mi Lista",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para Ver Mis Cartas
        Button(
            onClick = { navController.navigate(AppScreens.MisCartas.ruta) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                imageVector = Icons.Default.List, // Ícono de lista
                contentDescription = "Ver Mis Cartas",
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre ícono y texto
            Text(
                text = "Ver Mis Cartas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para Cerrar Sesión
        Button(
            onClick = {
                auth.signOut()
                navController.navigate(AppScreens.Login.ruta) {
                    popUpTo(AppScreens.HomeUser.ruta) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp, // Ícono de cerrar sesión
                contentDescription = "Cerrar Sesión",
                tint = MaterialTheme.colorScheme.onError
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre ícono y texto
            Text(
                text = "Cerrar Sesión",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}




