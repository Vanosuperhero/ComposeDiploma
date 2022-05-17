package com.example.composediploma.screens

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composediploma.navigation.BottomNavGraph
import com.example.composediploma.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainScreen(context: Context){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
        TopAppBar {}
    },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomNavGraph(context = context, navController = navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        Screen.Monitoring,
        Screen.Statistics,
        Screen.Weather
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
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
//    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = MaterialTheme.colors.isLight
//    SideEffect {
//        systemUiController.setStatusBarColor(
//            color = Color.Transparent,
//            darkIcons = useDarkIcons
//        )
//    }
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
