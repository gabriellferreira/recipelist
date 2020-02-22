package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.data.repository.RecipeDataRepository
import br.com.gabriellferreira.recipelist.domain.repository.RecipeRepository
import br.com.gabriellferreira.recipelist.presentation.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    @ApplicationScope
    open fun provideRecipeRepository(repository: RecipeDataRepository): RecipeRepository =
        repository
}