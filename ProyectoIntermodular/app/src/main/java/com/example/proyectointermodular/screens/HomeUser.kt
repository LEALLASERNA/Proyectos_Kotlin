
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectointermodular.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeUser(navController: NavHostController, auth: FirebaseAuth) {
    val user = auth.currentUser
    val uid = user?.uid ?: return

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Bienvenido Usuario", fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))

        // Fila para los botones cuadrados
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón "Agregar Proveedor"
            Button(
                onClick = { navController.navigate("AgregarProveedor") },
                modifier = Modifier
                    .size(120.dp) // Tamaño cuadrado
                    .weight(1f)
            ) {
                Text(text = "Agregar Proveedor", textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.width(10.dp)) // Espacio entre botones

            // Botón "Ver Mis Proveedores"
            Button(
                onClick = { navController.navigate("MisProveedores") },
                modifier = Modifier
                    .size(120.dp) // Tamaño cuadrado
                    .weight(1f)
            ) {
                Text(text = "Ver Mis Proveedores", textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón "Cerrar Sesión"
        Button(
            onClick = {
                auth.signOut()
                navController.navigate(AppScreens.Login.ruta) {
                    popUpTo(AppScreens.HomeUser.ruta) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Cerrar Sesión")
        }
    }
}


