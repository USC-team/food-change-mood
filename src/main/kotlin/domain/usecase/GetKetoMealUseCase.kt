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
        return carbs <= Constants.REQUIRED_KETO_CARB && protein >= Constants.REQUIRED_KETO_PROTEIN
    }

    private object Constants {
        const val REQUIRED_KETO_CARB = 15.0
        const val REQUIRED_KETO_PROTEIN = 10.0
    }

}