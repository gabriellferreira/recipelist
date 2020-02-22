package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.presentation.di.scope.ControllerScope
import br.com.gabriellferreira.recipelist.presentation.view.activity.RecipeListActivity
import dagger.Subcomponent

@ControllerScope
@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class])
interface ControllerComponent {

    // View
    fun inject(recipeListActivity: RecipeListActivity)
}