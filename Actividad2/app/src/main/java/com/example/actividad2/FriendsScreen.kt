package com.example.actividad2

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.actividad2.ui.theme.Black
import com.example.actividad2.ui.theme.ColorFondo
import com.example.actividad2.ui.theme.VerdeTecmi
import com.example.actividad2.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController,loggedUser: User){

    var showMenu by remember { mutableStateOf(false) }
    var selectedCampusIndex by remember { mutableStateOf(0) }
    var nombre  by remember { mutableStateOf("") }

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
                    onClick = {  },
                    modifier = Modifier.size(60.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = White,
                        containerColor = VerdeTecmi
                    )



                ) {
                    Icon(Icons.Default.Search,contentDescription = "Buscar", modifier = Modifier.size(40.dp))


                }


            }
        }

    }
}

