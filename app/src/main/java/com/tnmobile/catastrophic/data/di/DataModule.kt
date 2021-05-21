package com.tnmobile.catastrophic.data.di

import android.content.Context
import com.tnmobile.catastrophic.data.local.room.AppDatabase
import com.tnmobile.catastrophic.data.remote.APIService
import com.tnmobile.catastrophic.data.remote.RemoteDataSource
import com.tnmobile.catastrophic.data.remote.RemoteDataSourceImpl
import com.data.util.*
import com.tnmobile.catastrophic.data.local.LocalDataSource
import com.tnmobile.catastrophic.data.local.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {
    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
    /**
     * Remote data source
     */
    @Provides
    @BaseURL
    fun provideBaseUrl() = SERVER_URL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseURL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ServerPublicCertificate

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class APIKey

    @Provides
    @Singleton
    fun provideOkHttpClient(@BaseURL BASE_URL: String): OkHttpClient {


        val httpClient: OkHttpClient.Builder =
            OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())
        httpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseURL BASE_URL: String
    ): Retrofit {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        return builder.client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(
        APIService::class.java
    )


    @Provides
    @Singleton
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource =
            remoteDataSource


    /**
     * Local data source
     */

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideNoteDao(db: AppDatabase) = db.noteDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource = localDataSource

}