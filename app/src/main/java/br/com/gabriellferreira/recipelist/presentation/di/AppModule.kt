package br.com.gabriellferreira.recipelist.presentation.di

import android.content.Context
import android.content.res.Resources
import br.com.gabriellferreira.recipelist.BuildConfig
import com.contentful.java.cda.CDAClient
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

@Module
open class AppModule(private val appApplication: AppApplication) {

    @Provides
    open fun provideApplicationContext(): Context = appApplication

    @Provides
    open fun provideResources(context: Context): Resources = context.resources

    @Provides
    open fun provideScheduler(): Scheduler = Schedulers.io()

    @Provides
    open fun provideMarleySpoonClient(): CDAClient =
        CDAClient.builder()
            .setToken(BuildConfig.MARLEY_SPOON_CONTENTFUL_ACCESS_TOKEN)
            .setSpace(BuildConfig.MARLEY_SPOON_CONTENTFUL_SPACE_ID)
            .build()
}