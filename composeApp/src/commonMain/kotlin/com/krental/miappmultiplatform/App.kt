package com.krental.miappmultiplatform

import Bienvenida
import JokesChuckNorris
import JokesConBoton
import MostrarImagenLocal
import MostrarTextoIngresado
import PerritosCargaAutomatica
import PerritosConBoton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val cliente = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // Esto evita que la app falle si la API envía datos extra
        }) // Esto le dice a Ktor: "Prepárate para recibir JSON"
    }
}

@Serializable
data class FrasePrueba(val value: String)

@Serializable
data class PerroRespuesta(val message: String, val status: String)

@Composable
@Preview
fun App() {
    //Bienvenida()
    //MostrarImagenLocal()
    //MostrarTextoIngresado()
    //TraerDatosInternet()
    //JokesChuckNorris()
    //JokesConBoton()
    //PerritosConBoton()
    PerritosCargaAutomatica()
}