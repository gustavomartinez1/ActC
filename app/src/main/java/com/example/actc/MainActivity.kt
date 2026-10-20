package com.example.actc

import android.annotation.SuppressLint
import com.example.actc.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.actc.ui.theme.ActCTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActCTheme() {
                ViewContainer()
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    locale = "es-rMX"
)
@Composable
fun ViewContainer(){
    val intentContex = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope ()
    val mensajeSnackbar = stringResource(id = R.string.back_btn_desc)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_screen)) },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme
                        .secondary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Acción navegación */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back_btn_desc
                            ),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.bottom_app_bar),
                    fontSize =18.sp,

                    )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        floatingActionButton =  {
            FloatingActionButton(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(mensajeSnackbar)
                }
            }
            ){
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

    ){ innerPadding ->
        ContentView(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true, locale = "es-rMX")
@Composable
fun ContentView(modifier: Modifier = Modifier) {
    var counter by rememberSaveable { mutableStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ima),
                contentDescription = "Comida",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.padding(top = 2.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ds),
                    contentDescription = "Like",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { counter++ }
                )
                Text(
                    text = "$counter",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        item {
            Text(
                text = stringResource(R.string.nombre),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        item {
            Text(
                text = stringResource(R.string.apellido),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        item {
            val imagenes = listOf(
                R.drawable.iaas,
                R.drawable.os,
                R.drawable.ts,
                R.drawable.ima,
                R.drawable.ds,
                R.drawable.iaas
            )
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(imagenes) { img ->
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}