package br.com.gabriellferreira.recipelist.domain.itemcallback

import androidx.recyclerview.widget.DiffUtil
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem


class RecipeItemDiffUtil : DiffUtil.ItemCallback<RecipeItem>() {

    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.description == newItem.description &&
                oldItem.title == newItem.title &&
                oldItem.calories == newItem.calories &&
                oldItem.description == newItem.description &&
                oldItem.chef == newItem.chef &&
                oldItem.tags == newItem.tags &&
                oldItem.thumbnailUrl == newItem.thumbnailUrl &&
                oldItem.imageUrl == newItem.imageUrl
    }
}