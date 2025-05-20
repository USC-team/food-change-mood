package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository

class GetSpecialIraqMealsUseCae (private val repo: MealsRepository){
    companion object{
        val IRAQ ="iraq"
        val IRAQI ="iraqi"
    }

    fun getSpecialIraqMeals():List<Meal>{
        return repo.getAllMeals()
            .filter {isIraqiMeal(it) && isEmptyTagAndDescriptionOrMeal(it)}
    }

     fun isIraqiMeal(meal : Meal) : Boolean =
        meal.tags?.any{it.contains(IRAQ,ignoreCase = true)} == true || meal.description?.contains(IRAQI,ignoreCase = true) == true


   public  fun isEmptyTagAndDescriptionOrMeal(meal : Meal) : Boolean =
        meal.tags?.isNotEmpty() == true || meal.description?.isNotEmpty() == true
}