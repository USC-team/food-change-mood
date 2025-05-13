package domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository

class GetKetoMealUseCase(private val repo: MealsRepository) {
    val ketoMealList = mutableSetOf<Meal>()

    fun getKetoMeal(): Meal {
        return repo.getAllMeals()
            .filter { meal ->
                isKetoMeal(meal) && meal !in ketoMealList
            }
            .randomOrNull()
            ?.also { ketoMealList.add(it) }
            ?: throw kotlin.Exception("No more keto meals available.")
    }

    fun isKetoMeal(meal: Meal): Boolean {
        val carbs = meal.nutrition?.carbohydrates ?: return false
        val protein = meal.nutrition.protein ?: return false
        return carbs <= 15.0 && protein >= 10.0
    }

}