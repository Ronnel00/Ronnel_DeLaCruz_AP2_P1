package edu.ucne.ronnel_delacruz_ap2_p1.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.DeleteAmonestacionUseCase
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.ObserveAmonestacionesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAmonestacionViewModel @Inject constructor(
    private val observeAmonestaciones: ObserveAmonestacionesUseCase,
    private val deleteAmonestacion: DeleteAmonestacionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListAmonestacionUiState(isLoading = true))
    val state: StateFlow<ListAmonestacionUiState> = _state.asStateFlow()

    init { load() }

    fun onEvent(event: ListAmonestacionUiEvent) {
        when (event) {
            ListAmonestacionUiEvent.Load -> load()
            is ListAmonestacionUiEvent.Delete -> delete(event.id)
        }
    }

    private fun load() {
        viewModelScope.launch {
            observeAmonestaciones().collectLatest { list ->
                _state.update { it.copy(isLoading = false, amonestaciones = list) }
            }
        }
    }

    private fun delete(id: Int) {
        viewModelScope.launch {
            try {
                deleteAmonestacion(id)
                _state.update { it.copy(message = "Amonestación eliminada") }
            } catch (e: Exception) {
                _state.update { it.copy(message = "Error: ${e.message}") }
            }
        }
    }
}