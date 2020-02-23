package br.com.gabriellferreira.recipelist.domain.itemcallback

import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RecipeItemDiffUtilTest {

    private val diff = RecipeItemDiffUtil()

    private val item = RecipeItem(
        id = "1234",
        title = "",
        description = "",
        caloriesString = "",
        chefName = "",
        chefNameString = "",
        tags = listOf(),
        thumbnailUrl = "",
        tagsString = ""
    )

    private val item2 = RecipeItem(
        id = "1234",
        title = "title",
        description = "description",
        caloriesString = "caloriesString",
        chefName = "chefName",
        chefNameString = "chefNameString",
        tags = listOf(),
        thumbnailUrl = "thumbnailUrl",
        tagsString = "tagsString"
    )

    private val item3 = RecipeItem(
        id = "12345",
        title = "title",
        description = "description",
        caloriesString = "caloriesString",
        chefName = "chefName",
        chefNameString = "chefNameString",
        tags = listOf(),
        thumbnailUrl = "thumbnailUrl",
        tagsString = "tagsString"
    )

    @Test
    fun `should return true for same item identification`() {
        //when
        val result = diff.areItemsTheSame(item, item2)

        //then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `should return false for dif item identification`() {
        //when
        val result = diff.areItemsTheSame(item2, item3)

        //then
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `should return true for same content objects`() {
        //when
        val result = diff.areContentsTheSame(item, item)

        //then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `should return true for same content objects with dif ids`() {
        //when
        val result = diff.areContentsTheSame(item2, item3)

        //then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `should return false for dif content objects`() {
        //when
        val result = diff.areContentsTheSame(item, item2)

        //then
        assertThat(result).isEqualTo(false)
    }
}