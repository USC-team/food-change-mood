package org.example.dependencyInjection

import domain.usecase.GetKetoMealUseCase
import org.example.data.repository.MockDataMealRepository
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetMealsUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import org.example.presentation.FoodChangeMoodConsole
import org.koin.dsl.module

val appModule = module {
    single<MealsRepository> { MockDataMealRepository() }
    single { GetGuessGameUseCase(get()) }
    single { GetSweetsWithNoEggsUseCase(get()) }
    single { GetEasyPreparedMealsUseCase(get()) }
    single { GetKetoMealUseCase(get()) }
    single { FoodChangeMoodConsole(get(),get(),get(),get()) }
}