package com.hackatron52.androidacademyhackathon.di

import com.hackatron52.androidacademyhackathon.presentation.mapper.DateMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CoreViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideDateMapper(): DateMapper =
        DateMapper()
}