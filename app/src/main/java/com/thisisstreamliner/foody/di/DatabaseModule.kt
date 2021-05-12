package com.thisisstreamliner.foody.di

import android.content.Context
import androidx.room.Room
import com.thisisstreamliner.foody.data.local.database.RecipesDatabase
import com.thisisstreamliner.foody.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
        = Room.databaseBuilder(context, RecipesDatabase::class.java, Constants.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}