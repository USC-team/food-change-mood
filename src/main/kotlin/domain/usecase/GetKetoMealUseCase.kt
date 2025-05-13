package domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository

class GetKetoMealUseCase(private val repo: MealsRepository) {
    val ketoMealList = mutableSetOf<Meal>()

    fun getKetoMeal(): Meal {
        return repo.getAllMeals()
            .filter { meal ->
            isKetoMeal(meal) && meal !in ketoMealList}
            .randomOrNull()
            ?.store()
            ?: throw kotlin.Exception("No more keto meals available.")
    }
    fun isKetoMeal(meal: Meal): Boolean {
        val carbs = meal.nutrition?.carbohydrates ?: return false
        val protein = meal.nutrition.protein ?: return false
        return carbs <= 15.0 && protein >= 10.0
    }

    fun Meal.store(): Meal {
        ketoMealList.add(this)
        return this
    }
}