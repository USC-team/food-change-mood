package org.example.domain.usecase
import domain.model.Meal
import org.example.domain.repository.domain.repository.MealsRepository

class GetRandomMealUseCase(private val repo: MealsRepository) {
    fun getRandomMeal(): Meal {
        return repo.getAllMeals().filter(::getNotNullMeals).random()
    }

    fun checkMinutesOfMealForGuessGame(meal: Meal, minutes: Int): Boolean {
        return meal.minutes == minutes
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
}

