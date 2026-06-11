package edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase

import edu.ucne.ronnel_delacruz_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class ValidateAmonestacionUseCase @Inject constructor(
    private val repository: AmonestacionRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombresError: String? = null,
        val razonError: String? = null,
        val montoError: String? = null
    )

    suspend operator fun invoke(
        nombres: String,
        razon: String,
        monto: Double?,
        currentAmonestacionId: Int? = null
    ): ValidationResult {

        val nombresError = when {
            nombres.isBlank() -> "El nombre es requerido"
            else -> null
        }

        val razonError = when {
            razon.isBlank() -> "La razón es requerida"
            else -> null
        }

        val montoError = when {
            monto == null -> "El monto es requerido"
            monto <= 0 -> "El monto debe ser mayor a 0"
            else -> null
        }

        return ValidationResult(
            isValid = nombresError == null && razonError == null && montoError == null,
            nombresError = nombresError,
            razonError = razonError,
            montoError = montoError
        )
    }
}