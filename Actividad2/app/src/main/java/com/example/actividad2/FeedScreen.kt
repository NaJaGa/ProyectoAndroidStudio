package com.example.actividad2

import android.content.Context
import android.graphics.Color
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun FeedScreen(navController: NavController, currentUser: User?) {

    var showMenu by remember { mutableStateOf(false) }
    var posts by remember { mutableStateOf<List<PostData>?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(false) }


    LaunchedEffect(currentUser) {
        posts = getPosts(currentUser!!.UserId, context)
    }

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
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Opciones",
                            modifier = Modifier.size(40.dp)
                        )


                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.width(150.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("MainScreen") },
                            onClick = { navController.navigate(AppDestinations.MAIN_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("FriendsScreen") },
                            onClick = { navController.navigate(AppDestinations.FRIENDS_SCREEN) }
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { visible = !visible},
                containerColor = VerdeTecmi,
                contentColor = White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Usuario")

            }
        }


    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = ColorFondo)
                ,

            ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorFondo),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Feed",
                            fontSize = 30.sp,
                            color = Black
                        )

                        TextButton(
                            onClick = {
                                scope.launch {
                                    posts = getPosts(currentUser!!.UserId, context)

                                }

                            },

                        ) {
                            Text(text = "Actualizar")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    posts?.forEach { post ->
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                                .fillMaxWidth()
                                .background(White),
                        ){
                            Text(
                                text = post.CompleteName,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,

                            )
                            Text(
                                text = post.Message,
                                fontSize = 15.sp,
                                color = Black
                            )
                            Text(
                                text = post.TimeStamp,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black,
                                modifier = Modifier.align(Alignment.End)

                            )
                        }
                        Spacer(Modifier.height(15.dp))
                    }
                }


            }

        }
    }



}



suspend fun getPosts(LoggedUser: Int, context: Context): List<PostData>? {

    val request = ViewFeedRequest(
        PostFilter = ViewFeedData(
            LoggedUserID = LoggedUser,
        )
    )

    val response = RetrofitClient3.api.viewFeed(request)
    val message = response.d.Message
    println(response.d.ExecuteResult)


    return try {
        if (response.d.ExecuteResult == "OK") {
            Toast.makeText(context, "Mostrando Posts", Toast.LENGTH_SHORT).show()
             response.d.Posts
        } else {
            Toast.makeText(context,  message?: "Error al mostrar los Posts", Toast.LENGTH_SHORT)
                .show()
            println(message)
            return null
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error con la Conexion", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
        return null
    }


}
