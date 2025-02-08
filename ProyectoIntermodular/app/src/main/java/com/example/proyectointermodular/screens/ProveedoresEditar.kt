package com.example.proyectointermodular.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.ProveedorViewModel
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProveedoresEditar(navController: NavHostController, auth: FirebaseAuth, viewModel: ProveedorViewModel, nif: String) {
    val proveedores by viewModel.proveedores.collectAsState()
    val proveedor = proveedores.find { it.nif == nif } // Buscar el proveedor a editar

    var nuevoNombre by remember { mutableStateOf(proveedor?.nombre ?: "") }
    var mensajeConfirmacion by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Editar Proveedor", fontSize = MaterialTheme.typography.headlineSmall.fontSize)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = nuevoNombre,
            onValueChange = { nuevoNombre = it }, // Actualiza el estado cuando cambia el valor
            label = { Text("Nuevo nombre") }, // Etiqueta del campo de texto
            modifier = Modifier.fillMaxWidth(), // Usa Modifier de Jetpack Compose
            singleLine = true // Solo permite una línea de entrada
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (proveedor != null) {
                    viewModel.updateProveedor(proveedor.nif, nuevoNombre)
                    mensajeConfirmacion = "Proveedor actualizado correctamente"
                } else {
                    mensajeConfirmacion = "Error: Proveedor no encontrado"
                }
            },
            enabled = nuevoNombre.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "Guardar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = mensajeConfirmacion)

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
}