package com.example.actividad2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.actividad2.ui.theme.Actividad2Theme
import com.example.actividad2.ui.theme.Boxfondo
import com.example.actividad2.ui.theme.ColorFondo
import com.example.actividad2.ui.theme.VerdeTecmi
import com.example.actividad2.ui.theme.White
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch
import androidx.compose.material3.Surface
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.actividad2.FeedScreen
import com.example.actividad2.MainScreen
import com.example.actividad2.UserInfoScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentUser by remember { mutableStateOf<User?>(null) }

            Actividad2Theme {

                AppNavigator(
                    currentUser = currentUser,
                    onLogin = { user ->
                        currentUser = user
                    },
                    onLogout = {
                        currentUser = null
                    }
                )

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit
){
    var options by remember { mutableStateOf(listOf("Selecciona un Campus")) }

    var isExpanded by remember { mutableStateOf(false) }
    val selectedOption = options.getOrElse(selectedIndex) { options.first() }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.api.getCampuses()
            options = listOf("Elija una opcion") + response.Campuses.map { it.CampusName }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange ={isExpanded = !isExpanded}
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White
            )

            )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }

        ){
            options.forEachIndexed{ index, item ->
                DropdownMenuItem(
                    text = { Text(text = item)},
                    onClick = {
                        onSelectedIndexChange(index)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }

    }

}
@Composable
fun Mibox(
    visible: Boolean,
    onCancel: () -> Unit
){
    var ncorreo  by remember { mutableStateOf("") }
    var nombre  by remember { mutableStateOf("") }
    var apellido  by remember { mutableStateOf("") }
    var matricula  by remember { mutableStateOf("")  }
    var ncontrasena  by remember { mutableStateOf("") }
    var selectedCampusIndex by remember { mutableIntStateOf(0) }
    val esNcorreo = Patterns.EMAIL_ADDRESS.matcher(ncorreo).matches()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (visible){
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Boxfondo),
            contentAlignment = Alignment.Center


        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Crear Nueva Cuenta",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White
                )

                Spacer(modifier = Modifier.height(30.dp))
                Dropdown(selectedIndex = selectedCampusIndex,
                    onSelectedIndexChange = { newIndex ->
                        selectedCampusIndex = newIndex} )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = ncorreo,
                    onValueChange = {nuevoTexto ->
                        ncorreo = nuevoTexto},
                    label = { Text("Correo Electronico") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )
                if(!esNcorreo && ncorreo.isNotEmpty()){
                    Text(
                        text = "Correo Invalido",
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nuevoTexto ->
                        nombre = nuevoTexto},
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = apellido,
                    onValueChange = {nuevoTexto ->
                        apellido = nuevoTexto},
                    label = { Text("Apellido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = matricula,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            matricula = it
                        }
                    },
                    label = { Text("Matrícula") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number)
                )


                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = ncontrasena,
                    onValueChange = {nuevoTexto ->
                        ncontrasena = nuevoTexto},
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    TextButton(
                        onClick = { onCancel() }

                    ) {
                        Text(
                            text = "Cancelar",
                            color = White
                        )

                    }
                    TextButton(
                        onClick = {
                            if(esNcorreo && selectedCampusIndex != 0 && nombre.isNotEmpty() && apellido.isNotEmpty() && matricula.isNotEmpty() && ncontrasena.isNotEmpty() && ncorreo.isNotEmpty()){
                                scope.launch{
                                    try {
                                        signup(nombre,apellido,matricula,ncorreo,ncontrasena,selectedCampusIndex,context)
                                        onCancel()
                                    }catch (e: Exception){
                                        e.printStackTrace()
                                        Toast.makeText(
                                            context,
                                            "Error Con la Conexion",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                            }else{
                                Toast.makeText(
                                    context,
                                    "Por favor llene todos los campos",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    ){
                        Text(
                            text = "Crear",
                            color = White
                        )
                    }
                }
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoginScreen(onloginSuccess: (User) -> Unit) {


    var visible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var correo  by remember { mutableStateOf("") }
    val escorreo = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    var contrasena  by remember { mutableStateOf("") }



    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = VerdeTecmi,
                    titleContentColor = White
                ),
                title = {
                    Text("Tec Milenio")
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { visible = true },
                containerColor = VerdeTecmi,
                contentColor = White
            ) {
                Icon(Icons.Default.Add,contentDescription = "Crear Usuario")

            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = ColorFondo,
                modifier = Modifier.height(10.dp)
            ) { }
        }


    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center

        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorFondo)
                    .padding(16.dp)


            ) {
                item() {

                    val image = painterResource( R.drawable.tecmilogo)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(220.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hawk Connect",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(30.dp))


                    OutlinedTextField(
                        value = correo,
                        onValueChange = {nuevoTexto ->
                            correo = nuevoTexto},
                        label = { Text("Correo Electronico") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = White,
                            unfocusedContainerColor = White
                        )

                    )
                    if(!escorreo && correo.isNotEmpty()){
                        Text(
                            text = "Correo Invalido",
                            color = androidx.compose.ui.graphics.Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = contrasena ,
                        onValueChange = {nuevoTexto ->
                            contrasena = nuevoTexto},
                        label = { Text("Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = White,
                            unfocusedContainerColor = White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if(escorreo){
                                scope.launch{
                                        val user = loginAndGetUser(correo,contrasena,context)
                                        if (user != null) {
                                            onloginSuccess(user)
                                        }
                                }
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeTecmi,
                        )
                    ){
                        Text("Entrar")
                    }

                }
            }

            Mibox(
                visible = visible,
                onCancel = { visible = false },
            )
        }


    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(User: User, onLogout: () -> Unit,navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = VerdeTecmi,
                    titleContentColor = White
                ),
                title = {
                    Text("Tec Milenio")
                },
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = White
                        ),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.MoreVert,contentDescription = "Opciones", modifier = Modifier.size(40.dp))


                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.width(150.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("View Feed") },
                            onClick = { navController.navigate(AppDestinations.FEED_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("My Friends") },
                            onClick = { navController.navigate(AppDestinations.FRIENDS_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("My Info") },
                            onClick = { navController.navigate(AppDestinations.USER_INFO_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesion") },
                            onClick = { onLogout() }
                        )
                    }
                }
            )
        }, bottomBar = {
            BottomAppBar(
                containerColor = ColorFondo,
                modifier = Modifier.height(10.dp)
            ) { }
        }


    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = ColorFondo
        ){}
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Bienvenido",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${User.Name} ${User.LastName}",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

suspend fun loginAndGetUser(
    correo: String,
    contrasena: String,
    context: Context,

):User?{
    val request = LoginRequest(
        LoginData = LoginData(
            Email = correo,
            Password = contrasena
        )
    )
    return try {
        val response = RetrofitClient2.api.login(request)
        val userLoggedList = response.d.UserLogged
        val user = userLoggedList?.firstOrNull()

        if (user != null) {
            Toast.makeText(context, "Bienvenido ${user.Name} ${user.LastName} ${user.CampusName}", Toast.LENGTH_SHORT).show()
            user

        } else {
            Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            null
        }
    }catch (e: Exception){
        e.printStackTrace()
        Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
        null
    }

}

suspend fun signup(nombre: String, apellido: String, matricula: String, correo: String, contrasena: String, selectedCampusIndex: Int, context: Context){
    val matricula = matricula.toInt()
    val selectedCampusIndex = selectedCampusIndex - 1

    val NewUserRequest = NewUserRequest(
        NewUser = NewUser(
            Name = nombre,
            LastName = apellido,
            Email = correo,
            Password = contrasena,
            StudentNumber = matricula,
            CampusID = selectedCampusIndex
        )
    )
    val response = RetrofitClient3.api.signup(NewUserRequest)
    val ExecuteResult = response.d.ExecuteResult
    val mensaje = response.d.Message

    if (ExecuteResult == "OK") {
        Toast.makeText(context, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
    }
    else{
        Toast.makeText(context, mensaje ?: "Error al crear el usuario", Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun AppNavigator(currentUser: User?, onLogin: (User) -> Unit, onLogout: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) AppDestinations.MAIN_SCREEN else AppDestinations.LOGIN_SCREEN
    ) {
        // Login
        composable(AppDestinations.LOGIN_SCREEN) {
            LoginScreen { user ->
                onLogin(user)
                navController.navigate(AppDestinations.MAIN_SCREEN) {
                    popUpTo(AppDestinations.LOGIN_SCREEN) { inclusive = true }
                }
            }
        }

        // Main
        composable(AppDestinations.MAIN_SCREEN) {
            currentUser?.let{user ->
                MainScreen(
                    User = user,
                    onLogout = {
                        onLogout()
                        navController.navigate(AppDestinations.LOGIN_SCREEN) {
                            popUpTo(AppDestinations.MAIN_SCREEN) { inclusive = true }
                        }
                    },navController = navController

                )
            }
        }

        // Otras pantallas
        composable(AppDestinations.USER_INFO_SCREEN) {
            UserInfoScreen(navController)
        }
        composable(AppDestinations.FEED_SCREEN) {
            FeedScreen(navController)
        }
        composable(AppDestinations.FRIENDS_SCREEN) {
            FriendsScreen(navController)
        }
    }
}
