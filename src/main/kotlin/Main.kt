package org.example

import org.example.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import java.io.File

fun main() {

    val file= File("food.csv")
    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository(file))
    mealsUseCase.getEasyPreparedMeals().forEach {
        println("name: ${it.name}")
    }
    val randomMealUseCase= GetGuessGameUseCase(MockDataMealRepository(file))
    val meal = randomMealUseCase.getRandomMeal()
    val guessedMinutes=20
    println("name:${randomMealUseCase.getRandomMeal().name}\n" +
            "correct minutes:${randomMealUseCase.getRandomMeal().minutes} \n" +
            "guessed minutes:$guessedMinutes ${randomMealUseCase.isGuessCorrectHighOrLow(meal,guessedMinutes)}")

    println(GetSweetsWithNoEggsUseCase(MockDataMealRepository(file)).getMealHasNoEggs().name)
    println(GetSweetsWithNoEggsUseCase(MockDataMealRepository(file)).getMealHasNoEggs().name)

}