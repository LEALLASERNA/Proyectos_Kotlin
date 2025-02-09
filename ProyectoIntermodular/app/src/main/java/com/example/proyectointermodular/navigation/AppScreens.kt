package com.example.proyectointermodular.navigation

sealed class AppScreens(val ruta: String) {
     // Login
     object Login : AppScreens("Login")

     // Pantallas del Administrador
     object Home : AppScreens("Home")
     object ProveedorAlta : AppScreens("ProveedorAlta")
     object ProveedoresEliminar : AppScreens("ProveedoresEliminar")
     object ProveedoresListar : AppScreens("ProveedoresListar")
     object ProveedoresEditar : AppScreens("ProveedoresEditar")
     object AdminUsuarios : AppScreens("AdminUsuarios")

     // Pantallas del Usuario
     object HomeUser : AppScreens("HomeUser")
     object AgregarProveedor : AppScreens("AgregarProveedor")
     object MisProveedores : AppScreens("MisProveedores")
}
