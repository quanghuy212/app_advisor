package com.example.appadvisor.di

import android.content.Context
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.network.AdvisorApiService
import com.example.appadvisor.data.network.AuthApiService
import com.example.appadvisor.data.network.MeetingApiService
import com.example.appadvisor.data.network.StudentApiService
import com.example.appadvisor.data.network.TaskApiService
import com.example.appadvisor.data.network.UserApiService
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.data.repository.MeetingRepository
import com.example.appadvisor.data.repository.StudentRepository
import com.example.appadvisor.data.repository.AuthRepository
import com.example.appadvisor.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.Signed
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

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

    // Provides Meeting ApiService
    @Provides
    @Singleton
    fun provideMeetingApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): MeetingApiService {
        return retrofitWithAuth.create(MeetingApiService::class.java)
    }

    // Provides Advisor ApiService
    @Provides
    @Singleton
    fun provideAdvisorApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): AdvisorApiService {
        return retrofitWithAuth.create(AdvisorApiService::class.java)
    }

    // Provides Student ApiService
    @Provides
    @Singleton
    fun provideStudentApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): StudentApiService {
        return retrofitWithAuth.create(StudentApiService::class.java)
    }

    // Provides User ApiService
    @Provides
    @Singleton
    fun provideUserApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): UserApiService {
        return retrofitWithAuth.create(UserApiService::class.java)
    }

    // Provides User Repository
    @Provides
    @Singleton
    fun provideUserRepository(userApiService: UserApiService): UserRepository {
        return UserRepository(userApiService)
    }

    // Provides Student Repository
    @Provides
    @Singleton
    fun provideStudentRepository(studentApiService: StudentApiService): StudentRepository {
        return StudentRepository(studentApiService)
    }

    // Provides Advisor Repository
    @Provides
    @Singleton
    fun provideAdvisorRepository(advisorApiService: AdvisorApiService): AdvisorRepository {
        return AdvisorRepository(advisorApiService)
    }

    // Provides Meeting Repository
    @Provides
    @Singleton
    fun provideMeetingRepository(meetingApiService: MeetingApiService) : MeetingRepository {
        return MeetingRepository(meetingApiService)
    }


    // Provide User Repository
    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, tokenManager: TokenManager): AuthRepository {
        return AuthRepository(authApiService, tokenManager)
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
