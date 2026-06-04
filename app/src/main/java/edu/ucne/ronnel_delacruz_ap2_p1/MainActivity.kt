package edu.ucne.ronnel_delacruz_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.ronnel_delacruz_ap2_p1.presentation.navigation.AppNavHost
import edu.ucne.ronnel_delacruz_ap2_p1.ui.theme.Ronnel_DeLaCruz_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ronnel_DeLaCruz_AP2_P1Theme {
                val navController = rememberNavController()
                AppNavHost(navHostController = navController)
            }
        }
    }
}