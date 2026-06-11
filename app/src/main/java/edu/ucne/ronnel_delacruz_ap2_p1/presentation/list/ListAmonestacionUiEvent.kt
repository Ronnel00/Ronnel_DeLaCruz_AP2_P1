package edu.ucne.ronnel_delacruz_ap2_p1.presentation.list

sealed interface ListAmonestacionUiEvent {
    data object Load : ListAmonestacionUiEvent
    data class Delete(val id: Int) : ListAmonestacionUiEvent
}