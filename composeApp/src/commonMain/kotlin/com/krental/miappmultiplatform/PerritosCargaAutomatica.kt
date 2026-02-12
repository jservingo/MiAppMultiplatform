import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.krental.miappmultiplatform.PerroRespuesta
import com.krental.miappmultiplatform.cliente
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.launch

@Composable
@Preview
fun PerritosCargaAutomatica() {
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
                    text = "🐶 App realizada con Android Studio 🐶",
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
}