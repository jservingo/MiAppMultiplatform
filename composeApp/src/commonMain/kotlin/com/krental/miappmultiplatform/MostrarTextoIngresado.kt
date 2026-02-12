import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MostrarTextoIngresado() {
    // Mostrar el texto que el usuario ingresó en la pantalla

    MaterialTheme(
        // Cambiamos el color principal a un azul moderno
        colorScheme = lightColorScheme(primary = Color(0xFF007BFF))
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Magenta
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
            }
        }
    }
}
