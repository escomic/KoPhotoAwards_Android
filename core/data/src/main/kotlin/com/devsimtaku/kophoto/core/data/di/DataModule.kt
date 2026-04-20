package com.devsimtaku.kophoto.core.data.di

import com.devsimtaku.kophoto.core.data.repository.KoPhotoRepositoryImpl
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindKoPhotoRepository(
        koPhotoRepositoryImpl: KoPhotoRepositoryImpl
    ): KoPhotoRepository

}
