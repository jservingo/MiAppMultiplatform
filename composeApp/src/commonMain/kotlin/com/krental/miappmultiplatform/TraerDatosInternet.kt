package com.krental.miappmultiplatform

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

@Composable
fun TraerDatosInternet() {
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
}

