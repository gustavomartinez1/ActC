package com.example.actc

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.actc.ui.theme.ActCTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val tab = "MainActivity"
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }
        lifecycleScope.launch {
            delay(3000L)
            keepSplash = false
        }
        super.onCreate(savedInstanceState)
        showToast("Funcion onCreate()")
        enableEdgeToEdge()
        setContent {
            ActCTheme {
                ViewContainer()
            }
        }
    }

    override fun onStart(){
        super.onStart()
        showToast("Funcion OnStart()")
    }

    private fun showToast(message:String){
        val toast = Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG,
        )
        toast.show()
        Log.i(tab, message)
    }
}

fun openWebScreen(context: Context) {
    try {
        val url = "https://github.com/".toUri()
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    } catch (_: Exception) {
        Toast.makeText(context, "Escoge un navegador", Toast.LENGTH_LONG).show()
    }
}

// Intent explícito: indica la clase exacta de la Activity que se va a abrir
fun openSecondActivity(context: Context) {
    val intent = Intent(context, SecondActivity::class.java)
    context.startActivity(intent)
}

// Intent implícito: Android busca una app instalada que pueda atenderlo
fun openExternalApp(context: Context, intent: Intent, appName: String) {
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No se encontró la app de $appName", Toast.LENGTH_LONG).show()
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
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val mensajeSnackbar = stringResource(id = R.string.texto_snackbar)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title)) },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Acción navegación */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_btn_desc),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { openWebScreen(context) }) {
                        Icon(
                            imageVector = Icons.Default.AddLink,
                            contentDescription = "Marcar como favorito",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { openSecondActivity(context) }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            tint = Color.White
                        )
                    }
                    DropDownMenu()
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.bottom_app_bar),
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Mostrar")},
                icon = { Icon(Icons.Default.Add, contentDescription = "") },
                onClick={
                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = mensajeSnackbar,
                                actionLabel = "Action",
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                // Action performed
                            }
                            SnackbarResult.Dismissed -> {
                                // Action dismissed
                            }
                        }
                    }
                }
            )
        }

    ){ innerPadding ->
        ContentView(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true, locale = "es-rMX")
@Composable
fun ContentView(modifier: Modifier = Modifier) {
    var counter by rememberSaveable { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red) // Manteniendo el fondo rojo de tu proyecto
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ima),
                contentDescription = stringResource(R.string.image_description),
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
                    text = counter.toString(),
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
                text = stringResource(R.string.materia),
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

@Composable
fun DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options_menu),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.profile)) },
                onClick = {
                    expanded = false
                    // Acción para Perfil
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.config)) },
                onClick = {
                    expanded = false
                    // Acción para Configuración
                },
                leadingIcon = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text(stringResource(R.string.calendar)) },
                onClick = {
                    expanded = false
                    val intent = Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_APP_CALENDAR)
                    openExternalApp(context, intent, "Calendario")
                },
                leadingIcon = {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.alarm)) },
                onClick = {
                    expanded = false
                    openExternalApp(context, Intent(AlarmClock.ACTION_SHOW_ALARMS), "Alarma")
                },
                leadingIcon = {
                    Icon(Icons.Default.Alarm, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.files)) },
                onClick = {
                    expanded = false
                    val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                    openExternalApp(context, intent, "Archivos")
                },
                leadingIcon = {
                    Icon(Icons.Default.Folder, contentDescription = null)
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text(stringResource(R.string.logout)) },
                onClick = {
                    expanded = false
                    // Acción para Cerrar sesión
                },
                leadingIcon = {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                }
            )
        }
    }
}