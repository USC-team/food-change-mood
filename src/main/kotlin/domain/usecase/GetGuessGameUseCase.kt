package domain.usecase
import domain.model.Meal
import domain.model.GuessResult
import domain.repository.MealsRepository
import domain.usecase.exceptions.IncorrectGuessException
import domain.usecase.exceptions.MealNotFoundExceptions


class GetGuessGameUseCase(private val repo: MealsRepository) {
    fun getRandomMeal(): Meal {
        return repo.getAllMeals().filter(::getNotNullMeals).randomOrNull()
            ?:throw MealNotFoundExceptions()
    }

    fun isGuessCorrectHighOrLow(meal: Meal, guessMinutes: Int, numberOfTry:Int=1): GuessResult {
        return if(numberOfTry<=TRIES && meal.minutes == guessMinutes)GuessResult.Correct
                else if(numberOfTry<=TRIES && meal.minutes!! > guessMinutes) GuessResult.TooLow
                else if(numberOfTry<=TRIES && meal.minutes!! < guessMinutes) GuessResult.TooHigh
                else throw IncorrectGuessException()
    }

    private fun getNotNullMeals(meal: Meal): Boolean {
        return meal.minutes != null
    }
    companion object{
        const val TRIES=3
    }
}

