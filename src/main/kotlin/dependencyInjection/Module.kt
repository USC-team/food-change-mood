package org.example.dependencyInjection

import data.repository.CsvParsers
import domain.usecase.GetKetoMealUseCase
import org.example.data.repository.MockDataMealRepository
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import org.example.domain.usecase.SearchMealsByDateUseCase
import org.example.presentation.FoodChangeMoodConsole
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single<MealsRepository> { MockDataMealRepository(get()) }
    single { GetGuessGameUseCase(get()) }
    single { GetSweetsWithNoEggsUseCase(get()) }
    single { GetEasyPreparedMealsUseCase(get()) }
    single { GetKetoMealUseCase(get()) }
    single { SearchMealsByDateUseCase(get()) }
    single { FoodChangeMoodConsole(get(), get(), get(), get(), get()) }
}