package com.istea.appdelclima.presentacion.clima

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.appdelclima.presentacion.clima.actual.ClimaView
import com.istea.appdelclima.presentacion.clima.actual.ClimaViewModel
import com.istea.appdelclima.presentacion.clima.actual.ClimaViewModelFactory
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoView
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoViewModel
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoViewModelFactory
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.repository.modelos.MainForecast
import com.istea.appdelclima.router.Enrutador

@Composable
fun ClimaPage(
    navHostController: NavHostController,
    lat: Float,
    lon: Float,
    nombre: String
) {
    var showPronostico by remember { mutableStateOf(false) }
    val viewModel: ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon,
            nombre = nombre
        )
    )
    val pronosticoViewModel: PronosticoViewModel = viewModel(
        factory = PronosticoViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            nombre = nombre
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0XFF082032)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClimaView(
            state = viewModel.uiState,
            onAction = { intencion ->
                viewModel.ejecutar(intencion)
            }
        )
        Button(
            onClick = {
                showPronostico = !showPronostico
            },
            colors = ButtonDefaults.buttonColors(contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Text(
                text = if (showPronostico) "Ocultar Pronóstico Extendido" else "Mostrar Pronóstico Extendido",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (showPronostico) {
            PronosticoView(
                state = pronosticoViewModel.uiState,
                onAction = { intencion ->
                    pronosticoViewModel.ejecutar(intencion)
                }
            )
        }

    }

}



