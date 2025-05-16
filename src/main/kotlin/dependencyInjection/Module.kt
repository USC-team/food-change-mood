package org.example.dependencyInjection

import domain.usecase.GetKetoMealUseCase
import org.example.data.repository.MockDataMealRepository
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.example.domain.usecase.GetSpecialIraqMealsUseCae
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
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
    single { GetHealthyMealsUseCase(get()) }
    single { GetSpecialIraqMealsUseCae(get()) }
    single { FoodChangeMoodConsole(get(), get(), get(), get(),get(), get()) }
}