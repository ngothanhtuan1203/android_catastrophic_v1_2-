package com.tnmobile.catastrophic.presentation.di

import androidx.paging.ExperimentalPagingApi
import com.tnmobile.catastrophic.domain.usecase.TNCatInteraction
import com.tnmobile.catastrophic.domain.usecase.TNUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@ExperimentalPagingApi
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(): BaseApplication =
        BaseApplication()

    @Provides
    @Singleton
    fun provideInteraction(tnSecureInteraction: TNCatInteraction): TNUseCase =
            tnSecureInteraction

}