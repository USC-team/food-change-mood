package org.example.domain.usecase
import domain.model.Meal
import org.example.domain.model.GuessResult
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.model.MealNotFoundExceptions


class GetGuessGameUseCase(private val repo: MealsRepository) {
    fun getRandomMeal(): Meal {
        return repo.getAllMeals().filter(::getNotNullMeals).randomOrNull()
            ?:throw MealNotFoundExceptions()
    }

    fun isGuessCorrectHighOrLow(meal: Meal, guessMinutes: Int): GuessResult {
        return if( meal.minutes == guessMinutes)GuessResult.Correct
                else if(meal.minutes!! > guessMinutes) GuessResult.Too_Low
                else GuessResult.Too_High
    }

    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.minutes != null
    }
}

