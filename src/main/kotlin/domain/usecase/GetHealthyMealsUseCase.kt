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
        meal: Meal, avgTotalFat: Double, avgCarbohydrates: Double, avgSaturatedFat: Double
    ): Boolean {
        return meal.minutes?.let { it < minute } == true && meal.nutrition?.let { nutrition ->
            listOfNotNull(
                nutrition.totalFat?.let { it < avgTotalFat },
                nutrition.saturatedFat?.let { it < avgSaturatedFat },
                nutrition.carbohydrates?.let { it < avgCarbohydrates }).all { it }
        } == true
    }

    companion object {
       const val minute = 15
    }

}