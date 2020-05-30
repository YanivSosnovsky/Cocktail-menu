package com.yanivsos.mixological.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yanivsos.mixological.R
import com.yanivsos.mixological.extensions.toVisibility
import com.yanivsos.mixological.extensions.viewHolderInflate
import com.yanivsos.mixological.ui.models.IngredientUiModel
import com.yanivsos.mixological.ui.models.LoadingIngredientUiModel
import com.yanivsos.mixological.ui.utils.InputActions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_ingredient.view.*

private const val LOADING = 0
private const val LOADED = 1

class IngredientAdapter :
    DiffAdapter<LoadingIngredientUiModel, IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(ingredient: IngredientUiModel) {
            containerView.run {
                ingredient_name_tv.text = ingredient.name

                ingredient_quantity_tv.run {
                    text = ingredient.quantity
                    visibility = (ingredient.quantity != null).toVisibility()
                }
                ingredient_quantity_tv.text = ingredient.quantity
                setOnLongClickListener {
                    sendInputAction(InputActions.LongClick(LoadingIngredientUiModel.Loaded(ingredient)))
                    true
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getData(position)) {
            is LoadingIngredientUiModel.Loading -> LOADING
            is LoadingIngredientUiModel.Loaded -> LOADED
        }
    }

    override fun onBindViewHolder(
        holder: IngredientViewHolder,
        data: LoadingIngredientUiModel,
        position: Int
    ) {

        (data as? LoadingIngredientUiModel.Loaded)?.let { holder.bind(it.ingredient) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return if (viewType == LOADED) {
            IngredientViewHolder(
                parent.viewHolderInflate(R.layout.list_item_ingredient)
            )
        } else {
            IngredientViewHolder(
                parent.viewHolderInflate(R.layout.list_item_ingredient_loading)
            )
        }
    }
}