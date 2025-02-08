package com.example.proyectointermodular.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.ProveedorViewModel
import com.example.proyectointermodular.models.Proveedor
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProveedoresListar(navController: NavHostController, auth: FirebaseAuth, viewModel: ProveedorViewModel) {
    val listaProveedores by viewModel.proveedores.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProveedores()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Listado de Proveedores", fontSize = MaterialTheme.typography.headlineSmall.fontSize)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaProveedores) { proveedor ->
                ProveedorEditarItem(proveedor, viewModel)
            }
        }
    }
}

@Composable
fun ProveedorEditarItem(proveedor: Proveedor, viewModel: ProveedorViewModel) {
    var editando by remember { mutableStateOf(false) }
    var nuevoNombre by remember { mutableStateOf(proveedor.nombre) }
    var mensajeConfirmacion by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "NIF: ${proveedor.nif}")

            if (editando) {
                // Campo de texto editable
                OutlinedTextField(
                    value = nuevoNombre,
                    onValueChange = { nuevoNombre = it },
                    label = { Text("Nuevo nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Button(
                        onClick = {
                            viewModel.updateProveedor(proveedor.nif, nuevoNombre)
                            mensajeConfirmacion = "Proveedor actualizado correctamente"
                            editando = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text(text = "Guardar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = { editando = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text(text = "Cancelar", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = mensajeConfirmacion)
            } else {
                // Muestra el nombre actual y los botones de acción
                Text(text = "Nombre: ${proveedor.nombre}")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { editando = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                    ) {
                        Text(text = "Editar", color = Color.Black)
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
    }
}