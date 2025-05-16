package org.example
import org.example.presentation.FoodChangeMoodConsole
import org.example.dependencyInjection.appModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

import org.example.data.repository.data.repository.MockDataMealRepository
import org.example.domain.usecase.GetEasyPreparedMealsUseCase
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.example.domain.usecase.GetSpecialIraqMealsUseCae

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    startKoin {
    modules(appModule)
    val mealsUseCase = GetEasyPreparedMealsUseCase(MockDataMealRepository())
    val healthyMealsUseCase = GetHealthyMealsUseCase(MockDataMealRepository())
    val iraqMealsUseCase = GetSpecialIraqMealsUseCae(MockDataMealRepository())
    mealsUseCase.getEasyPreparedMeals().forEach {
        println(it.name)
    }

    val programConsole: FoodChangeMoodConsole = getKoin().get()
    programConsole.start()
    healthyMealsUseCase.getHealthyQuickMealsBelowAverage().forEach {
        println(it)
    }
    iraqMealsUseCase.getSpecialIraqMeals().forEach {
        println(it)
    }



}