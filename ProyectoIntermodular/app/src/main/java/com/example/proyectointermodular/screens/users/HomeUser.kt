package com.example.proyectointermodular.screens.users
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
fun HomeUser(navController: NavHostController, auth: FirebaseAuth) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Bienvenido Usuario", fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate(AppScreens.AgregarCarta.ruta) }) {
            Text(text = "Agregar Carta a Mi Lista")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate(AppScreens.MisCartas.ruta) }) {
            Text(text = "Ver Mis Cartas")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            auth.signOut()
            navController.navigate(AppScreens.Login.ruta) { popUpTo(AppScreens.HomeUser.ruta) { inclusive = true } }
        }) {
            Text(text = "Cerrar Sesión")
        }
    }
}




