import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krental.miappmultiplatform.FrasePrueba
import com.krental.miappmultiplatform.cliente
import io.ktor.client.call.body
import io.ktor.client.request.get

@Composable
@Preview
fun JokesChuckNorris() {
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
                    val response: FrasePrueba =
                        cliente.get("https://api.chucknorris.io/jokes/random").body()
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
}

