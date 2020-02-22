package br.com.gabriellferreira.recipelist.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.gabriellferreira.recipelist.R
import br.com.gabriellferreira.recipelist.domain.itemcallback.RecipeItemDiffUtil
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import br.com.gabriellferreira.recipelist.presentation.util.extension.inflate
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeListAdapter :
    PagedListAdapter<RecipeItem, RecipeListAdapter.ViewHolder>(RecipeItemDiffUtil()) {

    val onItemClickSubject: PublishSubject<RecipeItem> = PublishSubject.create<RecipeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_recipe))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
        }
    }

    inner class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        internal fun bind(
            model: RecipeItem
        ) {
            view.setOnClickListener {
                onItemClickSubject.onNext(model)
            }
            view.item_recipe_title?.text = model.title
            view.item_recipe_description?.text = model.description
        }
    }
}