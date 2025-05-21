package domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository
import kotlin.random.Random

class GetEasyPreparedMealsUseCase(private val repo: MealsRepository) {

    fun getEasyPreparedMeals(): List<Meal> {
        return repo.getAllMeals().filter(::isEasyPrepared)
            .takeRandom()
            .toList()
    }

    private fun isEasyPrepared(meal: Meal): Boolean {
        return (meal.minutes ?: Int.MAX_VALUE) in 0..REQUIRED_MINUTES_FOR_EASY_PREPARE &&
                (meal.nIngredients ?: Int.MAX_VALUE) in 0..REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE &&
                (meal.nSteps ?: Int.MAX_VALUE) in 0..REQUIRED_NSTEPS_FOR_EASY_PREPARE
    }

    private fun List<Meal>.takeRandom(): List<Meal> {
        if (isEmpty()) return emptyList()

        val result = mutableSetOf<Meal>()
        val random = Random.Default
        var attempts = 0
        val maxAttempts = TEN_RANDOM_MEALS * 2

        while (result.size < TEN_RANDOM_MEALS && attempts < maxAttempts) {
            val randomMeal = this[random.nextInt(size)]
            if (result.add(randomMeal)) {
                attempts = 0
            } else {
                attempts++
            }
        }

        return result.toList()
    }

    private companion object Constants {
        const val REQUIRED_MINUTES_FOR_EASY_PREPARE = 30
        const val REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE = 5
        const val REQUIRED_NSTEPS_FOR_EASY_PREPARE = 6
        const val TEN_RANDOM_MEALS = 10
    }
}