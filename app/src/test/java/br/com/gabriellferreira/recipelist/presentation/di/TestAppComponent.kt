package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.presentation.di.scope.ApplicationScope
import br.com.gabriellferreira.recipelist.presentation.view.viewmodel.RecipeDetailViewModelTest
import br.com.gabriellferreira.recipelist.presentation.view.viewmodel.RecipeListViewModelTest
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class, RepositoryModule::class])
interface TestAppComponent : AppComponent {

    fun inject(viewmodel: RecipeDetailViewModelTest)
    fun inject(viewmodel: RecipeListViewModelTest)
}