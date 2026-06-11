package edu.ucne.ronnel_delacruz_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object AmonestacionList : Screen()
    @Serializable data class Amonestacion(val amonestacionId: Int) : Screen()
}