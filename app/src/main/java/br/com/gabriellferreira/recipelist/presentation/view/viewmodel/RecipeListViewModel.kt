package br.com.gabriellferreira.recipelist.presentation.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.gabriellferreira.recipelist.domain.datasource.RecipeDataSource
import br.com.gabriellferreira.recipelist.domain.datasource.RecipeDataSourceFactory
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import javax.inject.Inject

open class RecipeListViewModel @Inject constructor(
    dataSourceFactory: RecipeDataSourceFactory
) : ViewModel() {

    private val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(RecipeDataSource.PAGE_SIZE).build()
    var itemPagedList: LiveData<PagedList<RecipeItem>> =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<RecipeItem>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    networkState.postValue(NetworkState(NetworkState.State.IN_PROGRESS))
                }

            })
            .build()

    val networkState: MutableLiveData<NetworkState> = dataSourceFactory.networkState
}