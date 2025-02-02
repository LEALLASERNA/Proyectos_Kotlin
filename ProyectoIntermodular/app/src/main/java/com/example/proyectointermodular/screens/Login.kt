package com.example.proyectointermodular.screens

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.R
import com.example.proyectointermodular.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Login(navController: NavHostController, auth: FirebaseAuth) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_login2),
            contentDescription = null,
            contentScale = ContentScale.Crop,// Ajusta la imagen sin deformarla
            modifier = Modifier.matchParentSize()
        )

        // Contenedor principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Opacidad para mejor visibilidad del texto
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(32.dp))

            Text(text = "Email", color = White, fontSize = 24.sp)
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "Contraseña", color = White, fontSize = 24.sp)
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Botón de Login
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate(AppScreens. Home.ruta)
                                Log.i("Jc", "Inicio de sesión correcto")
                            } else {
                                val errorMsg = task.exception?.message ?: "Error desconocido"
                                Toast.makeText(context, "Inicio de sesión fallido: $errorMsg", Toast.LENGTH_SHORT).show()
                                Log.e("Jc", errorMsg)
                            }
                        }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar botón
            ) {
                Text(text = "Login")
            }

            Spacer(Modifier.height(16.dp))

            // Botón para registrar un nuevo usuario
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Por favor, ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                Log.i("Auth", "Usuario creado con éxito")
                            } else {
                                val errorMsg = task.exception?.message ?: "Error desconocido"
                                Toast.makeText(context, "Error al registrar: $errorMsg", Toast.LENGTH_SHORT).show()
                                Log.e("Auth", errorMsg)
                            }
                        }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar botón
            ) {
                Text(text = "Registrarse")
            }
        }
    }
}