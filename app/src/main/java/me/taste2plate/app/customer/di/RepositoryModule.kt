package me.taste2plate.app.customer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.taste2plate.app.customer.data.repo.AnalyticsRepoImpl
import me.taste2plate.app.customer.data.repo.CustomRepoImpl
import me.taste2plate.app.customer.data.repo.GeoIpRepoImpl
import me.taste2plate.app.customer.data.repo.ProductRepoImpl
import me.taste2plate.app.customer.data.repo.UserRepoImpl
import me.taste2plate.app.customer.domain.repo.AnalyticsRepo
import me.taste2plate.app.customer.domain.repo.CustomRepo
import me.taste2plate.app.customer.domain.repo.GeoIpRepo
import me.taste2plate.app.customer.domain.repo.ProductRepo
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

    @Binds
    @Singleton
    abstract fun bindProductRepo(
        appReposImpl: ProductRepoImpl
    ): ProductRepo

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepo(
        appReposImpl: AnalyticsRepoImpl
    ): AnalyticsRepo
    @Binds
    @Singleton
    abstract fun bindGeoIpRepo(
        appReposImpl: GeoIpRepoImpl
    ): GeoIpRepo
}