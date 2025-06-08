package com.blogspot.vadim.learncompose.di

import com.blogspot.vadim.learncompose.ItemsRepository
import com.blogspot.vadim.learncompose.ItemsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ItemsRepositoryModule {

    @Binds
    fun itemsRepository(impl: ItemsRepositoryImpl): ItemsRepository
}