package me.taste2plate.app.customer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.taste2plate.app.customer.data.repo.CustomRepoImpl
import me.taste2plate.app.customer.data.repo.UserRepoImpl
import me.taste2plate.app.customer.domain.repo.CustomRepo
import me.taste2plate.app.customer.domain.repo.UserRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCustomRepo(
        appReposImpl: CustomRepoImpl
    ): CustomRepo

    @Binds
    @Singleton
    abstract fun bindUserRepo(
        appReposImpl: UserRepoImpl
    ): UserRepo
}