package domain.usecase

import domain.model.Meal
import domain.usecase.exceptions.MealNotFoundExceptions
import domain.repository.MealsRepository

class GetKetoMealUseCase(private val repo: MealsRepository) {
    private val ketoMealList = mutableSetOf<Meal>()

    fun getKetoMeal(): Meal {
        return repo.getAllMeals()
            .filter { meal ->
                isKetoMeal(meal) && meal !in ketoMealList
            }
            .randomOrNull()
            ?.also { ketoMealList.add(it) }
            ?: throw MealNotFoundExceptions()
    }

     private fun isKetoMeal(meal: Meal): Boolean {
        val carbs = meal.nutrition?.carbohydrates ?: return false
        val protein = meal.nutrition.protein ?: return false
        return carbs <= REQUIRED_KETO_CARB && protein >= REQUIRED_KETO_PROTEIN
    }

    companion object Constants {
        private const val REQUIRED_KETO_CARB = 15.0
        private const val REQUIRED_KETO_PROTEIN = 10.0
    }
}