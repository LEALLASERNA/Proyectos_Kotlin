package com.example.proyectointermodular.screens.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavHostController
import com.example.proyectointermodular.R
import com.example.proyectointermodular.models.Carta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MisCartas(navController: NavHostController, auth: FirebaseAuth) {
    val user = auth.currentUser
    val uid = user?.uid ?: return
    var listaCartas by remember { mutableStateOf(emptyList<Carta>()) }
    var colorFiltro by remember { mutableStateOf("") }

    // Cargar las cartas del usuario desde Firestore
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("usuarios").document(uid)
            .collection("mis_cartas")
            .get()
            .addOnSuccessListener { snapshot ->
                listaCartas = snapshot.toObjects(Carta::class.java)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Mis Cartas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(10.dp))

        // Filtro por color
        OutlinedTextField(
            value = colorFiltro,
            onValueChange = { colorFiltro = it },
            label = { Text("Filtrar por color") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        val cartasFiltradas = if (colorFiltro.isBlank()) listaCartas else listaCartas.filter { it.color.contains(colorFiltro, ignoreCase = true) }

        if (cartasFiltradas.isEmpty()) {
            Text(text = "No hay cartas disponibles", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn {
                items(cartasFiltradas) { carta ->
                    MisCartasItem(carta, uid) { codigo ->
                        //Eliminar carta cuando se presiona el botón
                        FirebaseFirestore.getInstance()
                            .collection("usuarios").document(uid)
                            .collection("mis_cartas").document(codigo)
                            .delete()
                            .addOnSuccessListener {
                                listaCartas = listaCartas.filter { it.codigo != codigo }
                            }
                    }
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
fun MisCartasItem(carta: Carta, uid: String, onDelete: (String) -> Unit) {
    val contexto = LocalContext.current
    val recursoId = remember(carta.color) {
        contexto.resources.getIdentifier(carta.color, "drawable", contexto.packageName)
    }
    val imagenPainter = if (recursoId != 0) {
        painterResource(id = recursoId)
    } else {
        painterResource(id = R.drawable.default_i) // Imagen por defecto si no se encuentra la carta
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de la carta
            Image(
                painter = imagenPainter,
                contentDescription = "Imagen de la carta",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Datos de la carta
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
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

            // Botón de eliminar
            Button(
                onClick = { onDelete(carta.codigo) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Eliminar", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}
