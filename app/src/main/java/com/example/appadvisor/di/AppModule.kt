package com.example.appadvisor.di

import com.example.appadvisor.data.network.AdvisorApiService
import com.example.appadvisor.data.network.StudentApiService
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.data.repository.StudentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    // Provide Student ApiService
    @Provides
    @Singleton
    fun provideStudentApiService(retrofit: Retrofit): StudentApiService {
        return retrofit.create(StudentApiService::class.java)
    }

    // Provide Student Repository
    @Provides
    @Singleton
    fun provideStudentRepository(studentApiService: StudentApiService): StudentRepository {
        return StudentRepository(studentApiService)
    }
    
    // Provide Advisor Repository
    @Provides
    @Singleton
    fun provideAdvisorRepository(advisorApiService: AdvisorApiService): AdvisorRepository {
        return AdvisorRepository(advisorApiService)
    }

    // Provide Advisor ApiService
    @Provides
    @Singleton
    fun provideAdvisorApiService(retrofit: Retrofit): AdvisorApiService {
        return retrofit.create(AdvisorApiService::class.java)
    }
}
