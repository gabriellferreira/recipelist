package br.com.gabriellferreira.recipelist.domain.mapper

import android.content.res.Resources
import br.com.gabriellferreira.recipelist.R
import br.com.gabriellferreira.recipelist.domain.model.Recipe
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import javax.inject.Inject

class RecipeItemMapper @Inject constructor(
    private val resources: Resources
) {

    fun map(list: List<Recipe>): List<RecipeItem> =
        list.map { map(it) }

    fun map(data: Recipe): RecipeItem =
        RecipeItem(
            id = data.id,
            title = data.title,
            description = data.description,
            caloriesString = getCaloriesString(data.calories),
            chefName = data.chef,
            chefNameString = getChefString(data.chef),
            tags = data.tags,
            thumbnailUrl = data.thumbnailUrl,
            tagsString= getTagsString(data.tags)
        )

    private fun getTagsString(tags: List<String>): String =
        if (tags.isNotEmpty()){
            val string = tags.toTypedArray().joinToString()
            resources.getString(R.string.recipe_tags, string)
        }else{
            ""
        }

    private fun getCaloriesString(calories: Int): String =
        if (calories > 0){
            resources.getString(R.string.recipe_calories, calories)
        }else{
            ""
        }

    private fun getChefString(chef: String): String =
        if (chef.isNotEmpty()){
            resources.getString(R.string.recipe_chef, chef)
        }else{
            ""
        }
}