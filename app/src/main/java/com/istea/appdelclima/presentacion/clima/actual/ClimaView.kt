package com.istea.appdelclima.presentacion.clima.actual

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.istea.appdelclima.R
import com.istea.appdelclima.ui.theme.AppDelClimaTheme


@Composable
fun ClimaView(
    modifier: Modifier = Modifier,
    state: ClimaEstado,
    onAction: (ClimaIntencion) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ClimaIntencion.actualizarClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is ClimaEstado.Error -> ErrorView(mensaje = state.mensaje)
            is ClimaEstado.Exitoso -> ClimaView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                st = state.st,
                icon = state.icon
            )

            ClimaEstado.Vacio -> LoadingView()
            ClimaEstado.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView() {
    Text(text = "No hay nada que mostrar")
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Spacer(modifier = Modifier.height(26.dp))
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
            .fillMaxSize()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(26.dp))
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
fun ClimaView(ciudad: String, temperatura: Double, descripcion: String, st: Double, icon: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.93f)
                .padding(0.dp)
                .height(190.dp),

            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Spacer(modifier = androidx.compose.ui.Modifier.width(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Text(
                        text = ciudad,
                        style = TextStyle(
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334756)
                        )

                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .height(60.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${temperatura}°",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF4C29)
                        )
                    )

                    Spacer(modifier = androidx.compose.ui.Modifier.width(100.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.logoapp),
                        contentDescription = "Icono del clima",
                        modifier = Modifier.size(55.dp),
                        tint = Color(0xFFFEC260)
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .height(20.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = descripcion,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .height(20.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sensacion Térmica: ${st}°",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }


            }

        }



        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.93f)
                .padding(0.dp)
                .height(490.dp),

            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Spacer(modifier = androidx.compose.ui.Modifier.width(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Text(
                        text = "Pronóstico extendido",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334756)
                        )

                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Día 1: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Día 2: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Día 3: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Día 4: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Día 5: ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color(0xFF334756)
                        )
                    )

                }


            }

        }

    }

}


/*@Preview(showBackground = true)
@Composable
fun ClimaPreviewVacio() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Vacio, onAction = {})
    }
}*/

/*@Preview(showBackground = true)
@Composable
fun ClimaPreviewError() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Error("Sucedió un error"), onAction = {})
    }
}*/

@Preview(showBackground = true)
@Composable
fun ClimaPreviewExitoso() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Exitoso(ciudad = "Mendoza", temperatura = 0.0, icon = "10d"), onAction = {})
    }
}