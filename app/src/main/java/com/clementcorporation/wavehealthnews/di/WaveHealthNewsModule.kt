package com.clementcorporation.wavehealthnews.di

import com.clementcorporation.wavehealthnews.data.remote.WaveHealthNewsApi
import com.clementcorporation.wavehealthnews.data.remote.WaveHealthNewsRepositoryImpl
import com.clementcorporation.wavehealthnews.domain.WaveHealthNewsRepository
import com.clementcorporation.wavehealthnews.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaveHealthNewsModule {

    @Provides
    @Singleton
    fun providePagingRepository(api: WaveHealthNewsApi): WaveHealthNewsRepository
            = WaveHealthNewsRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesWaveHealthNewsApi(): WaveHealthNewsApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WaveHealthNewsApi::class.java)
}