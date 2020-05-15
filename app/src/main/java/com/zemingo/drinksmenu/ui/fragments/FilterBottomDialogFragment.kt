package com.zemingo.drinksmenu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zemingo.drinksmenu.R
import com.zemingo.drinksmenu.domain.models.DrinkFilter
import com.zemingo.drinksmenu.domain.models.FilterType
import com.zemingo.drinksmenu.extensions.compatColor
import com.zemingo.drinksmenu.extensions.dpToPx
import com.zemingo.drinksmenu.extensions.viewHolderInflate
import com.zemingo.drinksmenu.ui.GridSpacerItemDecoration
import com.zemingo.drinksmenu.ui.adapters.DiffAdapter
import com.zemingo.drinksmenu.ui.models.DrinkFilterUiModel
import com.zemingo.drinksmenu.ui.models.SearchFiltersUiModel
import com.zemingo.drinksmenu.ui.view_model.AdvancedSearchViewModel
import com.zemingo.drinksmenu.ui.views.FilterHeaderView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bottom_dialog_search_filters.*
import kotlinx.android.synthetic.main.list_item_selectable_filter.view.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class FilterBottomDialogFragment : BottomSheetDialogFragment() {

    private val advancedSearchViewModel: AdvancedSearchViewModel by lazy {
        @Suppress("RemoveExplicitTypeArguments")
        requireParentFragment().getViewModel<AdvancedSearchViewModel>()
    }
    private val alcoholicAdapter = SelectableAdapter().apply {
        onClicked = { onFilterClicked(it) }
    }

    private val ingredientsAdapter = SelectableAdapter().apply {
        onClicked = { onFilterClicked(it) }
    }

    private val categoryAdapter = SelectableAdapter().apply {
        onClicked = { onFilterClicked(it) }
    }

    private val glassAdapter = SelectableAdapter().apply {
        onClicked = { onFilterClicked(it) }
    }

    private fun onFilterClicked(it: DrinkFilter) {
        advancedSearchViewModel.updateFilter(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_search_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFiltersRecyclerView()
        observerFilters()
        filter_accept_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun observerFilters() {
        advancedSearchViewModel.searchFiltersLiveData.observe(
            viewLifecycleOwner, Observer {
                Timber.d("Received filters: ${it.activeFilters}")
                updateResults(it)
                updateActiveFilters(it)
            }
        )
    }

    private fun updateResults(searchFiltersUiModel: SearchFiltersUiModel) {
        updateSelectableAdapter(alcoholicAdapter, searchFiltersUiModel.filters[FilterType.ALCOHOL])
        updateSelectableAdapter(categoryAdapter, searchFiltersUiModel.filters[FilterType.CATEGORY])
        updateSelectableAdapter(glassAdapter, searchFiltersUiModel.filters[FilterType.GLASS])
        updateSelectableAdapter(
            ingredientsAdapter,
            searchFiltersUiModel.filters[FilterType.INGREDIENTS]
        )
    }

    private fun updateActiveFilters(searchFiltersUiModel: SearchFiltersUiModel) {
        Timber.d("updateActiveFilters: ${searchFiltersUiModel.activeFilters}")
        updateFilterHeaders(
            alcoholic_header_tv,
            searchFiltersUiModel.activeFilters[FilterType.ALCOHOL]
        )
        updateFilterHeaders(
            category_header_tv,
            searchFiltersUiModel.activeFilters[FilterType.CATEGORY]
        )
        updateFilterHeaders(
            glasses_header_tv,
            searchFiltersUiModel.activeFilters[FilterType.GLASS]
        )

        updateFilterHeaders(
            ingredient_header_tv,
            searchFiltersUiModel.activeFilters[FilterType.INGREDIENTS]
        )
    }

    private fun updateFilterHeaders(filterHeaderView: FilterHeaderView, activeFilters: Int?) {
        filterHeaderView.run {
            filters = activeFilters?.toString()
        }
    }

    private fun updateSelectableAdapter(
        selectableAdapter: SelectableAdapter,
        filters: List<DrinkFilterUiModel>?
    ) {
        selectableAdapter.set(filters ?: emptyList())
    }

    private fun initFiltersRecyclerView() {
        initRecyclerView(filter_alcoholic_rv, alcoholicAdapter)
        initRecyclerView(filter_ingredients_rv, ingredientsAdapter)
        initRecyclerView(filter_category_rv, categoryAdapter)
        initRecyclerView(filter_glasses_rv, glassAdapter)
    }

    private fun initRecyclerView(recyclerView: RecyclerView, selectableAdapter: SelectableAdapter) {
        recyclerView.run {
            val padding = 4.dpToPx().toInt()
            addItemDecoration(
                GridSpacerItemDecoration(
                    top = padding,
                    bottom = padding,
                    left = padding,
                    right = padding
                )
            )
            adapter = selectableAdapter
        }
    }
}


class SelectableAdapter :
    DiffAdapter<DrinkFilterUiModel, SelectableAdapter.SelectableViewHolder>() {

    var onClicked: ((DrinkFilter) -> Unit)? = null

    inner class SelectableViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        //todo - fix shitty code
        fun bind(filter: DrinkFilterUiModel) {
            containerView.run {
                setOnClickListener { invokeFilterClicked(filter) }

                val selectedColor = context.compatColor(filter.color)
                card_container.run {
                    strokeColor = selectedColor
                    elevation = filter.elevation
                    alpha = filter.alpha
                }
                filter_tv.run {
                    setTextColor(selectedColor)
                    text = filter.name
                }
            }
        }
    }

    private fun invokeFilterClicked(filter: DrinkFilterUiModel) {
        onClicked?.invoke(filter.drinkFilter.copy(active = !filter.selected))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableViewHolder {
        return SelectableViewHolder(
            parent.viewHolderInflate(R.layout.list_item_selectable_filter)
        )
    }

    override fun onBindViewHolder(
        holder: SelectableViewHolder,
        data: DrinkFilterUiModel,
        position: Int
    ) {
        holder.bind(data)
    }
}