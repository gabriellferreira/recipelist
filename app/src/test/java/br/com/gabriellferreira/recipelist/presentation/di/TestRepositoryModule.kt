package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.data.repository.RecipeDataRepository
import br.com.gabriellferreira.recipelist.domain.repository.RecipeRepository
import com.nhaarman.mockitokotlin2.mock

class TestRepositoryModule : RepositoryModule() {

    override fun provideRecipeRepository(repository: RecipeDataRepository): RecipeRepository = mock()
}