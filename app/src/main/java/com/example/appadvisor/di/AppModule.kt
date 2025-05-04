package com.example.appadvisor.di

import android.content.Context
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.network.AuthApiService
import com.example.appadvisor.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        // Run AVD url = "http://10.0.2.2:8080/"
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provide User ApiService
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    // Provide User Repository
    @Provides
    @Singleton
    fun provideUserRepository(authApiService: AuthApiService, tokenManager: TokenManager): UserRepository {
        return UserRepository(authApiService, tokenManager)
    }


    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

}
