package org.example.domain.usecase

import domain.model.Meal
import domain.model.Nutrition
import org.example.domain.repository.MealsRepository


class GetHealthyMealsUseCase(private val repo: MealsRepository) {


    fun getHealthyQuickMealsBelowAverage(): List<Meal> =
        repo.getAllMeals().let { meals ->
            val (avgTotalFat, avgSaturatedFat, avgCarbohydrates) = meals.averageNutritionValues()
            meals.filter {
                getMealsLowFatAndMinute(
                    meal = it,
                    avgSaturatedFat = avgSaturatedFat,
                    avgTotalFat = avgTotalFat,
                    avgCarbohydrates = avgCarbohydrates
                )
            }
        }


    private fun List<Meal>.averageNutrition(selector: (Nutrition) -> Double?): Double =
        this.mapNotNull { it.nutrition?.let(selector) }.average()


    private fun List<Meal>.averageNutritionValues(): Triple<Double, Double, Double> {
        val avgTotalFat = averageNutrition { it.totalFat }
        val avgSaturatedFat = averageNutrition { it.saturatedFat }
        val avgCarbohydrates = averageNutrition { it.carbohydrates }
        return Triple(avgTotalFat, avgSaturatedFat, avgCarbohydrates)
    }


    private fun getMealsLowFatAndMinute(
        meal: Meal,
        avgTotalFat: Double,
        avgCarbohydrates: Double,
        avgSaturatedFat: Double
    ): Boolean {
        val nutrition = meal.nutrition
        val isPreparationTimeValid = meal.minutes != null && meal.minutes in 1..MINUTE
        val isNutritionBelowAverage = nutrition?.totalFat != null &&
                nutrition.saturatedFat != null &&
                nutrition.carbohydrates != null &&
                nutrition.totalFat < avgTotalFat &&
                nutrition.saturatedFat < avgSaturatedFat &&
                nutrition.carbohydrates < avgCarbohydrates
        return isPreparationTimeValid && isNutritionBelowAverage
    }

    companion object {
        private const val MINUTE = 15
    }

}