package com.zemingo.drinksmenu.ui.fragments

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.zemingo.drinksmenu.R
import com.zemingo.drinksmenu.extensions.dpToPx
import com.zemingo.drinksmenu.ui.SpacerItemDecoration
import com.zemingo.drinksmenu.ui.adapters.MethodAdapter
import com.zemingo.drinksmenu.ui.models.DrinkPreviewUiModel
import com.zemingo.drinksmenu.ui.models.DrinkUiModel
import com.zemingo.drinksmenu.ui.models.LoadingMethodUiModel
import com.zemingo.drinksmenu.ui.models.ResultUiModel
import com.zemingo.drinksmenu.ui.view_model.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_method.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MethodFragment(
    private val drinkPreviewUiModel: DrinkPreviewUiModel
) : Fragment(R.layout.fragment_method) {

    private val methodAdapter = MethodAdapter()

    @Suppress("RemoveExplicitTypeArguments")
    private val drinkViewModel: DrinkViewModel by lazy {
        requireParentFragment().getViewModel<DrinkViewModel> { parametersOf(drinkPreviewUiModel.id) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMethodRecyclerView()
        drinkViewModel
            .drink
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ResultUiModel.Success -> onDrinkReceived(it.data)
                    is ResultUiModel.Loading -> onDrinkLoading()
                }
            })
    }

    private fun initMethodRecyclerView() {
        method_rv.run {
            adapter = methodAdapter
            addItemDecoration(
                SpacerItemDecoration(bottom = 8.dpToPx().toInt())
            )
        }
    }

    private fun wrapLoaded(drinkUiModel: DrinkUiModel) =
        flow<List<LoadingMethodUiModel>> {
            emit(
                drinkUiModel
                    .instructions
                    .map { LoadingMethodUiModel.Loaded(it) }
            )
        }

    private fun wrapLoading() =
        flow<List<LoadingMethodUiModel>> {
            emit(
                listOf(
                    LoadingMethodUiModel.Loading
                )
            )
        }

    private fun onDrinkReceived(drinkUiModel: DrinkUiModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            wrapLoaded(drinkUiModel)
                .flowOn(Dispatchers.IO)
                .collect {
                    methodAdapter.update(it)
                }
        }
    }

    private fun onDrinkLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            wrapLoading()
                .flowOn(Dispatchers.IO)
                .collect { methodAdapter.update(it) }
        }
    }
}