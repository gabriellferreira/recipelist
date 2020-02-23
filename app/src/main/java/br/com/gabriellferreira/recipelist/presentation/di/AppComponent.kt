package br.com.gabriellferreira.recipelist.presentation.di

import br.com.gabriellferreira.recipelist.presentation.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class, RepositoryModule::class])
interface AppComponent {

    fun newControllerComponent(controllerModule: ControllerModule): ControllerComponent
}