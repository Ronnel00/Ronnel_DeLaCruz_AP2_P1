package edu.ucne.ronnel_delacruz_ap2_p1.presentation.edit

data class EditAmonestacionUiState(
    val amonestacionId: Int? = null,
    val nombres: String = "",
    val razon: String = "",
    val monto: Double? = null,
    val nombresError: String? = null,
    val razonError: String? = null,
    val montoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)