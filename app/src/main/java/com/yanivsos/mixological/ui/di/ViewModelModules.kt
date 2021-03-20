package com.yanivsos.mixological.ui.di

import com.yanivsos.mixological.ui.mappers.*
import com.yanivsos.mixological.ui.utils.SetNightModeUseCase
import com.yanivsos.mixological.ui.view_model.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ConnectivityViewModel()
    }

    viewModel {
        CategoriesViewModel(
            combineWithFavoriteUseCase = get(),
            categoriesUseCase = get(),
            getDrinkPreviewByCategoryUseCase = get(),
            categoriesMapper = get<CategoryMapperUi>(),
            previewMapper = get<DrinkPreviewMapperUi>()
        )
    }

    /*viewModel { (id: String) ->
        *//*DrinkViewModel(
            getDrinkUseCase = get { parametersOf(id) },
            addToRecentlyViewedUseCase = get(),
            toggleWatchlistUseCase = get(),
            resultMapper = get<ResultDrinkMapperUi> { parametersOf(id) },
            drinkId = id
        )*//*

        DrinkViewModel(
            context = androidContext(),
            getOrFetchDrinkUseCase = get(),
            toggleWatchlistUseCase = get()
        )
    }*/

    /*viewModel {
        LandingPageViewModel(
            mostPopularUseCase = get(),
            latestArrivalsUseCase = get(),
            recentSearchesUseCase = get(),
            updateLatestArrivalsUseCase = get(),
            updateMostPopularUseCase = get(),
            mapper = get<DrinkPreviewMapperUi>()
        )
    }*/

    viewModel { (name: String) ->
        IngredientDetailsViewModel(
            getIngredientDetailsUseCase = get { parametersOf(name) },
            mapper = get<IngredientDetailsMapperUi>()
        )
    }

    viewModel {
        AdvancedSearchViewModel(
            getIngredientsUseCase = get(),
            getSearchFiltersUseCase = get(),
            getAutoCompleteSuggestionsUseCase = get(),
            filter = get(),
            mapper = get<DrinkPreviewMapperUi>(),
            searchMapper = get<SearchFiltersMapperUi>()
        )
    }

    viewModel { (id: String) ->
        DrinkPreviewOptionsViewModel(
            addToWatchlistUseCase = get(),
            getDrinkPreviewUseCase = get(),
            removeFromWatchlistUseCase = get(),
            mapper = get<DrinkPreviewMapperUi>(),
            id = id
        )
    }

    /*viewModel {
        WatchlistViewModel(
            getWatchlistUseCase = get(),
            mapper = get<DrinkPreviewMapperUi>()
        )
    }*/

    viewModel {
        SettingsViewModel(SetNightModeUseCase())
    }
}
