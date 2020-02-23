package br.com.gabriellferreira.recipelist.presentation.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.domain.model.Retryable
import br.com.gabriellferreira.recipelist.domain.usecase.RecipeUseCase
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(
    private val useCase: RecipeUseCase,
    private val schedulers: Scheduler
) : ViewModel() {

    val recipe: MutableLiveData<NetworkState<RecipeItem>> = MutableLiveData()

    fun fetchRecipe(recipeId: String) {
        useCase.fetchRecipe(recipeId)
            .subscribeOn(schedulers)
            .subscribe(object : SingleObserver<RecipeItem> {
                override fun onSuccess(item: RecipeItem) {
                    recipe.postValue(NetworkState.Loaded(item))
                }

                override fun onSubscribe(d: Disposable) {
                    recipe.postValue(NetworkState.InProgress)
                }

                override fun onError(e: Throwable) {
                    recipe.postValue(
                        NetworkState.Error(object : Retryable {
                            override fun retry() {
                                fetchRecipe(recipeId)
                            }
                        })
                    )
                }
            })
    }
}