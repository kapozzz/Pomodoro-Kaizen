package com.kapozzz.timer.di

import com.kapozzz.timer.navigation.TimerApi
import com.kapozzz.timer.navigation.TimerApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {
    @Provides
    fun provideDetailsApi(): TimerApi {
        return TimerApiImpl()
    }
}