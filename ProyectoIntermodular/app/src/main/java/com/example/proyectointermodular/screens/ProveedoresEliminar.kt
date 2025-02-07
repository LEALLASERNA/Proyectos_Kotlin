package com.example.proyectointermodular.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.ProveedorViewModel
import com.example.proyectointermodular.models.Proveedor
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProveedoresEliminar(navController: NavHostController, auth: FirebaseAuth, viewModel: ProveedorViewModel) {
    val listaProveedores by viewModel.proveedores.collectAsState()

    // Cargar los proveedores cuando la pantalla se monta
    LaunchedEffect(Unit) {
        viewModel.loadProveedores()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Eliminar Proveedores", fontSize = MaterialTheme.typography.headlineSmall.fontSize)

        Spacer(modifier = Modifier.height(10.dp))

        // Mostrar la lista de proveedores con botón de eliminación
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaProveedores) { proveedor ->
                ProveedorEliminarItem(proveedor, viewModel)
            }
        }
    }
}

@Composable
fun ProveedorEliminarItem(proveedor: Proveedor, viewModel: ProveedorViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp), // Agregamos sombra manualmente
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "NIF: ${proveedor.nif}")
                Text(text = "Nombre: ${proveedor.nombre}")
            }

            Button(
                onClick = { viewModel.deleteProveedor(proveedor.nif) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Eliminar", color = Color.White)
            }
        }
    }
}