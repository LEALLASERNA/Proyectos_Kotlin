package com.example.proyectointermodular.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectointermodular.models.Carta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CartaViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _codigo = MutableLiveData<String>()
    val codigo: LiveData<String> = _codigo

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _color = MutableLiveData<String>()
    val color: LiveData<String> = _color

    private val _pd = MutableLiveData<String>()
    val pd: LiveData<String> = _pd

    private val _cartas = MutableStateFlow<List<Carta>>(emptyList())
    val cartas: StateFlow<List<Carta>> = _cartas

    private val _mensajeConfirmacion = MutableStateFlow("")
    val mensajeConfirmacion: StateFlow<String> = _mensajeConfirmacion

    private val _isButtonEnable = MutableLiveData<Boolean>()
    val isButtonEnable: LiveData<Boolean> = _isButtonEnable

    private fun enableButton(codigo: String, nombre: String, color: String, pd: String): Boolean =
        codigo.isNotBlank() && nombre.isNotBlank() && color.isNotBlank() && pd.isNotBlank()

    fun onCompletedFields(codigo: String, nombre: String, color: String, pd: String) {
        _codigo.value = codigo
        _nombre.value = nombre
        _color.value = color
        _pd.value = pd
        _isButtonEnable.value = enableButton(codigo, nombre, color, pd)
    }

    // Cargar todas las cartas (Lista global para admins y usuarios)
    fun loadCartas() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("cartas").get().await()
                _cartas.value = snapshot.toObjects(Carta::class.java)
            } catch (e: Exception) {
                _cartas.value = emptyList()
            }
        }
    }

    // Agregar nueva carta (Solo admins)
    fun addCarta(codigo: String, nombre: String, color: String, pd: String, imageUrl: String) {
        viewModelScope.launch {
            try {
                val carta = Carta(codigo, nombre, color, pd, imageUrl)
                db.collection("cartas").document(codigo).set(carta).await()
                _mensajeConfirmacion.value = "Carta agregada correctamente"
                loadCartas()
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al agregar la carta"
            }
        }
    }
    // Subir imagen de la carta (Solo admins)
    suspend fun uploadImage(imageUri: Uri): String? {
        return try {
            val fileName = "cartas/${imageUri.lastPathSegment}" // Nombre único para la imagen
            val storageRef = FirebaseStorage.getInstance().reference.child(fileName)
            val uploadTask = storageRef.putFile(imageUri).await() // Sube la imagen
            storageRef.downloadUrl.await().toString() // Obtiene la URL de descarga
        } catch (e: Exception) {
            null // Devuelve null si hay un error
        }
    }

    // Eliminar carta (Solo admins)
    fun deleteCarta(codigo: String) {
        viewModelScope.launch {
            try {
                db.collection("cartas").document(codigo).delete().await()
                _mensajeConfirmacion.value = "Carta eliminada correctamente"
                loadCartas()
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al eliminar la carta"
            }
        }
    }

    //Actualizar proveedor por Codigo (Solo admins)
    fun updateCarta(codigo: String, nuevoNombre: String, nuevoColor: String, nuevoPd:String) {
        viewModelScope.launch {
            try {
                db.collection("cartas").document(codigo)
                    .update(
                        mapOf(
                            "nombre" to nuevoNombre,
                            "color" to nuevoColor,
                            "pd" to nuevoPd
                        )
                    )
                    .await()

                _mensajeConfirmacion.value = "Proveedor actualizado correctamente"
                loadCartas() // Actualizar lista después de editar
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al actualizar el proveedor"
            }
        }
    }

    // Guardar carta en la lista personal del usuario (Usuarios)
    fun agregarCartaUsuario(uid: String, carta: Carta) {
        viewModelScope.launch {
            try {
                db.collection("usuarios").document(uid).collection("mis_cartas")
                    .document(carta.nombre).set(carta).await()
                _mensajeConfirmacion.value = "Carta agregada a tu lista"
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al agregar la carta"
            }
        }
    }

    // Cargar lista personal del usuario
    fun loadCartasUsuario(uid: String) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("usuarios").document(uid).collection("mis_cartas").get().await()
                _cartas.value = snapshot.toObjects(Carta::class.java)
            } catch (e: Exception) {
                _cartas.value = emptyList()
            }
        }
    }

}
