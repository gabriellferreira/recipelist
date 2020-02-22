package br.com.gabriellferreira.recipelist.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.gabriellferreira.recipelist.domain.datasource.RecipeDataSourceFactory
import br.com.gabriellferreira.recipelist.presentation.view.viewmodel.RecipeListViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    fun getRecipeListViewModel(dataSourceFactory: RecipeDataSourceFactory): ViewModel {
        return RecipeListViewModel(dataSourceFactory)
    }

    @Provides
    fun getViewModelFactory(
        map: Map<Class<out ViewModel>,
                @JvmSuppressWildcards ViewModel>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(map)
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val viewModelMap: Map<Class<out ViewModel>, ViewModel>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelMap[modelClass] as T
    }
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)