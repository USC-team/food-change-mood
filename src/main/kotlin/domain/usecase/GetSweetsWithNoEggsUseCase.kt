package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository
import domain.usecase.exceptions.MealNotFoundExceptions

class GetSweetsWithNoEggsUseCase(private val repo: MealsRepository) {

    private val chosenMealList: MutableList<Meal> = mutableListOf()

    fun getMealHasNoEggs(): Meal {
        return repo.getAllMeals()
            .filter { meal-> getNotNullMeals(meal) &&
                    meal !in chosenMealList &&
                    getMealsHasNoEggsIngredients(meal)}
            .randomOrNull()
            ?.also { chosenMealList.add(it) }
         ?:throw MealNotFoundExceptions()
    }
    private fun getMealsHasNoEggsIngredients(meal:Meal):Boolean {
        return meal.ingredients?.none {
            it.contains("egg", ignoreCase = true)} == true
    }
    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.ingredients != null
    }
}