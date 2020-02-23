package br.com.gabriellferreira.recipelist.data.mapper

import br.com.gabriellferreira.recipelist.domain.model.Recipe
import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.CDAResource
import com.contentful.java.cda.LocalizedResource
import com.contentful.java.cda.image.ImageOption
import javax.inject.Inject

class RecipeMapper @Inject constructor() {

    companion object {
        const val THUMBNAIL_JPEG_QUALITY = 20
    }

    fun map(data: CDAResource) =
        Recipe(
            id = data.id(),
            title = (data as LocalizedResource).getField<String>("title") ?: "",
            calories = data.getField<Double>("calories").toInt(),
            description = data.getField("description") ?: "",
            chef = data.getField<CDAEntry>("chef")?.getField<String>("name") ?: "",
            tags = data.getField<ArrayList<CDAEntry>>("tags")?.map { tag -> tag.getField<String>("name") }
                ?: listOf(),
            thumbnailUrl = data.getField<CDAAsset>("photo")?.urlForImageWith(
                ImageOption.https(),
                ImageOption.jpegQualityOf(THUMBNAIL_JPEG_QUALITY)
            ) ?: "",
            imageUrl = data.getField<CDAAsset>("photo")?.urlForImageWith(ImageOption.https()) ?: ""
        )
}