package com.ombati.guidecane.module

import com.ombati.guidecane.data.repositories.RetroInstance
import com.ombati.guidecane.data.repositories.ThingsSpeakAPI
import com.ombati.guidecane.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetroInstance.getRetroInstance()
    }

    @Provides
    @Singleton
    fun provideThingsSpeakAPI(retrofit: Retrofit): ThingsSpeakAPI {
        return retrofit.create(ThingsSpeakAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: ThingsSpeakAPI): UserRepository {
        return UserRepository(api)
    }
}
