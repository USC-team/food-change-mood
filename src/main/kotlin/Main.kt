package org.example

import domain.model.Meal
import org.example.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase
import org.example.domain.usecase.GetSweetsWithNoEggsUseCase
import java.io.File

fun main() {

    val mealRepository= MockDataMealRepository()
    val meals: List<Meal> = mealRepository.loadAll()

    println("Loaded ${meals.size} meals")
    meals
        .sortedBy { it.minutes ?: Int.MAX_VALUE }
        .take(2)
        .forEach { println(it) }


    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
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

}