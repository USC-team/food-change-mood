package domain.usecase

import domain.model.Meal
import domain.model.isKetoMeal
import org.example.domain.repository.MealsRepository
import kotlin.collections.filter
import kotlin.collections.randomOrNull

class GetKetoMealUseCase(private val repo: MealsRepository) {
    val ketoMealList = mutableSetOf<Meal>()

    fun getKetoMeal(): Meal {
        val ketoMeal = repo.getAllMeals().filter { meal ->
            meal.isKetoMeal(ketoMealList)
        }.randomOrNull() ?: throw kotlin.Exception("No more keto meals available.")
        ketoMealList.add(ketoMeal)
        return ketoMeal
    }
}