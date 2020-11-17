package com.raul.macias.wikiheroes.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.raul.macias.wikiheroes.room.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    //La instancia de la aplicación la proporciona por BindInstance dentro del Builder del componente
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "ComicCatalogDB.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): ItemDao {
        return database.itemDao()
    }


    @Provides
    @Singleton
    fun provideComicsDao(database: AppDatabase): ComicsDao {
        return database.comicsDao()
    }

    @Provides
    @Singleton
    fun provideShopDao( database: AppDatabase) : ShopDao {
        return database.shopDao()
    }

    @Provides
    @Singleton
    fun provideFavCharacterDao( database: AppDatabase) : FavCharacterDao {
        return database.favCharacterDao()
    }

    @Singleton
    @Provides
    fun providePreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}