package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.domain.repository.MealsRepository

class GetSpecialIraqMealsUseCae (private val repo: MealsRepository){
    companion object{
        val IRAQ ="iraq"
        val IRAQI ="iraqi"
    }

    fun getSpecialIraqMeals():List<Meal>{
        return repo.getAllMeals()
            .filter {isIraqiMeal(it) && isEmptyTagAndDescriptionOrMeal(it)}
    }

    private fun isIraqiMeal(meal : Meal) : Boolean =
        meal.tags?.contains(IRAQ) == true || meal.description?.contains(IRAQI) == true


    private fun isEmptyTagAndDescriptionOrMeal(meal : Meal) : Boolean =
        meal.tags?.isNotEmpty() == true || meal.description?.isNotEmpty() == true
}