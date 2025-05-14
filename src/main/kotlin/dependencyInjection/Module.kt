package org.example.dependencyInjection

import org.example.data.repository.MockDataMealRepository
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import org.example.presentation.FoodChangeMoodConsole
import org.koin.dsl.module

val appModule = module {
    single<MealsRepository> { MockDataMealRepository() }
    single { GetGuessGameUseCase(get()) }
    single { GetSweetsWithNoEggsUseCase(get()) }
    single { FoodChangeMoodConsole(get(),get()) }
}