package com.example.proyectointermodular.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectointermodular.models.Proveedor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProveedorViewModel : ViewModel() {

    // ✅ Instancia de Firebase Firestore
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _nif = MutableLiveData<String>()
    val nif: LiveData<String> = _nif

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _proveedores = MutableStateFlow<List<Proveedor>>(emptyList())
    val proveedores: StateFlow<List<Proveedor>> = _proveedores

    private val _isButtonEnable = MutableLiveData<Boolean>()
    val isButtonEnable: LiveData<Boolean> = _isButtonEnable

    private val _mensajeConfirmacion = MutableLiveData<String>()
    val mensajeConfirmacion: LiveData<String> = _mensajeConfirmacion

    fun onCompletedFields(nif: String, nombre: String) {
        _nif.value = nif
        _nombre.value = nombre
        _isButtonEnable.value = enableButton(nif, nombre)
    }

    private fun enableButton(nif: String, nombre: String): Boolean =
        nif.isNotBlank() && nombre.isNotBlank()

    //Agregar proveedor a Firebase
    fun addProveedor() {
        val nifValue = _nif.value ?: return
        val nombreValue = _nombre.value ?: return

        viewModelScope.launch {
            try {
                val proveedor = Proveedor(nifValue, nombreValue)
                db.collection("proveedores").document(nifValue).set(proveedor).await()
                _mensajeConfirmacion.value = "Proveedor agregado correctamente"
                loadProveedores()  // Actualizar lista
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al guardar el proveedor"
            }
        }
    }

    //Eliminar proveedor por NIF
    fun deleteProveedor(nif: String) {
        viewModelScope.launch {
            try {
                db.collection("proveedores").document(nif).delete().await()
                _mensajeConfirmacion.value = "Proveedor eliminado correctamente"
                loadProveedores()  // Actualizar lista
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al eliminar el proveedor"
            }
        }
    }

    //Obtener proveedores desde Firebase Firestore
    fun loadProveedores() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("proveedores").get().await()
                _proveedores.value = snapshot.toObjects(Proveedor::class.java)
            } catch (e: Exception) {
                _proveedores.value = emptyList()
            }
        }
    }
    //Actualizar proveedor por NIF
    fun updateProveedor(nif: String, nuevoNombre: String) {
        viewModelScope.launch {
            try {
                db.collection("proveedores").document(nif)
                    .update("nombre", nuevoNombre)
                    .await()

                _mensajeConfirmacion.value = "Proveedor actualizado correctamente"
                loadProveedores() // Actualizar lista después de editar
            } catch (e: Exception) {
                _mensajeConfirmacion.value = "Error al actualizar el proveedor"
            }
        }
    }
}
