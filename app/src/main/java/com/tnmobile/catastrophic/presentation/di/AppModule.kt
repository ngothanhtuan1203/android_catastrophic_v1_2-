package com.tnmobile.catastrophic.presentation.di

import com.tnmobile.catastrophic.domain.usecase.TNCatInteractor
import com.tnmobile.catastrophic.domain.usecase.TNUseCase
import com.tnmobile.catastrophic.presentation.di.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(): BaseApplication =
        BaseApplication()

    @Provides
    @Singleton
    fun provideInteractor(tnSecureInteractor: TNCatInteractor): TNUseCase =
            tnSecureInteractor

}