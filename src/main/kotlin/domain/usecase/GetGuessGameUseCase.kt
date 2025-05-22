package domain.usecase
import domain.model.Meal
import domain.model.GuessResult
import domain.repository.MealsRepository
import domain.usecase.exceptions.MealNotFoundExceptions


class GetGuessGameUseCase(private val repo: MealsRepository) {
    fun getRandomMeal(): Meal {
        return repo.getAllMeals().filter(::getNotNullMeals).randomOrNull()
            ?:throw MealNotFoundExceptions()
    }

    fun isGuessCorrectHighOrLow(meal: Meal, guessMinutes: Int): GuessResult {
        return if( meal.minutes == guessMinutes)GuessResult.Correct
                else if(meal.minutes!! > guessMinutes) GuessResult.TooLow
                else GuessResult.TooHigh
    }

    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.minutes != null
    }
}

