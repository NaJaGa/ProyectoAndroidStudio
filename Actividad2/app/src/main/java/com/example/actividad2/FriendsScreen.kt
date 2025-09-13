package com.example.actividad2

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.actividad2.ui.theme.Black
import com.example.actividad2.ui.theme.ColorFondo
import com.example.actividad2.ui.theme.VerdeTecmi
import com.example.actividad2.ui.theme.White
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController,loggedUser: User){

    var showMenu by remember { mutableStateOf(false) }
    var selectedCampusIndex by remember { mutableStateOf(0) }
    var nombre  by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var amigosEncontrados by remember { mutableStateOf<List<FriendsData>?>(null) }

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
                            text = { Text("Main Screen") },
                            onClick = {navController.navigate(AppDestinations.MAIN_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("Feed") },
                            onClick = { navController.navigate(AppDestinations.FEED_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("My Info") },
                            onClick = { navController.navigate(AppDestinations.USER_INFO_SCREEN) }
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
        ) {  }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "MY FRIENDS",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)

                ){
                    Dropdown(selectedIndex = selectedCampusIndex,
                        onSelectedIndexChange = { newIndex ->
                            selectedCampusIndex = newIndex})
                    Spacer(modifier = Modifier.height(8.dp))
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

                }

                IconButton(
                    onClick = {
                            amigosEncontrados = null

                                scope.launch {
                                    amigosEncontrados = findFriends(loggedUser,selectedCampusIndex,nombre,context)

                            }
                    },
                    modifier = Modifier.size(55.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = White,
                        containerColor = VerdeTecmi
                    )



                ) {
                    Icon(Icons.Default.Search,contentDescription = "Buscar", modifier = Modifier.size(40.dp))
                }
            }
            if (amigosEncontrados != null){
                mostrarListaDeAmigos(amigosEncontrados!!,loggedUser)
            }
        }


    }
}


suspend fun findFriends(loggedUser: User,campusID: Int,name: String,context: Context): List<FriendsData>?{
    var ncampusID = if (campusID == 0) 0 else campusID - 1
    val friendsRequest =
        FriendsRequest(
            FriendsFilter = FriendsRequestData(
                LoggedUserID = loggedUser.UserId,
                CampusID = ncampusID,
                Name = name
            )
        )
    return try {
        val response = RetrofitClient2.api.findFriends(friendsRequest)
        val executeResult = response.d.ExecuteResult
        val mensaje = response.d.Message
        if (executeResult == "OK"){
            Toast.makeText(context,  "Mostrando amigos coincidentes", Toast.LENGTH_SHORT).show()
            response.d.Friends
        }else{
            Toast.makeText(context, mensaje ?: "Error al conseguir los amigos", Toast.LENGTH_SHORT).show()
            null

        }

    }catch (e: Exception){
        e.printStackTrace()
        Toast.makeText(context, "Error al conseguir los amigos", Toast.LENGTH_SHORT).show()
        null
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mostrarListaDeAmigos(amigos: List<FriendsData>,loggedUser: User){
    val listadeIds = remember { mutableStateListOf<Int>() }
    var listaamigosGuardar by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(amigos) {
        listadeIds.clear()
        listadeIds.addAll(amigos.filter { it.IsFriend }.map { it.UserID })
        listaamigosGuardar = listadeIds.distinct().sorted().joinToString(",")
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        Text(text = "Amigos ${amigos.filter { it.IsFriend }.size} de ${amigos.size}")
        Spacer(modifier = Modifier.width(15.dp))
        Button(
            onClick ={
                scope.launch {
                    saveFriends(loggedUser,listaamigosGuardar, context)
                }
            },
            modifier = Modifier.padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VerdeTecmi
            )
        ){
            Text(text = "Guardar")
        }
    }

    Spacer(modifier = Modifier.height(5.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(10.dp),
    ) {
        item {
            amigos.forEach { amigo ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                        var checked by remember { mutableStateOf(amigo.IsFriend) }
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                if (checked) {
                                    if (!listadeIds.contains(amigo.UserID)) {
                                        listadeIds.add(amigo.UserID)
                                    }
                                } else {
                                    listadeIds.remove(amigo.UserID)
                                }
                                listaamigosGuardar = listadeIds.distinct().sorted().joinToString(",")

                                              },
                            modifier = Modifier
                                .padding( 15.dp)
                                .background(White)
                                .border(1.dp, Black)
                                .size(30.dp)
                            ,
                            colors = CheckboxDefaults.colors(
                                checkedColor = White,
                                uncheckedColor = White,
                                checkmarkColor = Black
                            )
                        )
                    Spacer(modifier = Modifier.width(15.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = amigo.CompleteName)
                        Text(text = "Student ID: ${amigo.StudentNumber}")
                        Text(text = "Campus: ${amigo.CampusName}")

                    }

                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}

suspend fun saveFriends(loggedUser: User,listadeAmigos: String, context: Context){

    val saveFriendsRequest = SaveFriendsRequest(
        FriendsList = SaveFriendsData(
            LoggedUserID = loggedUser.UserId,
            Friends = listadeAmigos
        )
    )
    val response = RetrofitClient2.api.saveFriends(saveFriendsRequest)
    val message = response.d.Message

    if (response.d.ExecuteResult == "OK"){
        Toast.makeText(context, "Amigos Guardados", Toast.LENGTH_SHORT).show()
    }else{
        println(message)
        Toast.makeText(context, message ?: "Error al guardar los amigos", Toast.LENGTH_SHORT).show()
    }
}
