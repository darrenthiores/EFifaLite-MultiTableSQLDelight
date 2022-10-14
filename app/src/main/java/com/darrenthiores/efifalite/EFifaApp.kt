package com.darrenthiores.efifalite

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.darrenthiores.efifalite.component.EFifaTabRow
import com.darrenthiores.efifalite.screen.*
import com.darrenthiores.efifalite.viewModel.AppViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun EFifaApp(
    viewModel: AppViewModel
) {
    val allScreens = EFifaScreen.values().toList()
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = EFifaScreen.fromRoute(
        backStackEntry.value?.destination?.route
    )
    var showLandingScreen by remember {
        mutableStateOf(true)
    }

    if(showLandingScreen) {
        LandingScreen {
            showLandingScreen = false
        }
    } else {
        Scaffold(
            topBar = {
                val coin = viewModel.getCoin().collectAsState(initial = 0)
                EFifaTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                    currentScreen = currentScreen,
                    coin = coin.value
                )
            }
        ) { innerPadding ->
            RallyNavHost(
                navController = navController,
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun RallyNavHost(
    navController: NavHostController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = EFifaScreen.Overview.name,
        modifier = modifier
    ) {
        composable(EFifaScreen.Overview.name) {
            val showDialog = viewModel.showDialog.collectAsState()
            OverviewScreen(
                onCarouselClicked = { navController.navigate(EFifaScreen.Draw.name) },
                onStartingCardClicked = { navController.navigate(EFifaScreen.LineUp.name) },
                showDialog = showDialog.value,
                onDismiss = { viewModel.closeDialog() }
            )
        }
        composable(EFifaScreen.LineUp.name) {
            LineUpScreen(
                onChange = {
                    navigateToUpdateStarting(navController, it.id)
                }
            )
        }
        composable(EFifaScreen.Draw.name) {
            DrawScreen()
        }
        val lineUp = EFifaScreen.LineUp.name
        composable(
            route = "$lineUp/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getInt("id")
            ChangeScreen(
                id = id ?: 0
            ) {
                navController.navigate(EFifaScreen.LineUp.name) {
                    popUpTo(EFifaScreen.LineUp.name) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

private fun navigateToUpdateStarting(
    navController: NavController,
    id: Int
) {
    navController.navigate("${EFifaScreen.LineUp.name}/$id")
}