package br.com.gabriellferreira.recipelist.data.repository

import br.com.gabriellferreira.recipelist.data.mapper.RecipeMapper
import br.com.gabriellferreira.recipelist.domain.model.Recipe
import br.com.gabriellferreira.recipelist.domain.repository.RecipeRepository
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import io.reactivex.Single
import javax.inject.Inject

class RecipeDataRepository @Inject constructor(
    private val client: CDAClient,
    private val mapper: RecipeMapper
) : RecipeRepository {

    override fun fetchRecipeList(): Single<List<Recipe>> =
        Single.create<List<Recipe>> { emitter ->
            val result = client.fetch(CDAEntry::class.java)
                .orderBy("sys.createdAt")
                .withContentType("recipe")
                .all()

            val list = result.items().map {
                mapper.map(it)
            }

            emitter.onSuccess(list)
        }

    override fun fetchRecipe(id: String): Single<Recipe> =
        Single.create<Recipe> { emitter ->
            val result = client.fetch(CDAEntry::class.java)
                .withContentType("recipe")
                .one(id)

            emitter.onSuccess(mapper.map(result))
        }
}