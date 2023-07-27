package uz.ipoteka.samplefullproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.ipoteka.samplefullproject.utils.localeStorage.LocalStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @[Provides Singleton]
    fun provideLocalStorage(@ApplicationContext context: Context): LocalStorage {
        return LocalStorage(context)
    }

}