package com.example.proyectointermodular.ViewModel

import androidx.lifecycle.ViewModel
import com.example.proyectointermodular.models.Proveedor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProveedoresListarViewModel: ViewModel() {

    suspend fun getProveedoresViewModel(): List<Proveedor> {
        return try {
            // Obtener instancia de Firebase Firestore
            val db = FirebaseFirestore.getInstance()

            //Almacenar el nombre de la colección
            var nombre_coleccion = "proveedores"

            val query = db.collection(nombre_coleccion).get().await()

            val proveedores = mutableListOf<Proveedor>()

            for (document in query.documents) {
                val proveedor = document.toObject(Proveedor::class.java)
                if (proveedor != null) {
                    proveedores.add(proveedor)
                }
            }
            query.toObjects(Proveedor::class.java)

        } catch (e: Exception) {
            emptyList()
        }
    }
}
