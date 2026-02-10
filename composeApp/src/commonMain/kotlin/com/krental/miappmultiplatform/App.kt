package com.krental.miappmultiplatform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import miappmultiplatform.composeapp.generated.resources.Res
import miappmultiplatform.composeapp.generated.resources.compose_multiplatform
import miappmultiplatform.composeapp.generated.resources.fotomural
import coil3.compose.AsyncImage
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor.KtorNetworkFetcherFactory

/*
val cliente = HttpClient() {
    // Aquí configuraremos cosas después
}
*/

val cliente = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // Esto evita que la app falle si la API envía datos extra
        }) // Esto le dice a Ktor: "Prepárate para recibir JSON"
    }
}

/*
@Serializable
data class FrasePrueba(val value: String)
*/

@Serializable
data class PerroRespuesta(val message: String, val status: String)

@Composable
@Preview
fun App() {
    // Mostrar imagen de internet (con Ktor) - Perritos aleatorios
    // Carga automática al abrir la app en Linux Mint

    var imagenUrl by remember { mutableStateOf("") }
    var estaCargando by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // 3. Función para traer datos (Se usa en el botón y al inicio)
    fun cargarPerrito() {
        scope.launch {
            estaCargando = true
            try {
                val respuesta: PerroRespuesta = cliente.get("https://dog.ceo/api/breeds/image/random").body()
                imagenUrl = respuesta.message
            } catch (e: Exception) {
                println("Error de red: ${e.message}")
            } finally {
                estaCargando = false
            }
        }
    }

    // 4. EL TRUCO: Carga automática al abrir la app en Linux Mint
    LaunchedEffect(Unit) {
        cargarPerrito()
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🐶 Mi App de Perritos 🐶",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Contenedor de la imagen
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (imagenUrl.isNotEmpty()) {
                        AsyncImage(
                            model = imagenUrl,
                            contentDescription = "Perrito aleatorio",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Muestra el círculo de carga sobre la imagen o el fondo gris
                    if (estaCargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { cargarPerrito() },
                    enabled = !estaCargando // Desactiva el botón mientras descarga
                ) {
                    Text(if (estaCargando) "Buscando..." else "¡Ver otro perrito!")
                }
            }
        }
    }
    /* ----------------------------------------
    // Mostrar imagen de internet (con Ktor) - Perritos aleatorios
    // No carga la imagen al inicio, solo al hacer clic en el botón

    var imagenUrl by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun cargarPerrito() {
        scope.launch {
            try {
                // 1. Traemos el JSON con la URL de la imagen
                val respuesta: PerroRespuesta = cliente.get("https://dog.ceo/api/breeds/image/random").body()
                imagenUrl = respuesta.message
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imagenUrl.isNotEmpty()) {
                // 2. Este componente descarga y renderiza la imagen de internet
                AsyncImage(
                    model = imagenUrl,
                    contentDescription = "Perrito aleatorio",
                    modifier = Modifier.size(300.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { cargarPerrito() }) {
                Text("¡Ver otro perrito!")
            }
        }
    }
    */
    /* ----------------------------------------
    // Traer datos de internet (con Ktor) - Jokes de Chuck Norris
    // con botón para cargar otra frase

    var textoInternet by remember { mutableStateOf("Presiona el botón para cargar...") }
    val scope = rememberCoroutineScope() // 1. Creamos el scope para el botón

    // Función interna para traer los datos (así no repetimos código)
    fun cargarDatos() {
        scope.launch { // 2. Lanzamos la tarea en un hilo del Xeon
            textoInternet = "Cargando joke de chucknorries..."
            try {
                val response: FrasePrueba = cliente.get("https://api.chucknorris.io/jokes/random").body()
                textoInternet = response.value
            } catch (e: Exception) {
                textoInternet = "Error de red: ${e.message}"
            }
        }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = textoInternet,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 3. EL BOTÓN MÁGICO
            Button(onClick = { cargarDatos() }) {
                Text("Traer otra frase")
            }
        }
    }
    */
    /* ---------------------------------------
    // Traer datos de internet (con Ktor) - Jokes de Chuck Norris
    // sin botón, solo al abrir la app (esto es lo que se pidió originalmente)

    var textoInternet by remember { mutableStateOf("Cargando frase célebre...") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // El bloque que trae los datos de internet
            LaunchedEffect(Unit) {
                try {
                    // Hacemos la petición a la API
                    val response: FrasePrueba = cliente.get("https://api.chucknorris.io/jokes/random").body()
                    textoInternet = response.value
                } catch (e: Exception) {
                    textoInternet = "Error de red: ${e.message}"
                }
            }

            // 3. Mostramos el texto en tu Linux Mint
            Text(
                text = textoInternet,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
    */
    /* ---------------------------------------
    // Traer datos de internet (con Ktor)

    // Estado para guardar lo que traigamos de internet
    var textoInternet by remember { mutableStateOf("Cargando datos...") }

    // Este bloque se ejecuta cuando la app abre
    LaunchedEffect(Unit) {
        try {
            // Hacemos una petición de prueba a una web de texto
            val response: String = cliente.get("https://raw.githubusercontent.com").bodyAsText()
            textoInternet = "Conexión exitosa. El archivo mide: ${response.length} caracteres."
        } catch (e: Exception) {
            textoInternet = "Error: ${e.message}"
        }
    }

    // Mostrar el resultado en tu ventana de Linux Mint
    Text(textoInternet)
    */
    /* ---------------------------------------
    //  Cambiar background y mostrar imagen (con un botón para cambiar el color de fondo)

    // 1. Creamos el estado para el color (por defecto blanco)
    var colorFondo by remember { mutableStateOf(Color.White) }

    // 2. Definimos una lista de colores divertidos
    val colores = listOf(Color.Cyan, Color.Yellow, Color.Magenta, Color.Green, Color(0xFFBB86FC))

    MaterialTheme(
        // Cambiamos el color principal a un azul moderno
        colorScheme = lightColorScheme(primary = Color(0xFF007BFF))
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorFondo
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    // 3. Al hacer clic, elegimos un color al azar de la lista
                    colorFondo = colores.random()
                }) {
                    Text("¡Cambiar Color de Fondo!")
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Mostramos la imagen que pegaste
                Image(
                    painter = painterResource(Res.drawable.fotomural),
                    contentDescription = "Imagen de prueba",
                    modifier = Modifier.width(600.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¡Hola desde Linux Mint!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary // Usa el azul que definimos arriba
                )

                // Declaramos el estado (Esto es puro Compose)
                var nombre by remember { mutableStateOf("") }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = nombre,
                    onValueChange = { nombre = it }, // Actualiza el estado al escribir
                    label = { Text("Escribe tu nombre") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // El texto cambia mágicamente cuando 'nombre' cambia
                Text("Hola, $nombre", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(Res.drawable.fotomural),
                    contentDescription = "Imagen de prueba",
                    modifier = Modifier.width(600.dp)
                )
            }
        }
    }
    */
    /* ---------------------------------------
    // Código original de tu App.kt (con algunos ajustes para que funcione)

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
     */
}