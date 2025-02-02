package com.example.proyectoejemplo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.proyectoejemplo.ui.theme.ProyectoEjemploTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoEjemploTheme {

                MediaList()
            }
        }
    }
}
//@Preview(showBackground = true, widthDp = 400, heightDp = 200)
@Composable
fun  ButtonText(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Hello Antonio",
            style = MaterialTheme.typography.bodyLarge.copy(
                shadow = Shadow (
                    offset = Offset (5f, 5f),
                    blurRadius = 5f,
                    color = Color.Black.copy(alpha = 0.5f)
                )
            ),
            modifier = Modifier
                .clickable { /*TODO*/ }
                .background(Color.Cyan)
                .border(2.dp,color = Color.Blue)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun MediaListItem(item: MediaItem) {
    Column {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(color = Color.Red),
            contentAlignment = Alignment.Center

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current) //Poner confg. extras
                    .data(item.thumb)
                    .crossfade(1000)
                    .transformations(CircleCropTransformation()) // Aplicar transformación circular
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(92.dp),
                tint = Color.White
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@ExperimentalCoilApi
@Preview
@Composable
fun MediaList() {
    LazyColumn {
        items(getMedia()) {item ->
            MediaListItem(item)
        }
    }
}
