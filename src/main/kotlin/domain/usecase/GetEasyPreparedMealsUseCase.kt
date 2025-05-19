package domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository
import kotlin.random.Random

class GetEasyPreparedMealsUseCase(private val repo: MealsRepository) {

    fun getEasyPreparedMeals(): List<Meal> {
        return repo.getAllMeals().filter(::isEasyPrepared)
            .takeRandom(TEN_RANDOM_MEALS)
            .toList()
    }

    private fun isEasyPrepared(meal: Meal): Boolean {
        return (meal.minutes ?: Int.MAX_VALUE) <= REQUIRED_MINUTES_FOR_EASY_PREPARE &&
                (meal.nIngredients ?: Int.MAX_VALUE) <= REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE &&
                (meal.nSteps ?: Int.MAX_VALUE) <= REQUIRED_NSTEPS_FOR_EASY_PREPARE
    }

    private fun List<Meal>.takeRandom(count: Int): List<Meal> {
        if (isEmpty() || count <= 0) return emptyList()

        val result = mutableSetOf<Meal>()
        val random = Random.Default
        var attempts = 0
        val maxAttempts = count * 2

        while (result.size < count && attempts < maxAttempts) {
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
        private const val REQUIRED_MINUTES_FOR_EASY_PREPARE = 30
        private const val REQUIRED_NINGREDIENTS_FOR_EASY_PREPARE = 5
        private const val REQUIRED_NSTEPS_FOR_EASY_PREPARE = 6
        private const val TEN_RANDOM_MEALS = 10
    }
}