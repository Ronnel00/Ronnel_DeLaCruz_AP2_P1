package edu.ucne.ronnel_delacruz_ap2_p1.data.local.dao

import androidx.room.*
import edu.ucne.ronnel_delacruz_ap2_p1.data.local.entities.AmonestacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AmonestacionDao {

    @Upsert
    suspend fun upsert(amonestacion: AmonestacionEntity): Long

    @Query("SELECT * FROM amonestaciones")
    fun observeAll(): Flow<List<AmonestacionEntity>>

    @Query("SELECT * FROM amonestaciones WHERE amonestacionId = :id")
    suspend fun getById(id: Int): AmonestacionEntity?

    @Query("DELETE FROM amonestaciones WHERE amonestacionId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM amonestaciones WHERE nombres = :nombres")
    suspend fun getByNombres(nombres: String): List<AmonestacionEntity>
}