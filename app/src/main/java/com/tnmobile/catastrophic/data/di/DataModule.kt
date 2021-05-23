package com.tnmobile.catastrophic.data.di

import com.data.util.SERVER_URL
import com.tnmobile.catastrophic.data.remote.APIService
import com.tnmobile.catastrophic.data.remote.RemoteDataSource
import com.tnmobile.catastrophic.data.remote.RemoteDataSourceImpl
import com.tnmobile.catastrophic.presentation.di.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication =
        BaseApplication()

    /**
     * Remote data source
     */
    @Provides
    @BaseURL
    fun provideBaseUrl() = SERVER_URL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseURL

    @Provides
    @Singleton
    fun provideOkHttpClient(tnOkHttpClient: CacheablesHttpClient): OkHttpClient =
        tnOkHttpClient.provideOkHttpClient()

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


}