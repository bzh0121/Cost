package cc.bzzzh.cost.di

import android.app.Application
import cc.bzzzh.cost.app.CostApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideMyApplication(application: Application): CostApplication {
        return application as CostApplication
    }
}