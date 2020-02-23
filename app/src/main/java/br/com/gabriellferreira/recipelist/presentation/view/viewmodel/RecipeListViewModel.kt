package br.com.gabriellferreira.recipelist.presentation.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.domain.model.Retryable
import br.com.gabriellferreira.recipelist.domain.usecase.RecipeUseCase
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class RecipeListViewModel @Inject constructor(
    private val useCase: RecipeUseCase
) : ViewModel() {

    var itemList: MutableLiveData<NetworkState<List<RecipeItem>>> = MutableLiveData()

    fun fetchRecipeList() {
        useCase.fetchRecipeList()
            .subscribe(object : SingleObserver<List<RecipeItem>> {
                override fun onSuccess(t: List<RecipeItem>) {
                    itemList.postValue(NetworkState.Loaded(t))
                }

                override fun onSubscribe(d: Disposable) {
                    itemList.postValue(NetworkState.InProgress)
                }

                override fun onError(e: Throwable) {
                    itemList.postValue(
                        NetworkState.Error(object : Retryable {
                            override fun retry() {
                                fetchRecipeList()
                            }
                        })
                    )
                }

            })
    }
}