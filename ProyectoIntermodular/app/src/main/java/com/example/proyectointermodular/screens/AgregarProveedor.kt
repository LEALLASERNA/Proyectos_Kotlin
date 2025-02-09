package com.example.proyectointermodular.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.ProveedorViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AgregarProveedor(navController: NavHostController, viewModel: ProveedorViewModel, auth: FirebaseAuth) {
    val listaProveedores by viewModel.proveedores.collectAsState()
    val user = auth.currentUser
    val uid = user?.uid ?: return

    LaunchedEffect(Unit) {
        viewModel.loadProveedores()
    }

    LazyColumn {
        items(listaProveedores) { proveedor ->
            Button(
                onClick = {
                    FirebaseFirestore.getInstance()
                        .collection("usuarios").document(uid)
                        .collection("mis_proveedores").document(proveedor.nif)
                        .set(proveedor)
                }
            ) {
                Text(text = "Agregar ${proveedor.nombre}")
            }
        }
    }
}
