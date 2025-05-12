package org.example

import org.example.data.repository.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetGuessGameUseCase

fun main() {
    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println(it.name)
    }
    val randomMealUseCase= GetGuessGameUseCase(MockDataMealRepository())
    println("${randomMealUseCase.GetRandomMeal().name}  ${randomMealUseCase.GetRandomMeal().minutes}")
}