package com.yanivsos.mixological.ui.mappers

import android.content.Context
import android.text.SpannableString
import com.yanivsos.mixological.R
import com.yanivsos.mixological.domain.models.DrinkModel
import com.yanivsos.mixological.ui.models.DrinkUiModel
import com.yanivsos.mixological.ui.models.IngredientUiModel
import java.util.function.Function

class DrinkMapperListUi(
    private val singleMapper: Function<DrinkModel, DrinkUiModel>
) : Function<List<DrinkModel>, List<DrinkUiModel>> {
    override fun apply(t: List<DrinkModel>): List<DrinkUiModel> {
        return t.map { singleMapper.apply(it) }
    }
}

class DrinkMapperUi(
    private val appCtx: Context
) : Function<DrinkModel, DrinkUiModel> {

    override fun apply(t: DrinkModel): DrinkUiModel {
        return DrinkUiModel(
            id = t.id,
            name = t.name,
            instructions = mapInstructions(t),
            ingredients = mapIngredients(t),
            category = t.category,
            alcoholic = t.alcoholic,
            glass = t.glass,
            thumbnail = t.thumbnail,
            video = t.video,
            shareText = mapShareText(t),
            isFavorite = t.isFavorite
        )
    }

    private fun mapInstructions(t: DrinkModel): List<SpannableString> {
        return t.instructions
            .trimIndent()
            .split(". ")
            .map { instruction ->
                SpannableString("$instruction.").apply {
//                    if (instruction.firstOrNull()?.isLetter() == true) {
//                        setSpan(RelativeSizeSpan(1.5f), 0, 1, 0)
//                    }
                }
            }
    }

    private fun mapIngredients(t: DrinkModel): List<IngredientUiModel> =
        t.ingredients
            .map {
                IngredientUiModel(
                    name = it.key,
                    quantity = it.value
                )
            }

    private fun mapShareText(t: DrinkModel): String {
        return StringBuilder().apply {
            append(t.name)
            append("\n\n")
            t.ingredients.run {
                if (isNotEmpty()) {
                    append(appCtx.getString(R.string.ingredients))
                    append("\n")
                    forEach { ingredient, quantity ->
                        append("■ ")
                        append(ingredient)
                        if (quantity?.isNotBlank() == true) {
                            append(" - ")
                            append(quantity)
                        }
                        append("\n")
                    }
                    append("\n")
                }
            }
            append(appCtx.getString(R.string.method))
            append(" - \n")
            append(t.instructions)
        }.toString()
    }
}