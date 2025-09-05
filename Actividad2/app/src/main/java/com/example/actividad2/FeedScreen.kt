package com.example.actividad2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actividad2.ui.theme.ColorFondo
import com.example.actividad2.ui.theme.VerdeTecmi
import com.example.actividad2.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController){

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
                            text = { Text("MainScreen") },
                            onClick = {navController.navigate(AppDestinations.MAIN_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("FriendsScreen") },
                            onClick = { navController.navigate(AppDestinations.FRIENDS_SCREEN)}
                        )
                        DropdownMenuItem(
                            text = { Text("My Info") },
                            onClick = { navController.navigate(AppDestinations.USER_INFO_SCREEN)}
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
        Text(modifier = Modifier.padding(innerPadding), text = "Feed")

    }
}