package br.com.gabriellferreira.recipelist.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.gabriellferreira.recipelist.R
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.presentation.di.AppApplication
import br.com.gabriellferreira.recipelist.presentation.di.ControllerModule
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import br.com.gabriellferreira.recipelist.presentation.util.extension.hide
import br.com.gabriellferreira.recipelist.presentation.util.extension.loadCenterCrop
import br.com.gabriellferreira.recipelist.presentation.util.extension.show
import br.com.gabriellferreira.recipelist.presentation.util.extension.showRetrySnackbar
import br.com.gabriellferreira.recipelist.presentation.view.viewmodel.RecipeDetailViewModel
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.content_recipe_detail.*
import javax.inject.Inject
import kotlin.math.abs


class RecipeDetailActivity @Inject constructor() : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RecipeDetailViewModel

    private val recipeId by lazy { intent?.getStringExtra(EXTRA_RECIPE_ID) ?: "" }

    private val mControllerComponent by lazy {
        (application as AppApplication).getApplicationComponent()
            .newControllerComponent(ControllerModule(this))
    }

    companion object {
        const val EXTRA_RECIPE_ID = "EXTRA_RECIPE_ID"

        fun start(context: Activity, recipeId: String) {
            context.startActivity(createIntent(context, recipeId))
        }

        private fun createIntent(context: Context, recipeId: String): Intent {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_RECIPE_ID, recipeId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        mControllerComponent.inject(this)
        //Postpone the enter transition until image is loaded
        supportPostponeEnterTransition()
        initToolbar()
        initObservers()
        viewModel.fetchRecipe(recipeId)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        recipe_detail_app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            toolbar_title.alpha = abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        })
    }

    private fun initObservers() {
        viewModel.recipe.observe(this,
            Observer<NetworkState<RecipeItem>> {
                recipe_detail_error?.setOnClickListener(null)
                when (it) {
                    is NetworkState.Loaded -> {
                        recipe_detail_content?.show()
                        recipe_detail_error?.hide()
                        recipe_detail_progress?.hide()
                        initView(it.result)
                    }
                    is NetworkState.InProgress -> {
                        recipe_detail_content?.hide()
                        recipe_detail_error?.hide()
                        recipe_detail_progress?.show()
                    }
                    is NetworkState.Error -> {
                        recipe_detail_content?.hide()
                        recipe_detail_progress?.hide()
                        recipe_detail_error?.show()
                        showRetrySnackbar(
                            recipe_detail_parent,
                            R.string.generic_retry,
                            it.retryable
                        )
                    }
                }
            })
    }

    private fun initView(result: RecipeItem) {
        recipe_detail_title?.text = result.title
        toolbar_title?.text = result.title
        recipe_detail_image?.loadCenterCrop(result.thumbnailUrl)
        recipe_detail_description?.text = result.description
        recipe_detail_chef?.text = result.chefNameString
        recipe_detail_tags?.text = result.tagsString

        if (result.tagsString.isNotEmpty()) {
            recipe_detail_tags?.show()
        } else {
            recipe_detail_tags?.hide()
        }

        if (result.chefNameString.isNotEmpty()) {
            recipe_detail_chef?.show()
        } else {
            recipe_detail_chef?.hide()
        }
    }
}
