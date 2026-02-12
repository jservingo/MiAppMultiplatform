import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import miappmultiplatform.composeapp.generated.resources.Res
import miappmultiplatform.composeapp.generated.resources.fotomural
import org.jetbrains.compose.resources.painterResource

@Composable
fun MostrarImagenLocal() {
    //  Cambiar background con un botón y mostrar imagen

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

                Text("Se muestran dos imagenes para probar desplazamiento vertical")

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(Res.drawable.fotomural),
                    contentDescription = "Imagen de prueba",
                    modifier = Modifier.width(600.dp)
                )
            }
        }
    }
}

