package org.example
import org.example.presentation.FoodChangeMoodConsole

import org.example.data.repository.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val programConsole= FoodChangeMoodConsole()
    programConsole.start()

    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println(it.name)
    }
}