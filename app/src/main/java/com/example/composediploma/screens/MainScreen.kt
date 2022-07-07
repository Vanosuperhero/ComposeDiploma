package com.example.composediploma.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composediploma.R
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.mqtt.MySqlViewModel
import com.example.composediploma.navigation.BottomNavGraph
import com.example.composediploma.navigation.Screen
import com.example.composediploma.ui.theme.Accent_Blue
import com.example.composediploma.ui.theme.Accent_Blue80
import com.example.composediploma.ui.theme.Accent_BlueDark
import com.example.composediploma.ui.theme.New_Blue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MainScreen(context: Context, viewModel: MQTTViewModel,sqlViewModel: MySqlViewModel){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route?:Screen.Monitoring.route
    val screens = listOf(
        Screen.Monitoring,
        Screen.Statistics,
        Screen.Weather
    )
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        topBar = {
            TopAppBar(
                title = { Text(screens.first { it.route == currentRoute }.title) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = null) } },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description") } },
                elevation = 8.dp
            )
        },
        drawerContent = {ModalDrawerMain()},
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(navController, currentDestination, screens) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(context = context, navController = navController, viewModel,sqlViewModel)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController,currentDestination: NavDestination?, screens: List<Screen>){
    BottomNavigation(
    ) {
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
              imageVector = screen.icon,
              contentDescription = "Navigation Icon"
          )
      },
      selected = currentDestination?.hierarchy?.any{
          it.route == screen.route
      } == true,
      onClick = {
          navController.navigate(screen.route){
              popUpTo(navController.graph.findStartDestination().id)
              launchSingleTop = true
          }
      }
  )
}

@Composable
fun ModalDrawerMain() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
            Text(text = "Дизайн и код", fontWeight = FontWeight.Medium, color = Color.Black, fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Бескоровайный Иван", fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Данные и сети", fontWeight = FontWeight.Medium, color = Color.Black, fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Долматов Владислав", fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
//            Text(text = "MySQL", fontWeight = FontWeight.Medium, color = Color.Black, fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
//            Text(text = "Нарынбаев Алишер", fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)) {
            Image(painter = painterResource(R.drawable.igvie), contentDescription = "Igvie logo", modifier = Modifier.align(Alignment.CenterHorizontally))
//            Text(text = "МЭИ ИГВИЭ", fontWeight = FontWeight.Normal, color = Color.Black, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "2022", fontWeight = FontWeight.Normal, color = Color.Black, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

