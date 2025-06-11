package com.example.appadvisor.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.Preferences
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.local.WebSocketManager
import com.example.appadvisor.data.network.AdvisorApiService
import com.example.appadvisor.data.network.AuthApiService
import com.example.appadvisor.data.network.ConversationApiService
import com.example.appadvisor.data.network.DocumentApiService
import com.example.appadvisor.data.network.InfoApiService
import com.example.appadvisor.data.network.MeetingApiService
import com.example.appadvisor.data.network.SearchApiService
import com.example.appadvisor.data.network.StudentApiService
import com.example.appadvisor.data.network.TaskApiService
import com.example.appadvisor.data.network.UserApiService
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.data.repository.AuthRepository
import com.example.appadvisor.data.repository.ConversationRepository
import com.example.appadvisor.data.repository.DocumentRepository
import com.example.appadvisor.data.repository.InfoRepository
import com.example.appadvisor.data.repository.MeetingRepository
import com.example.appadvisor.data.repository.SearchRepository
import com.example.appadvisor.data.repository.StudentRepository
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

    // Provides Info ApiService
    @Provides
    @Singleton
    fun provideInfoApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): InfoApiService {
        return retrofitWithAuth.create(InfoApiService::class.java)
    }

    // Provides Document ApiService
    @Provides
    @Singleton
    fun provideDocumentApiService(@Named("no-auth-retrofit") retrofitWithoutAuth: Retrofit): DocumentApiService {
        return retrofitWithoutAuth.create(DocumentApiService::class.java)
    }

    // Provides Conversation ApiService
    @Provides
    @Singleton
    fun provideConversationApiService(@Named("auth-retrofit") retrofitWithAuth: Retrofit): ConversationApiService {
        return retrofitWithAuth.create(ConversationApiService::class.java)
    }

    // Provides Search ApiService
    @Provides
    @Singleton
    fun provideSearchApiService(@Named("no-auth-retrofit") retrofitWithoutAuth: Retrofit): SearchApiService {
        return retrofitWithoutAuth.create(SearchApiService::class.java)
    }

    // Provides Search Repository
    @Provides
    @Singleton
    fun provideSearchRepository(searchApiService: SearchApiService): SearchRepository {
        return SearchRepository(searchApiService)
    }

    // Provides Conversation Repository
    @Provides
    @Singleton
    fun provideConversationRepository(conversationApiService: ConversationApiService): ConversationRepository {
        return ConversationRepository(conversationApiService)
    }

    // Provides Document Repository
    @Provides
    @Singleton
    fun provideDocumentRepository(documentApiService: DocumentApiService): DocumentRepository {
        return DocumentRepository(documentApiService)
    }
    // Provides Info Repository
    @Provides
    @Singleton
    fun provideInfoRepository(infoApiService: InfoApiService): InfoRepository {
        return InfoRepository(infoApiService)
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

    // Provide WebSocketManager
    @Singleton
    @Provides
    fun provideWebSocketManager(tokenManager: TokenManager): WebSocketManager {
        return WebSocketManager(tokenManager)
    }

    // Provides Data Store
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("settings") }
        )
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
