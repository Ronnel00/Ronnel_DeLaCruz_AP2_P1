package edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase

import edu.ucne.ronnel_delacruz_ap2_p1.domain.model.Amonestacion
import edu.ucne.ronnel_delacruz_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class UpsertAmonestacionUseCase @Inject constructor(
    private val repository: AmonestacionRepository
) {
    suspend operator fun invoke(amonestacion: Amonestacion): Result<Int> {
        return try {
            val id = repository.upsert(amonestacion)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}