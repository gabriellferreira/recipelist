package br.com.gabriellferreira.recipelist.data.mapper

import br.com.gabriellferreira.recipelist.domain.model.Recipe
import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.CDAResource
import com.contentful.java.cda.LocalizedResource
import com.contentful.java.cda.image.ImageOption
import javax.inject.Inject

class RecipeMapper @Inject constructor() {

    fun map(data: CDAResource) =
        Recipe(
            id = data.id(),
            title = (data as LocalizedResource).getField<String>("title") ?: "",
            calories = data.getField<Double>("calories") ?: 0.0,
            description = data.getField("description") ?: "",
            chef = data.getField<CDAEntry>("chef")?.getField<String>("name") ?: "",
            tags = data.getField<ArrayList<CDAEntry>>("tags")?.map { tag -> tag.getField<String>("name") }
                ?: listOf(),
            thumbnailUrl = data.getField<CDAAsset>("photo").url() ?: "",
            imageUrl = data.getField<CDAAsset>("photo")?.urlForImageWith(ImageOption.https()) ?: ""
        )
}