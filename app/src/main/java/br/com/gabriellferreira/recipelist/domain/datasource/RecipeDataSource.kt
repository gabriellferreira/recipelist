package br.com.gabriellferreira.recipelist.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.domain.model.Retryable
import br.com.gabriellferreira.recipelist.domain.usecase.RecipeUseCase
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class RecipeDataSource @Inject constructor(
    private val useCase: RecipeUseCase,
    private val scheduler: Scheduler
) : PageKeyedDataSource<Int, RecipeItem>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    companion object {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RecipeItem>
    ) {
        useCase.fetchRecipeList(page = FIRST_PAGE, itemsPerPage = PAGE_SIZE)
            .subscribeOn(scheduler)
            .subscribe(object : DisposableSingleObserver<List<RecipeItem>>() {
                override fun onSuccess(list: List<RecipeItem>) {
                    callback.onResult(list, null, FIRST_PAGE + 1)
                    onNetworkStateLoaded()
                    dispose()
                }

                override fun onError(e: Throwable) {
                    onNetworkStateFailed(object : Retryable {
                        override fun retry() {
                            loadInitial(params, callback)
                        }
                    })
                    dispose()
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RecipeItem>) {
        useCase.fetchRecipeList(page = params.key, itemsPerPage = PAGE_SIZE)
            .subscribeOn(scheduler)
            .subscribe(object : DisposableSingleObserver<List<RecipeItem>>() {
                override fun onSuccess(list: List<RecipeItem>) {
                    val adjacentKey = if (params.key > 1) {
                        params.key - 1
                    } else {
                        null
                    }
                    callback.onResult(list, adjacentKey)
                    onNetworkStateLoaded()
                    dispose()
                }

                override fun onError(e: Throwable) {
                    onNetworkStateFailed(object : Retryable {
                        override fun retry() {
                            loadBefore(params, callback)
                        }
                    })
                    dispose()
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RecipeItem>) {
        useCase.fetchRecipeList(page = params.key, itemsPerPage = PAGE_SIZE)
            .subscribeOn(scheduler)
            .subscribe(object : DisposableSingleObserver<List<RecipeItem>>() {
                override fun onSuccess(list: List<RecipeItem>) {
                    callback.onResult(list, params.key + 1)
                    onNetworkStateLoaded()
                    dispose()
                }

                override fun onError(e: Throwable) {
                    onNetworkStateFailed(object : Retryable {
                        override fun retry() {
                            loadAfter(params, callback)
                        }
                    })
                    dispose()
                }
            })
    }

    private fun onNetworkStateLoaded() {
        networkState.postValue(NetworkState(NetworkState.State.LOADED))
    }

    private fun onNetworkStateFailed(retry: Retryable) {
        networkState.postValue(NetworkState(NetworkState.State.ERROR, retry))
    }
}