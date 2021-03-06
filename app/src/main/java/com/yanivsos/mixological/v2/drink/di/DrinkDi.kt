package com.yanivsos.mixological.v2.drink.di

import com.yanivsos.mixological.database.DrinksDatabase
import com.yanivsos.mixological.v2.drink.repo.DrinkRepository
import com.yanivsos.mixological.v2.drink.useCases.AddToRecentlyViewedUseCase
import com.yanivsos.mixological.v2.drink.useCases.FetchAndStoreDrinkUseCase
import com.yanivsos.mixological.v2.drink.useCases.GetDrinkUseCase
import com.yanivsos.mixological.v2.drink.useCases.GetOrFetchDrinkUseCase
import com.yanivsos.mixological.v2.drink.viewModel.DrinkViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val drinkDi = module {

    single { get<DrinksDatabase>().getDrinkDao() }

    single {
        DrinkRepository(
            drinkService = get(),
            drinkDao = get(),
            favoriteDrinksDao = get()
        )
    }

    single {
        FetchAndStoreDrinkUseCase(
            repo = get(),
            fetchAndStoreIngredientUseCase = get()
        )
    }

    single {
        AddToRecentlyViewedUseCase(
            landingPageRepository = get()
        )
    }

    factory { (drinkId: String) ->
        GetDrinkUseCase(
            drinkId = drinkId,
            drinkRepository = get()
        )
    }

    factory { (drinkId: String) ->
        GetOrFetchDrinkUseCase(
            getDrinkUseCase = get { parametersOf(drinkId) },
            fetchAndStoreDrinkUseCase = get(),
            drinkId = drinkId
        )
    }

    viewModel { (drinkId: String) ->
        DrinkViewModel(
            application = androidApplication(),
            getOrFetchDrinkUseCase = get { parametersOf(drinkId) },
            toggleFavoriteUseCase = get(),
            addToRecentlyViewedUseCase = get(),
            drinkId = drinkId
        )
    }
}
