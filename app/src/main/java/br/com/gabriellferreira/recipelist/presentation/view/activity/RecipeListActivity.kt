package br.com.gabriellferreira.recipelist.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import br.com.gabriellferreira.recipelist.R
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.domain.model.NetworkState.State.*
import br.com.gabriellferreira.recipelist.domain.model.Retryable
import br.com.gabriellferreira.recipelist.presentation.di.AppApplication
import br.com.gabriellferreira.recipelist.presentation.di.ControllerModule
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import br.com.gabriellferreira.recipelist.presentation.util.extension.hide
import br.com.gabriellferreira.recipelist.presentation.util.extension.show
import br.com.gabriellferreira.recipelist.presentation.util.extension.showRetrySnackbar
import br.com.gabriellferreira.recipelist.presentation.view.adapter.RecipeListAdapter
import br.com.gabriellferreira.recipelist.presentation.view.viewmodel.RecipeListViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_recipe_list.*
import javax.inject.Inject


class RecipeListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RecipeListViewModel

    private val adapter by lazy { RecipeListAdapter() }

    private val mControllerComponent by lazy {
        (application as AppApplication).getApplicationComponent()
            .newControllerComponent(ControllerModule(this))
    }
    private var clicksDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        mControllerComponent.inject(this)
        setupRecycler()
        initObservers()
    }

    override fun onDestroy() {
        clicksDisposable?.dispose()
        super.onDestroy()
    }

    private fun initObservers() {
        viewModel.itemPagedList.observe(this,
            androidx.lifecycle.Observer<PagedList<RecipeItem>> { items ->
                adapter.submitList(items)
            })
        viewModel.networkState.observe(this,
            androidx.lifecycle.Observer<NetworkState> {
                recipe_list_error?.setOnClickListener(null)
                when (it.state) {
                    LOADED -> {
                        recipe_list_recycler?.show()
                        recipe_list_error?.hide()
                        recipe_list_progress?.hide()
                    }
                    IN_PROGRESS -> {
                        if (adapter.itemCount == 0) {
                            recipe_list_recycler?.show()
                            recipe_list_error?.hide()
                            recipe_list_progress?.hide()
                        }
                    }
                    ERROR -> {
                        if (adapter.itemCount > 0) {
                            recipe_list_progress?.hide()
                            recipe_list_error?.hide()
                            showRetrySnackbar(it.retryable)
                        } else {
                            recipe_list_recycler?.hide()
                            recipe_list_progress?.hide()
                            recipe_list_error?.show()
                            showRetrySnackbar(it.retryable)
                        }
                    }
                }
            })
    }

    private fun showRetrySnackbar(retryable: Retryable?) {
        retryable?.let {
            showRetrySnackbar(recipe_list_parent, getString(R.string.generic_retry), it)
        }
    }

    private fun setupRecycler() {
        recipe_list_recycler?.adapter = adapter
        clicksDisposable = adapter.onItemClickSubject.subscribe {
            //            openUrlSelectorDialog(it)
        }
    }
}