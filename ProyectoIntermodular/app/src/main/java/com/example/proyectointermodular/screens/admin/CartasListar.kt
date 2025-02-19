package com.example.proyectointermodular.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectointermodular.R
import com.example.proyectointermodular.ViewModel.CartaViewModel
import com.example.proyectointermodular.models.Carta
import com.google.firebase.auth.FirebaseAuth


@Composable
fun CartasListar(navController: NavHostController, auth: FirebaseAuth, viewModel: CartaViewModel = viewModel()) {
    val listaCartas by viewModel.cartas.collectAsState()

    // Cargar la lista de cartas al abrir la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadCartas()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Lista de Cartas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(10.dp))

        if (listaCartas.isEmpty()) {
            Text(text = "No hay cartas disponibles", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn {
                items(listaCartas) { carta ->
                    CartaItem(navController, carta, viewModel)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun CartaItem(
    navController: NavHostController,
    carta: Carta,
    viewModel: CartaViewModel
) {
    var editando by remember { mutableStateOf(false) }
    var nuevoNombre by remember { mutableStateOf(carta.nombre) }
    var nuevoColor by remember { mutableStateOf(carta.color) }
    var nuevoPd by remember { mutableStateOf(carta.pd) }

    // Obtener la imagen desde drawable usando el código de la carta
    val contexto = LocalContext.current
    val recursoId = remember(carta.color) {
        contexto.resources.getIdentifier(carta.color, "drawable", contexto.packageName)
    }
    val imagenPainter = if (recursoId != 0) { //significa si encuentra la imagen
        painterResource(id = recursoId)
    } else {
        painterResource(id = R.drawable.default_i) // Imagen por defecto si no se encuentra la carta
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sección de la Imagen
            Image(
                painter = imagenPainter,
                contentDescription = "Imagen de la carta",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Sección de Detalles y Edición
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                if (editando) {
                    // Modo Edición
                    OutlinedTextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = nuevoColor,
                        onValueChange = { nuevoColor = it },
                        label = { Text("Color") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = nuevoPd,
                        onValueChange = { nuevoPd = it },
                        label = { Text("Poder Defensivo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.updateCarta(
                                    carta.nombre,
                                    nuevoNombre,
                                    nuevoColor,
                                    nuevoPd
                                )
                                editando = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = "Guardar", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { editando = false },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(text = "Cancelar", color = MaterialTheme.colorScheme.onSecondary)
                        }
                    }
                } else {
                    // Modo Vista
                    Column {
                        Text(
                            text = "Nombre: ${carta.nombre}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Color: ${carta.color}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Poder Defensivo: ${carta.pd}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { editando = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text(text = "Editar", color = MaterialTheme.colorScheme.onTertiary)
                        }
                        Button(
                            onClick = { viewModel.deleteCarta(carta.codigo) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text(text = "Eliminar", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }
        }
    }
}