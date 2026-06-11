package edu.ucne.ronnel_delacruz_ap2_p1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.ronnel_delacruz_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.ronnel_delacruz_ap2_p1.data.local.entities.AmonestacionEntity

@Database(
    entities = [AmonestacionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AmonestacionDB : RoomDatabase() {
    abstract fun amonestacionDao(): AmonestacionDao
}