package com.zemingo.drinksmenu.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zemingo.drinksmenu.ui.models.DrinkUiModel
import com.zemingo.drinksmenu.ui.models.ResultUiModel
import com.zemingo.drinksmenu.ui.view_model.DrinkViewModel
import org.koin.android.viewmodel.ext.android.getViewModel

/*
abstract class BaseDrinkFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract fun onDrinkReceived(drinkUiModel: DrinkUiModel)

    protected abstract fun onDrinkLoading(id: String)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDrink()
    }

    protected open fun getViewModel(): DrinkViewModel {
        return requireParentFragment().getViewModel()
    }

    private fun observeDrink() {
        getViewModel()
            .drink
            .observe(viewLifecycleOwner, Observer { onDrinkResultReceived(it) })
    }

    private fun onDrinkResultReceived(result: ResultUiModel<DrinkUiModel>) {
        when (result) {
            is ResultUiModel.Success -> onDrinkReceived(result.data)
            is ResultUiModel.Loading -> onDrinkLoading(result.id)
        }
    }
}*/
