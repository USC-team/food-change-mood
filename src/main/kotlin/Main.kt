package org.example

import org.example.data.repository.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.example.domain.usecase.GetSpecialIraqMealsUseCae

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
    val healthyMealsUseCase = GetHealthyMealsUseCase(MockDataMealRepository())
    val iraqMealsUseCase = GetSpecialIraqMealsUseCae(MockDataMealRepository())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println(it.name)
    }
    healthyMealsUseCase.getAllHealthyMeals().forEach {
        println(it)
    }
    iraqMealsUseCase.getSpecialIraqMeals("iraqi").forEach {
        println(it)
    }



}