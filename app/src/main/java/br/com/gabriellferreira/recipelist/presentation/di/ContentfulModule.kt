package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.BuildConfig
import br.com.gabriellferreira.recipelist.presentation.di.scope.ApplicationScope
import com.contentful.java.cda.CDAClient
import dagger.Module
import dagger.Provides

@Module
class ContentfulModule {

    @Provides
    @ApplicationScope
    fun provideMarleySpoonClient(): CDAClient =
        CDAClient.builder()
            .setToken(BuildConfig.MARLEY_SPOON_CONTENTFUL_ACCESS_TOKEN)
            .setSpace(BuildConfig.MARLEY_SPOON_CONTENTFUL_SPACE_ID)
            .build()
}