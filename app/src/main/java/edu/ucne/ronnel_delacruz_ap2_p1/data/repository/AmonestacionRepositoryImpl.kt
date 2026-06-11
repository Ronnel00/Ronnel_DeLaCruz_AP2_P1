package edu.ucne.ronnel_delacruz_ap2_p1.data.repository

import edu.ucne.ronnel_delacruz_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.ronnel_delacruz_ap2_p1.data.mapper.toDomain
import edu.ucne.ronnel_delacruz_ap2_p1.data.mapper.toEntity
import edu.ucne.ronnel_delacruz_ap2_p1.domain.model.Amonestacion
import edu.ucne.ronnel_delacruz_ap2_p1.domain.repository.AmonestacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AmonestacionRepositoryImpl @Inject constructor(
    private val amonestacionDao: AmonestacionDao
) : AmonestacionRepository {

    override fun observeAmonestaciones(): Flow<List<Amonestacion>> =
        amonestacionDao.observeAll().map { it.map { entity -> entity.toDomain() } }

    override suspend fun getAmonestacion(id: Int): Amonestacion? =
        amonestacionDao.getById(id)?.toDomain()

    override suspend fun upsert(amonestacion: Amonestacion): Int {
        val result = amonestacionDao.upsert(amonestacion.toEntity())
        return if (amonestacion.amonestacionId == 0) result.toInt() else amonestacion.amonestacionId
    }

    override suspend fun delete(id: Int) {
        amonestacionDao.deleteById(id)
    }

    override suspend fun getByNombres(nombres: String): List<Amonestacion> =
        amonestacionDao.getByNombres(nombres).map { it.toDomain() }
}