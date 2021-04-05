package com.yanivsos.mixological.v2.search.mapper

import com.yanivsos.mixological.domain.models.AlcoholicFilterModel
import com.yanivsos.mixological.domain.models.CategoryModel
import com.yanivsos.mixological.domain.models.GlassModel
import com.yanivsos.mixological.domain.models.IngredientModel
import com.yanivsos.mixological.network.response.*

fun DrinksWrapperResponse<IngredientResponse>.toIngredientsModel(): List<IngredientModel> {
    return data.map {
        IngredientModel(
            name = it.name
        )
    }
}

fun DrinksWrapperResponse<CategoryResponse>.toCategoriesModel(): List<CategoryModel> {
    return data.map {
        CategoryModel(
            name = it.category
        )
    }
}

fun DrinksWrapperResponse<GlassResponse>.toGlassesModel(): List<GlassModel> {
    return data
        .mapNotNull { it.strGlass }
        .filter { it.isNotEmpty() }
        .map { GlassModel(it) }
}

fun DrinksWrapperResponse<AlcoholicFilterResponse>.toAlcoholicsModel(): List<AlcoholicFilterModel> {
    return data
        .mapNotNull { it.strAlcoholic }
        .filterNot { it.isBlank() }
        .map {
            AlcoholicFilterModel(
                name = it
            )
        }
}
