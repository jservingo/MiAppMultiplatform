import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun PerritosConBoton() {
    // Mostrar imagen de internet (con Ktor) - Perritos aleatorios
    // No carga la imagen al inicio, solo al hacer clic en el botón

    var imagenUrl by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun cargarPerrito() {
        scope.launch {
            try {
                // 1. Traemos el JSON con la URL de la imagen
                val respuesta: PerroRespuesta =
                    cliente.get("https://dog.ceo/api/breeds/image/random").body()
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
}
