package com.example.proyectointermodular.screens.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.CartaViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AgregarCarta(navController: NavHostController, viewModel: CartaViewModel, auth: FirebaseAuth) {
    val listaCartas by viewModel.cartas.collectAsState()
    val user = auth.currentUser
    val uid = user?.uid ?: return

    LaunchedEffect(Unit) { viewModel.loadCartas() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Selecciona una Carta", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(listaCartas) { carta ->
                Button(
                    onClick = { viewModel.agregarCartaUsuario(uid, carta) },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(text = "Agregar ${carta.nombre}")
                }
            }
        }
    }
}

