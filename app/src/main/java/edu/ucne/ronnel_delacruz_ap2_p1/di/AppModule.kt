package edu.ucne.ronnel_delacruz_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.ronnel_delacruz_ap2_p1.data.database.AmonestacionDB
import edu.ucne.ronnel_delacruz_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.ronnel_delacruz_ap2_p1.data.repository.AmonestacionRepositoryImpl
import edu.ucne.ronnel_delacruz_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAmonestacionDB(@ApplicationContext context: Context): AmonestacionDB =
        Room.databaseBuilder(
            context,
            AmonestacionDB::class.java,
            "AmonestacionDB"
        ).fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideAmonestacionDao(db: AmonestacionDB): AmonestacionDao =
        db.amonestacionDao()

    @Provides
    @Singleton
    fun provideAmonestacionRepositoryImpl(dao: AmonestacionDao): AmonestacionRepositoryImpl =
        AmonestacionRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideAmonestacionRepository(impl: AmonestacionRepositoryImpl): AmonestacionRepository =
        impl
}