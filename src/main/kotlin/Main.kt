package org.example
import org.example.presentation.FoodChangeMoodConsole

import org.example.data.repository.MockDataMealRepository
import org.example.dependencyInjection.appModule
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
    modules(appModule)
    }

    val programConsole: FoodChangeMoodConsole = getKoin().get()
    programConsole.start()

    /*val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println("name: ${it.name}")
    }
    val randomMealUseCase= GetGuessGameUseCase(MockDataMealRepository())
    val meal = randomMealUseCase.getRandomMeal()
    val guessedMinutes=20
    println("name:${randomMealUseCase.getRandomMeal().name}\n" +
            "correct minutes:${randomMealUseCase.getRandomMeal().minutes} \n" +
            "guessed minutes:$guessedMinutes ${randomMealUseCase.isGuessCorrectHighOrLow(meal,guessedMinutes)}")

    println(GetSweetsWithNoEggsUseCase(MockDataMealRepository()).getMealHasNoEggs().name)
    println(GetSweetsWithNoEggsUseCase(MockDataMealRepository()).getMealHasNoEggs().name)
*/
}