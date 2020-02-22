package br.com.gabriellferreira.recipelist.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import javax.inject.Inject

class RecipeDataSourceFactory @Inject constructor(
    private val dataSource: RecipeDataSource
) : DataSource.Factory<Int, RecipeItem>() {

    val networkState: MutableLiveData<NetworkState> = dataSource.networkState

    private val itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, RecipeItem>> =
        MutableLiveData()

    override fun create(): DataSource<Int, RecipeItem> {
        itemLiveDataSource.postValue(dataSource)
        return dataSource
    }
}