package br.com.gabriellferreira.recipelist.presentation.model

class RecipeItem (
    val id: String,
    val title: String,
    val calories: Double,
    val description: String,
    val chef: String,
    val tags: List<String>,
    val thumbnailUrl: String,
    val imageUrl: String
)