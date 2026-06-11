package edu.ucne.ronnel_delacruz_ap2_p1.presentation.edit

sealed interface EditAmonestacionUiEvent {
    data class Load(val id: Int?) : EditAmonestacionUiEvent
    data class NombresChanged(val value: String) : EditAmonestacionUiEvent
    data class RazonChanged(val value: String) : EditAmonestacionUiEvent
    data class MontoChanged(val value: String) : EditAmonestacionUiEvent
    data object Save : EditAmonestacionUiEvent
    data object Delete : EditAmonestacionUiEvent
}