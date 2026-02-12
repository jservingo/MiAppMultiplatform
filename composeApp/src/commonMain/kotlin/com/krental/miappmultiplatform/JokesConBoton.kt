import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krental.miappmultiplatform.FrasePrueba
import com.krental.miappmultiplatform.cliente
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.launch

@Composable
@Preview
fun JokesConBoton() {
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
}

