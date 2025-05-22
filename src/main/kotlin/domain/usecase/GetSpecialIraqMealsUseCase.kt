package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository

class GetSpecialIraqMealsUseCase(private val repo: MealsRepository) {

    companion object {
        private val IRAQ = "iraq"
        private val IRAQI = "iraqi"
    }

    fun getSpecialIraqMeals(): List<Meal> {
        return repo.getAllMeals()
            .filter { isNotEmptyTagAndDescriptionOrMeal(it) &&
                    (isIraqiMealTage(it) || isIraqiMealDescription(it)) }
    }

    private fun isIraqiMealTage(meal: Meal): Boolean {
        return meal.tags?.any {
            it.contains(IRAQ,ignoreCase = true)
        } == true
    }

    private fun isIraqiMealDescription(meal: Meal): Boolean {
        return  meal.description?.contains(IRAQI, ignoreCase = true) == true

    }
    private fun isNotEmptyTagAndDescriptionOrMeal(meal: Meal): Boolean =
        meal.tags != null && meal.description != null &&
                (meal.tags.isNotEmpty() || meal.description.isNotEmpty())
}