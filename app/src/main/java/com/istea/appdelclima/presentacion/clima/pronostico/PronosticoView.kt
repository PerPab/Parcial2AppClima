package com.istea.appdelclima.presentacion.clima.pronostico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import com.istea.appdelclima.repository.modelos.Clima
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import com.istea.appdelclima.R
import com.istea.appdelclima.repository.modelos.ListForecast
import com.istea.appdelclima.repository.modelos.MainForecast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun convertUnixTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp * 1000) // Convertimos de segundos a milisegundos
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Formato deseado
    return format.format(date)
}

@Composable
fun PronosticoView(
    modifier: Modifier = Modifier,
    state: PronosticoEstado,
    onAction: (PronosticoIntencion) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(PronosticoIntencion.actualizarClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is PronosticoEstado.Error -> ErrorView(mensaje = state.mensaje)
            is PronosticoEstado.Exitoso -> PronosticoView(state.climas)
            PronosticoEstado.Vacio -> LoadingView()
            PronosticoEstado.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "No hay nada que mostrar",
            style = TextStyle(
                fontSize = 21.sp,
                color = Color(0xFFFF4C29)
            )
        )

    }

}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Cargando",
            style = TextStyle(
                fontSize = 21.sp,
                color = Color(0xFFFF4C29)
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        Icon(
            painter = painterResource(id = R.drawable.waiticon),
            contentDescription = "Icono del clima",
            modifier = Modifier.size(80.dp),
            tint = Color(0xFFEEEEEE)
        )

    }
}

@Composable
fun ErrorView(mensaje: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = mensaje,
            style = TextStyle(
                fontSize = 21.sp,
                color = Color(0xFFFF4C29)
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        Icon(
            painter = painterResource(id = R.drawable.alerticon),
            contentDescription = "Icono del clima",
            modifier = Modifier.size(80.dp),
            tint = Color(0xFFEEEEEE)
        )
    }
}

@Composable
fun PronosticoView(climas: List<ListForecast>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Pronóstico Extendido",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 21.sp,
                    color = Color(0xFFFF4C29)
                )
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn {
            items(items = climas) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.93f)
                        .padding(4.dp)
                        .height(135.dp),

                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)

                    ) {
                        Text(text = "${convertUnixTimestampToDate(it.dt)}",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF4C29)
                            ))
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ){
                            Text(text = "Temperatura: °${it.main.temp}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color(0xFF334756)
                                ))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ){
                            Text(text = "Sensación Térmica: °${it.main.feels_like}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color(0xFF334756)
                                ))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ){
                            Text(text = "Temp Min: °${it.main.temp_min}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color(0xFF334756)
                                ))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ){
                            Text(text = "Temp Max: °${it.main.temp_max}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color(0xFF334756)
                                ))
                        }

                    }

                }
            }
        }

    }

}

/*@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    EmptyView()
}*/

/*@Preview(showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorView("Error")
}*/

@Preview(showBackground = true)
@Composable
fun PronosticoViewPreview() {
    // Datos ficticios para probar el preview
    val sampleForecasts = listOf(
        ListForecast(
            dt = 1632922800L,
            main = MainForecast(
                temp = 22.5,
                feels_like = 21.0,
                temp_min = 20.0,
                temp_max = 25.0,
                pressure = 1015,
                sea_level = 1015,
                grnd_level = 1010,
                humidity = 65,
                temp_kf = 0.0
            )
        ),
        ListForecast(
            dt = 1632926400L,
            main = MainForecast(
                temp = 18.0,
                feels_like = 16.5,
                temp_min = 15.0,
                temp_max = 20.0,
                pressure = 1015,
                sea_level = 1015,
                grnd_level = 1010,
                humidity = 70,
                temp_kf = 0.0
            )
        ),
        ListForecast(
            dt = 1632930000L,
            main = MainForecast(
                temp = 19.5,
                feels_like = 18.0,
                temp_min = 17.0,
                temp_max = 22.0,
                pressure = 1014,
                sea_level = 1014,
                grnd_level = 1009,
                humidity = 60,
                temp_kf = 0.0
            )
        )
    )

    // Llamada al Composable con los datos ficticios
    PronosticoView(climas = sampleForecasts)
}


