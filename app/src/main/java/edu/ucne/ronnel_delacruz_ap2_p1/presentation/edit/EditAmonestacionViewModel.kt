package edu.ucne.ronnel_delacruz_ap2_p1.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.ronnel_delacruz_ap2_p1.domain.model.Amonestacion
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.DeleteAmonestacionUseCase
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.GetAmonestacionUseCase
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.UpsertAmonestacionUseCase
import edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase.ValidateAmonestacionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAmonestacionViewModel @Inject constructor(
    private val getAmonestacion: GetAmonestacionUseCase,
    private val upsertAmonestacion: UpsertAmonestacionUseCase,
    private val deleteAmonestacion: DeleteAmonestacionUseCase,
    private val validate: ValidateAmonestacionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditAmonestacionUiState())
    val state: StateFlow<EditAmonestacionUiState> = _state.asStateFlow()

    fun onEvent(event: EditAmonestacionUiEvent) {
        when (event) {
            is EditAmonestacionUiEvent.Load -> load(event.id)
            is EditAmonestacionUiEvent.NombresChanged -> _state.update {
                it.copy(
                    nombres = event.value,
                    nombresError = if (event.value.isBlank()) "El nombre es requerido" else null
                )
            }
            is EditAmonestacionUiEvent.RazonChanged -> _state.update {
                it.copy(
                    razon = event.value,
                    razonError = if (event.value.isBlank()) "La razón es requerida" else null
                )
            }
            is EditAmonestacionUiEvent.MontoChanged -> {
                val d = event.value.toDoubleOrNull()
                _state.update {
                    it.copy(
                        monto = d,
                        montoError = when {
                            event.value.isBlank() -> "El monto es requerido"
                            d == null -> "Ingrese un número válido"
                            d <= 0 -> "El monto debe ser mayor a 0"
                            else -> null
                        }
                    )
                }
            }
            EditAmonestacionUiEvent.Save -> save()
            EditAmonestacionUiEvent.Delete -> delete()
        }
    }

    private fun load(id: Int?) {
        if (id == null || id == 0) {
            _state.value = EditAmonestacionUiState(isNew = true)
            return
        }
        viewModelScope.launch {
            getAmonestacion(id)?.let { a ->
                _state.update {
                    it.copy(
                        isNew = false,
                        amonestacionId = a.amonestacionId,
                        nombres = a.nombres,
                        razon = a.razon,
                        monto = a.monto
                    )
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            val v = validate(
                nombres = _state.value.nombres,
                razon = _state.value.razon,
                monto = _state.value.monto,
                currentAmonestacionId = _state.value.amonestacionId
            )
            if (!v.isValid) {
                _state.update {
                    it.copy(
                        nombresError = v.nombresError,
                        razonError = v.razonError,
                        montoError = v.montoError
                    )
                }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }
            try {
                upsertAmonestacion(
                    Amonestacion(
                        amonestacionId = _state.value.amonestacionId ?: 0,
                        nombres = _state.value.nombres.trim(),
                        razon = _state.value.razon.trim(),
                        monto = _state.value.monto ?: 0.0
                    )
                )
                _state.update { it.copy(isSaving = false, saved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, nombresError = e.message) }
            }
        }
    }

    private fun delete() {
        val id = _state.value.amonestacionId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteAmonestacion(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, nombresError = e.message) }
            }
        }
    }
}