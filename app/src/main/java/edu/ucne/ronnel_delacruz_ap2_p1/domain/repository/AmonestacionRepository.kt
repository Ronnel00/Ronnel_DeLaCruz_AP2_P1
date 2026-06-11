package edu.ucne.ronnel_delacruz_ap2_p1.domain.repository

import edu.ucne.ronnel_delacruz_ap2_p1.domain.model.Amonestacion
import kotlinx.coroutines.flow.Flow

interface AmonestacionRepository {
    fun observeAmonestaciones(): Flow<List<Amonestacion>>
    suspend fun getAmonestacion(id: Int): Amonestacion?
    suspend fun upsert(amonestacion: Amonestacion): Int
    suspend fun delete(id: Int)
    suspend fun getByNombres(nombres: String): List<Amonestacion>
}