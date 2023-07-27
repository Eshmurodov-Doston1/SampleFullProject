package uz.ipoteka.samplefullproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.ipoteka.samplefullproject.service.ApiService
import javax.inject.Singleton

/***
Created by Abdurashidov Shahzod on 27/07/23.
Company Ipoteka bank.
Contact t.me/Programmer_2001.
 **/

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @[Provides Singleton]
    fun providesAuthService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}