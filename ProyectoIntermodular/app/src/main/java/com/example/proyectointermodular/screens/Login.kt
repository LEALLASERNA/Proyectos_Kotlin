package com.example.proyectointermodular.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.R
import com.example.proyectointermodular.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun Login(navController: NavHostController, auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_login2),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.Center)
                .offset(y = (-200).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0f))
                .padding(32.dp)
                .offset(y = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Iniciar Sesión", color = Color.White, fontSize = 28.sp)

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Black) }, // Color del label
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.let {
                                    db.collection("usuarios").document(user.uid)
                                        .get()
                                        .addOnSuccessListener { document ->
                                            val rol = document.getString("rol") ?: "usuario"
                                            if (rol == "admin") {
                                                navController.navigate(AppScreens.Home.ruta) {
                                                    popUpTo(AppScreens.Login.ruta) { inclusive = true }
                                                }
                                            } else {
                                                navController.navigate(AppScreens.HomeUser.ruta) {
                                                    popUpTo(AppScreens.Login.ruta) { inclusive = true }
                                                }
                                            }
                                        }
                                }
                            } else {
                                val errorMsg = task.exception?.message ?: "Error desconocido"
                                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Iniciar Sesión")
            }

            Spacer(Modifier.height(10.dp))

            //Modificar el registro para asignar un rol
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Por favor, ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.let {
                                    val userData = hashMapOf(
                                        "email" to email,
                                        "rol" to "usuario" // Todos los nuevos usuarios son "usuario" por defecto
                                    )

                                    db.collection("usuarios").document(user.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                            navController.navigate(AppScreens.HomeUser.ruta) {
                                                popUpTo(AppScreens.Login.ruta) { inclusive = true }
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                val errorMsg = task.exception?.message ?: "Error desconocido"
                                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                            }
                           }
                },
                modifier = Modifier.
                    fillMaxWidth()

            ) {
                Text(text = "Registrarse")
            }
        }
    }
}
