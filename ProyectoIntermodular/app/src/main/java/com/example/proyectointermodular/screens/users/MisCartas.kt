package com.example.proyectointermodular.screens.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.models.Carta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MisCartas(navController: NavHostController, auth: FirebaseAuth) {
    val user = auth.currentUser
    val uid = user?.uid ?: return
    var listaCartas by remember { mutableStateOf(emptyList<Carta>()) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("usuarios").document(uid)
            .collection("mis_cartas")
            .get()
            .addOnSuccessListener { snapshot ->
                listaCartas = snapshot.toObjects(Carta::class.java)
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Mis Cartas", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(listaCartas) { carta ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nombre: ${carta.nombre}")
                        Text(text = "Color: ${carta.color}")
                        Text(text = "Poder Defensivo: ${carta.pd}")
                    }
                }
            }
        }
    }
}