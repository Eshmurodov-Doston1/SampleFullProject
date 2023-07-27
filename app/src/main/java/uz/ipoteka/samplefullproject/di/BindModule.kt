package uz.ipoteka.samplefullproject.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.ipoteka.samplefullproject.repoContainer.repository.ApiRepository
import uz.ipoteka.samplefullproject.repoContainer.repositoryImpl.ApiRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {
    @Binds
    fun apiRepository(apiRepositoryImpl: ApiRepositoryImpl):ApiRepository
}