package edu.ucne.ronnel_delacruz_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object List : Screen()
    @Serializable data object Edit : Screen()
}