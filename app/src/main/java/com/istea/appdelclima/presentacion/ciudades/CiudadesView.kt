package com.istea.appdelclima.presentacion.ciudades

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istea.appdelclima.repository.modelos.Ciudad
import com.istea.appdelclima.ui.theme.AppDelClimaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView (
    modifier: Modifier = Modifier,
    state : CiudadesEstado,
    onAction: (CiudadesIntencion)->Unit
) {
    var value by remember{ mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(0.dp)
        .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = value,
            label = {
                Text(text = "Ingrese una Ciudad",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334756)
                    )

                    )},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            ),
            onValueChange = {
                value = it
                onAction(CiudadesIntencion.Buscar(value))
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        when(state) {
            CiudadesEstado.Cargando -> Text(text = "Cargando",
                style = TextStyle(
                    fontSize = 21.sp,
                    color = Color(0xFFFF4C29)
                ))
            is CiudadesEstado.Error -> Text(
                text = state.mensaje,
                style = TextStyle(
                    fontSize = 21.sp,
                    color = Color(0xFFDA0037)
                ))
            is CiudadesEstado.Resultado -> ListaDeCiudades(state.ciudades) {
                onAction(
                    CiudadesIntencion.Seleccionar(it)
                )
            }
            CiudadesEstado.Vacio -> Text(
                text = "No hay resultados",
                style = TextStyle(
                    fontSize = 21.sp,
                    color = Color(0xFFFF4C29)
                ))
        }
    }
}

@Preview(showBackground = true, name = "Cargando Estado")
@Composable
fun CiudadesViewCargandoPreview() {
    AppDelClimaTheme {
        CiudadesView(
            state = CiudadesEstado.Cargando,
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeCiudades(ciudades: List<Ciudad>, onSelect: (Ciudad)->Unit) {
    LazyColumn {
        items(items = ciudades) {
            Card(
                onClick = { onSelect(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF082032))
                    .padding(horizontal = 10.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color(0XFF082032),
                    containerColor = Color(0XFF082032)
                ),
                shape = RoundedCornerShape(0.dp), // Bordes redondeados
                border = BorderStroke(0.dp, Color(0xFF082032)),


                ) {
                Text(
                    text = it.name,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color(0xFFFF4C29)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaDeCiudades() {
    // Lista de ejemplo de ciudades
    val ciudades = listOf(
        Ciudad(name = "Madrid", lat = 40.4168f, lon = -3.7038f, country = "España", state = "Madrid"),
        Ciudad(name = "Barcelona", lat = 41.3851f, lon = 2.1734f, country = "España", state = "Cataluña"),
        Ciudad(name = "Sevilla", lat = 37.3886f, lon = -5.9823f, country = "España", state = "Andalucía"),
        Ciudad(name = "Valencia", lat = 39.4699f, lon = -0.3763f, country = "España", state = "Valencia"),
        Ciudad(name = "Bilbao", lat = 43.2630f, lon = -2.9340f, country = "España", state = "País Vasco")
    )

    // Vista previa de la ListaDeCiudades
    ListaDeCiudades(ciudades = ciudades) { ciudad ->
        // Acción cuando se selecciona una ciudad (simulada)
        println("Ciudad seleccionada: ${ciudad.name}")
    }
}



