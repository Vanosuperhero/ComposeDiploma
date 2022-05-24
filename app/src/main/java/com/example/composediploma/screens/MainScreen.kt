package com.example.composediploma.screens

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.navigation.BottomNavGraph
import com.example.composediploma.navigation.Screen
import com.example.composediploma.ui.theme.Accent_Blue80
import com.example.composediploma.ui.theme.Accent_BlueDark
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainScreen(context: Context, viewModel: MQTTViewModel){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route?:Screen.Monitoring.route
    val screens = listOf(
        Screen.Monitoring,
        Screen.Statistics,
        Screen.Weather
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screens.first { it.route == currentRoute }.title) },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                },
                elevation = 8.dp
            )
    },
        bottomBar = { BottomBar(navController, currentDestination, screens) }
    ) {
        BottomNavGraph(context = context, navController = navController, viewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController,currentDestination: NavDestination?, screens: List<Screen>){
    BottomNavigation() {
        screens.forEach{ screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Accent_BlueDark,
            darkIcons = useDarkIcons
        )
    }
  BottomNavigationItem(
      label = {
          Text(text = screen.title)
      },
      icon = {
          Icon(
//              painter = painterResource(screen.icon),
              imageVector = screen.icon,
              contentDescription = "Navigation Icon"
          )
      },
      selected = currentDestination?.hierarchy?.any{
          it.route == screen.route
      } == true,
//      unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
      onClick = {
          navController.navigate(screen.route){
              popUpTo(navController.graph.findStartDestination().id)
              launchSingleTop = true
          }
      }
  )
}

