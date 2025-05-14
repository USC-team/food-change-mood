package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository
import kotlin.random.Random

class GetEasyPreparedMealsUseCase(private val repo: MealsRepository) {

    fun getEasyPreparedMeals(): List<Meal> {
        return repo.getAllMeals().filter { meal ->
            (meal.minutes ?: Int.MAX_VALUE) <= Constants.REQUIRED_MINUTES_FOR_EASY_PREPARE &&
                    (meal.nIngredients ?: Int.MAX_VALUE) <= Constants.REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE &&
                    (meal.nSteps ?: Int.MAX_VALUE) <= Constants.REQUIRED_NSTEPS_FOR_EASY_PREPARE
        }
            .takeRandom(Constants.TEN_RANDOM_MEALS)
            .toList()
    }


    fun List<Meal>.takeRandom(count: Int): List<Meal> {
        if (isEmpty() || count <= 0) return emptyList()

        val result = mutableSetOf<Meal>()
        val random = Random.Default
        var attempts = 0
        val maxAttempts = count * 2  // Prevent infinite loops

        while (result.size < count && attempts < maxAttempts) {
            val randomMeal = this[random.nextInt(size)]
            if (result.add(randomMeal)) {  // Set.add() returns true if new element
                attempts = 0  // Reset attempts counter on successful add
            } else {
                attempts++
            }
        }

        return result.toList()
    }

    private object Constants {
        const val REQUIRED_MINUTES_FOR_EASY_PREPARE = 30
        const val REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE = 5
        const val REQUIRED_NSTEPS_FOR_EASY_PREPARE = 6
        const val TEN_RANDOM_MEALS = 10
    }
}