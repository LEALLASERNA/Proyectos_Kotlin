package com.example.proyectointermodular.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.proyectointermodular.models.Proveedor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MisProveedores(navController: NavHostController, auth: FirebaseAuth) {
    val user = auth.currentUser
    val uid = user?.uid ?: return
    var listaProveedores by remember { mutableStateOf(emptyList<Proveedor>()) }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("usuarios").document(uid)
            .collection("mis_proveedores")
            .get()
            .addOnSuccessListener { snapshot ->
                listaProveedores = snapshot.toObjects(Proveedor::class.java)
            }
    }

    LazyColumn {
        items(listaProveedores) { proveedor ->
            Text(text = "Proveedor: ${proveedor.nombre}")
        }
    }
}