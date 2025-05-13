package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.domain.repository.MealsRepository

class GetEasyPreparedMealsUseCase(private val repo: MealsRepository) {

    fun getEasyPreparedMeals(): List<Meal> {
        return repo.getAllMeals().filter { meal ->
            (meal.minutes ?: Int.MAX_VALUE) <= 30 &&
                    (meal.nIngredients ?: Int.MAX_VALUE) <= 5 &&
                    (meal.nSteps ?: Int.MAX_VALUE) <= 6
        }.shuffled().take(10)
    }
}