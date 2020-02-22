package br.com.gabriellferreira.recipelist.domain.mapper

import br.com.gabriellferreira.recipelist.domain.model.Recipe
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import javax.inject.Inject

class RecipeItemMapper @Inject constructor() {

    fun map(list: List<Recipe>): List<RecipeItem> =
        list.map { map(it) }

    private fun map(data: Recipe): RecipeItem =
        RecipeItem(
            id = data.id,
            title = data.title,
            calories = data.calories,
            description = data.description,
            chef = data.chef,
            tags = data.tags,
            thumbnailUrl = data.thumbnailUrl,
            imageUrl = data.imageUrl
        )
}