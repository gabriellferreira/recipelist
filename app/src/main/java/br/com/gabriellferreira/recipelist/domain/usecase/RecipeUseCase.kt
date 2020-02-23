package br.com.gabriellferreira.recipelist.domain.usecase

import br.com.gabriellferreira.recipelist.domain.mapper.RecipeItemMapper
import br.com.gabriellferreira.recipelist.domain.repository.RecipeRepository
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import io.reactivex.Single
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val repository: RecipeRepository,
    private val mapper: RecipeItemMapper
) {

    fun fetchRecipeList(): Single<List<RecipeItem>> =
        repository.fetchRecipeList()
            .map {
                mapper.map(it)
            }

    fun fetchRecipe(id: String): Single<RecipeItem> =
        repository.fetchRecipe(id)
            .map {
                mapper.map(it)
            }
}
