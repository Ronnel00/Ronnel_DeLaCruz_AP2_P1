package edu.ucne.ronnel_delacruz_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.ronnel_delacruz_ap2_p1.presentation.edit.EditScreen
import edu.ucne.ronnel_delacruz_ap2_p1.presentation.list.ListScreen

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List
    ) {
        composable<Screen.List> {
            ListScreen(
                goToEdit = { navHostController.navigate(Screen.Edit) }
            )
        }
        composable<Screen.Edit> {
            EditScreen(
                onNavigateBack = { navHostController.navigateUp() }
            )
        }
    }
}