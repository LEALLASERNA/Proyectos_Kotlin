package com.example.proyectointermodular.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.ViewModel.CartaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable

fun CartaAlta(navController: NavHostController,
                  auth: FirebaseAuth,
                  ViewModel: CartaViewModel) {

    val db = FirebaseFirestore.getInstance()

    var nombre_coleccion = "cartas"

    val codigo:String by ViewModel.codigo.observeAsState(initial = "")
    val nombre:String by ViewModel.nombre.observeAsState (initial = "")
    val color:String by ViewModel.color.observeAsState (initial = "")
    val pd:String by ViewModel.pd.observeAsState (initial = "")

    val isButtonEnable:Boolean by ViewModel.isButtonEnable.observeAsState (initial = false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = "Registro de Carta",
                fontWeight = FontWeight.ExtraBold,

            )
            Spacer(modifier = Modifier.size(20.dp))

            // Campo para el código
            OutlinedTextField(
                value = codigo,
                onValueChange = { ViewModel.onCompletedFields(codigo = it, nombre = nombre, color = color, pd = pd)},
                label = { Text("Introduce el código") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.size(5.dp))

            // Campo para el nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { ViewModel.onCompletedFields(codigo = codigo, nombre = it, color = color, pd = pd)},
                label = { Text("Introduce el nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.size(5.dp))

            // Campo para el color
            OutlinedTextField(
                value = color,
                onValueChange = { ViewModel.onCompletedFields(codigo = codigo, nombre = nombre, color = it, pd = pd)},
                label = { Text("Introduce el color") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.size(5.dp))

            // Campo para PD
            OutlinedTextField(
                value = pd,
                onValueChange = { ViewModel.onCompletedFields(codigo = codigo, nombre = nombre, color = color, pd = it)},
                label = { Text("Introduce el PD") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.size(10.dp))

            // Botón para guardar los datos
            val mensajeConfirmacion = remember { mutableStateOf("") }
            Button(
                onClick = {
                    if (codigo.isNotEmpty() && nombre.isNotEmpty()) {
                        val dato = hashMapOf(
                            "codigo" to codigo,
                            "nombre" to nombre,
                            "color" to color,
                            "pd" to pd
                        )

                        db.collection(nombre_coleccion)
                            .document(codigo) // Usamos el código como ID del documento
                            .set(dato)
                            .addOnSuccessListener {
                                mensajeConfirmacion.value = "Datos guardados correctamente"
                            }
                            .addOnFailureListener {
                                mensajeConfirmacion.value = "No se ha podido guardar"
                            }
                    } else {
                        mensajeConfirmacion.value = "Por favor, completa todos los campos."
                    }
                },
                enabled = isButtonEnable,
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = "Guardar")
            }

            Spacer(modifier = Modifier.size(10.dp))
            Text(text = mensajeConfirmacion.value)
        }
    }
}
