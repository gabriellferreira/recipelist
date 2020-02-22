package br.com.gabriellferreira.recipelist.domain.repository

import br.com.gabriellferreira.recipelist.domain.model.Recipe
import io.reactivex.Single

interface RecipeRepository {
    fun fetchRecipeList(page: Int, itemsPerPage: Int): Single<List<Recipe>>
}