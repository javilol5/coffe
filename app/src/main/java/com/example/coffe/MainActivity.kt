package com.example.coffe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.coffe.ui.theme.CoffeTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeTheme {
                Aplicacion()
            }
        }
    }
}

@Composable
fun PantallaCarga() {

    val desplazamiento = remember { Animatable(0f) }
    val rotacion = remember { Animatable(0f) }

    var puntos by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {

        while (true) {

            // Primera subida y bajada
            desplazamiento.animateTo(
                targetValue = -20f,
                animationSpec = tween(500)
            )

            desplazamiento.animateTo(
                targetValue = 0f,
                animationSpec = tween(500)
            )

            // Segunda subida y bajada
            desplazamiento.animateTo(
                targetValue = -20f,
                animationSpec = tween(500)
            )

            desplazamiento.animateTo(
                targetValue = 0f,
                animationSpec = tween(500)
            )

            // Rotación de 360 grados
            rotacion.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                )
            )

            // Reiniciar rotación
            rotacion.snapTo(0f)
        }
    }
    // Animación del texto
    LaunchedEffect(Unit) {

        while (true) {

            delay(500)

            puntos++

            if (puntos > 3) {
                puntos = 1
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .offset(y = desplazamiento.value.dp)
                    .rotate(rotacion.value)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Loading " + ".".repeat(puntos),
                fontSize = 18.sp
            )
        }
    }
}


@Composable
fun PantallaPrincipal(onCafeSeleccionado: (Int) -> Unit) {

    val cafes = listOf(
        R.drawable.cafe1,
        R.drawable.cafe2,
        R.drawable.cafe3,
        R.drawable.cafe4,
        R.drawable.cafe5,
        R.drawable.cafe6,
        R.drawable.cafe7,
        R.drawable.cafe8,
        R.drawable.cafe9
    )

    val nombres = listOf(
        "Espresso",
        "Americano",
        "Cappuccino",
        "Latte",
        "Mocha",
        "Macchiato",
        "Flat White",
        "Frappé",
        "Caramel"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Elige tu cafe",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            items(cafes.size) { indice ->

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        onCafeSeleccionado(cafes[indice])
                    }
                ) {

                    Image(
                        painter = painterResource(id = cafes[indice]),
                        contentDescription = nombres[indice],
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = nombres[indice],
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaCondimentos(
    imagenSeleccionada: Int,
    azucar: Int,
    miel: Int,
    canela: Int,
    chocolate: Int,

    sumarAzucar: () -> Unit,
    restarAzucar: () -> Unit,

    sumarMiel: () -> Unit,
    restarMiel: () -> Unit,

    sumarCanela: () -> Unit,
    restarCanela: () -> Unit,

    sumarChocolate: () -> Unit,
    restarChocolate: () -> Unit,

    cancelar: () -> Unit,
    finalizar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 80.dp, end = 10.dp, bottom = 20.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imagenSeleccionada),
                contentDescription = "Cafe seleccionado",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(120.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        FilaContador("Azúcar", azucar, restarAzucar, sumarAzucar)

        FilaContador("Miel", miel, restarMiel, sumarMiel)

        FilaContador("Canela", canela, restarCanela, sumarCanela)

        FilaContador("Chocolate", chocolate, restarChocolate, sumarChocolate)

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = cancelar) {
                Text("Cancelar")
            }

            Button(onClick = finalizar) {
                Text("Finalizar")
            }
        }
    }
}

@Composable
fun FilaContador(
    nombre: String,
    cantidad: Int,
    restar: () -> Unit,
    sumar: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = nombre,
            modifier = Modifier.weight(1f),
            fontSize = 20.sp
        )

        Button(onClick = restar) {
            Text("-")
        }

        Text(
            text = cantidad.toString(),
            modifier = Modifier.padding(horizontal = 15.dp),
            fontSize = 20.sp
        )

        Button(onClick = sumar) {
            Text("+")
        }
    }
}



@Composable
fun NumeroPantalla(
    imagenSeleccionada: Int,
    azucar: Int,
    miel: Int,
    canela: Int,
    chocolate: Int,
    numero: Int
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 80.dp, end = 20.dp, bottom = 20.dp)
    ) {

        Image(
            painter = painterResource(id = imagenSeleccionada),
            contentDescription = "Cafe seleccionado",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(120.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp)
        ) {

            Text("$azucar x Azúcar", fontSize = 18.sp)
            Text("$miel x Miel", fontSize = 18.sp)
            Text("$canela x Canela", fontSize = 18.sp)
            Text("$chocolate x Chocolate", fontSize = 18.sp)
        }

        Text(
            text = "N$numero",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 60.sp
        )
    }
}


@Composable
fun Aplicacion() {

    var cargando by remember { mutableStateOf(true) }
    var imagenSeleccionada by remember { mutableIntStateOf(0) }
    var mostrarCondimentos by remember { mutableStateOf(false) }
    var mostrarNumero by remember { mutableStateOf(false) }

    var azucar by remember { mutableIntStateOf(0) }
    var miel by remember { mutableIntStateOf(0) }
    var canela by remember { mutableIntStateOf(0) }
    var chocolate by remember { mutableIntStateOf(0) }

    var numeroAleatorio by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(3000)
        cargando = false
    }

    if (cargando) {

        PantallaCarga()

    } else if (mostrarNumero) {

        NumeroPantalla(
            imagenSeleccionada,
            azucar,
            miel,
            canela,
            chocolate,
            numeroAleatorio
        )

    } else if (mostrarCondimentos) {

        PantallaCondimentos(
            imagenSeleccionada,
            azucar,
            miel,
            canela,
            chocolate,

            { azucar++ },
            { if (azucar > 0) azucar-- },

            { miel++ },
            { if (miel > 0) miel-- },

            { canela++ },
            { if (canela > 0) canela-- },

            { chocolate++ },
            { if (chocolate > 0) chocolate-- },

            {
                mostrarCondimentos = false
                azucar = 0
                miel = 0
                canela = 0
                chocolate = 0
            },

            {
                numeroAleatorio = (1..100).random()
                mostrarNumero = true
            }
        )

    } else {

        PantallaPrincipal { cafe ->

            imagenSeleccionada = cafe

            azucar = 0
            miel = 0
            canela = 0
            chocolate = 0

            mostrarCondimentos = true
        }
    }
}

@Preview
@Composable
fun vista() {
    Aplicacion()
}