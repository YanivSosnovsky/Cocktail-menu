package com.yanivsos.mixological.v2.drink.mappers

import com.yanivsos.mixological.domain.models.DrinkModel
import com.yanivsos.mixological.domain.models.DrinkPreviewModel
import com.yanivsos.mixological.repo.models.DrinkPreviewResponse
import com.yanivsos.mixological.repo.models.DrinkResponse
import com.yanivsos.mixological.repo.models.DrinksWrapperResponse

fun DrinksWrapperResponse<DrinkResponse>.toFirstOrNullModel(): DrinkModel? {
    return this.data.firstOrNull()?.toModel()
}

fun DrinksWrapperResponse<DrinkPreviewResponse>.toModel(): List<DrinkPreviewModel> {
    return this.data.map { it.toModel() }
}
