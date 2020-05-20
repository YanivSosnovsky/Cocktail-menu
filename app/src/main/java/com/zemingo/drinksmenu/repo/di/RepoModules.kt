package com.zemingo.drinksmenu.repo.di

import com.zemingo.drinksmenu.repo.DrinkService
import com.zemingo.drinksmenu.repo.mappers.*
import com.zemingo.drinksmenu.repo.reactiveStore.*
import com.zemingo.drinksmenu.repo.repositories.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("RemoveExplicitTypeArguments")
val repoModule = module {

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<DrinkService> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .build().create(DrinkService::class.java)
    }


    factory {
        CategoryRepository(
            service = get<DrinkService>(),
            reactiveStore = get<CategoryReactiveStore>(),
            mapper = get<CategoryMapper>()
        )
    }

    factory {
        IngredientRepository(
            service = get<DrinkService>(),
            reactiveStore = get<IngredientReactiveStore>(),
            mapper = get<IngredientMapper>()
        )
    }

    factory {
        DrinkPreviewRepository(
            service = get<DrinkService>(),
            reactiveStore = get<DrinkPreviewReactiveStore>(),
            mapper = get<DrinkPreviewMapper>(),
            searchMapper = get<SearchDrinkPreviewMapper>()
        )
    }

    factory {
        RecentlyViewedRepository(
            recentlyViewedReactiveStore = get<RecentlyViewedReactiveStore>(),
            drinkReactiveStore = get<DrinkPreviewReactiveStore>()
        )
    }

    factory {
        DrinkRepository(
            drinksReactiveStore = get<DrinkReactiveStore>(),
            service = get<DrinkService>(),
            mapper = get<DrinkMapper>()
        )
    }

    factory {
        IngredientDetailsRepository(
            service = get<DrinkService>(),
            reactiveStore = get<IngredientDetailsReactiveStore>(),
            mapper = get<IngredientDetailsMapper>()
        )
    }

    factory {
        AdvancedSearchRepository(
            service = get<DrinkService>(),
            drinkMapper = get<SearchDrinkMapper>(),
            previewMapper = get<DrinkPreviewMapper>(),
            previewReactiveStore = get(),
            drinkReactiveStore = get()

        )
    }

    factory {
        GlassRepository(
            service = get<DrinkService>(),
            reactiveStore = get<GlassReactiveStore>(),
            mapper = get<GlassMapper>()
        )
    }

    factory {
        AlcoholicFilterRepository(
            service = get<DrinkService>(),
            reactiveStore = get<AlcoholicFiltersReactiveStore>(),
            mapper = get<AlcoholicFilterMapper>()
        )
    }
    
    factory { 
        WatchlistRepository(
            watchlistReactiveStore = get()
        )
    }
}