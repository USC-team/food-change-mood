package org.example.domain.usecase
import domain.model.Meal
import org.example.domain.model.CorrectHighLow
import org.example.domain.repository.domain.repository.MealsRepository
import org.example.domain.usecase.model.MealException

class GetGuessGameUseCase(private val repo: MealsRepository) {
    fun getRandomMeal(): Meal {
        return repo.getAllMeals().filter(::getNotNullMeals).randomOrNull()
            ?:throw MealException("Meal Not Found")
    }

    fun isGuessCorrectHighOrLow(meal: Meal, guessMinutes: Int): CorrectHighLow {
        return if( meal.minutes == guessMinutes)CorrectHighLow.Correct
                else if(meal.minutes!! > guessMinutes) CorrectHighLow.Too_Low
                else CorrectHighLow.Too_High
    }

    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.minutes != null
    }
}

