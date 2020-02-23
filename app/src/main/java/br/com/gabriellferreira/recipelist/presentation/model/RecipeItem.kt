package br.com.gabriellferreira.recipelist.presentation.model

class RecipeItem(
    val id: String,
    val title: String,
    val description: String,
    val caloriesString: String,
    val chefName: String,
    val chefNameString: String,
    val tags: List<String>,
    val thumbnailUrl: String,
    val tagsString: String
)