package com.example.appadvisor.di

import android.content.Context
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.network.AuthApiService
import com.example.appadvisor.data.network.TaskApiService
import com.example.appadvisor.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

/*    // Provide Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        // Run AVD url = "http://10.0.2.2:8080/"
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }*/

    // Provide Auth ApiService
    @Provides
    @Singleton
    fun provideAuthApiService(@Named("no-auth-retrofit") retrofitWithoutAuth: Retrofit): AuthApiService {
        return retrofitWithoutAuth.create(AuthApiService::class.java)
    }

    // Provides Task ApiService
    @Provides
    @Singleton
    fun provideTaskApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): TaskApiService {
        return retrofitWithAuth.create(TaskApiService::class.java)
    }

    // Provide User Repository
    @Provides
    @Singleton
    fun provideUserRepository(authApiService: AuthApiService, tokenManager: TokenManager): UserRepository {
        return UserRepository(authApiService, tokenManager)
    }

    // Provide Token manager
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    // Provides AuthInterceptor
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    // ProvideOkHttpclientWithAuth
    @Provides
    @Singleton
    @Named("auth")
    fun provideOkHttpClientWithAuth(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    // Provides OkHttpClient No Auth
    @Provides
    @Singleton
    @Named("no-auth")
    fun provideOkHttpClientWithoutAuth(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    // Provides Retrofit With Auth
    @Provides
    @Singleton
    @Named("auth-retrofit")
    fun provideRetrofitWithAuth(@Named("auth") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides Retrofit No Auth
    @Provides
    @Singleton
    @Named("no-auth-retrofit")
    fun provideRetrofitWithoutAuth(@Named("no-auth") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
