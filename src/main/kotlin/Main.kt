package org.example

import org.example.data.repository.data.repository.MealsRepositoryImplementation
import org.example.domain.usecase.GetMealsUseCase

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val mealsUseCase = GetMealsUseCase(MealsRepositoryImplementation())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println(it.name)
    }
}