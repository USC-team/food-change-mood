package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.domain.repository.MealsRepository

class GetSweetsWithNoEggsUseCase(private val repo: MealsRepository) {

    fun getMealHasNoEggs(): Meal {
        val randomMeal: Meal?= repo.getAllMeals()
            .filter(::getNotNullMeals)
            .filter { meal -> getMealsHasNoEggsIngredients(meal)}
            .filter { meal-> meal !in CHOSEN_MEAL_LIST }
            .randomOrNull()

        addMealToChosenList(randomMeal)

        return randomMeal?:throw Exception("No Meal Found")
    }
    private fun addMealToChosenList(randomMeal:Meal?){
        randomMeal?.let { meal-> CHOSEN_MEAL_LIST.add(meal) }
    }
    private fun getMealsHasNoEggsIngredients(meal:Meal):Boolean {
        return meal.ingredients?.none {
            it.contains("egg", ignoreCase = true)} == true
    }
    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.name != null &&
                meal.id != null &&
                meal.minutes != null &&
                meal.contributorId != null &&
                meal.submitted != null &&
                meal.tags != null &&
                meal.nutrition != null &&
                meal.nSteps != null &&
                meal.steps != null &&
                meal.description != null &&
                meal.ingredients != null &&
                meal.nIngredients != null
    }

    companion object{
        private val CHOSEN_MEAL_LIST: MutableList<Meal> = mutableListOf()
    }
}