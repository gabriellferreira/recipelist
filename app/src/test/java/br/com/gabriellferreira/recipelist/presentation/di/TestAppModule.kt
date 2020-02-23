package br.com.gabriellferreira.recipelist.presentation.di

import android.content.Context
import android.content.res.Resources
import br.com.gabriellferreira.recipelist.presentation.util.RxSchedulersOverrideRule
import com.contentful.java.cda.CDAClient
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Scheduler

class TestAppModule(appApplication: AppApplication) : AppModule(appApplication) {

    override fun provideApplicationContext(): Context = mock()

    override fun provideScheduler(): Scheduler = RxSchedulersOverrideRule().scheduler

    override fun provideResources(context: Context): Resources = mock()

    override fun provideMarleySpoonClient(): CDAClient = mock()
}