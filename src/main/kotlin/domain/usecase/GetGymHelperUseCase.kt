package domain.usecase

import domain.model.Meal
import domain.repository.MealsRepository
import domain.usecase.exceptions.MealNotFoundExceptions
import kotlin.math.absoluteValue

class GetGymHelperUseCase(private val repo: MealsRepository) {
    fun gymHelper(calories : Double, protein: Double ): List<Meal>{
        if(calories<0||protein<0)
            throw MealNotFoundExceptions()
        return repo.getAllMeals().filter { meal ->
           approximateProteinAndCalories(meal,calories,protein)
        }.takeIf { it.isNotEmpty() }?:throw MealNotFoundExceptions()
    }
    private fun approximateProteinAndCalories(meal: Meal, calories : Double, protein: Double ): Boolean{
        val nutrition = meal.nutrition
        return nutrition != null &&
                nutrition.protein != null &&
                nutrition.calories != null &&
                (nutrition.protein - protein).absoluteValue <= TOLERANCE &&
                (nutrition.calories - calories).absoluteValue <= TOLERANCE
    }
    private companion object{
        const val TOLERANCE=1.0
    }
}