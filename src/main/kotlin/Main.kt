package org.example
import org.example.presentation.FoodChangeMoodConsole

import org.example.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase

fun main() {
    val repository = MockDataMealRepository()
    val getGuessGameUseCase= GetGuessGameUseCase(repository)
    val getSweetsWithNoEggsUseCase= GetSweetsWithNoEggsUseCase(repository)
    val programConsole= FoodChangeMoodConsole(getGuessGameUseCase,getSweetsWithNoEggsUseCase)
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