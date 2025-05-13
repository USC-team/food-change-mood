package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.domain.repository.MealsRepository

class GetHealthyMealsUseCase (private val repo : MealsRepository){

    fun  getAllHealthyMeals () :List<Meal>{
        val allMeals = repo.getAllMeals().filter { it.minutes != null && it.nutrition != null }
        val  totalFat = allMeals.mapNotNull { it.nutrition?.totalFat }.average()
        val  carbohydrates = allMeals.mapNotNull { it.nutrition?.carbohydrates }.average()
        val  saturatedFat = allMeals.mapNotNull { it.nutrition?.saturatedFat }.average()
        val result=  allMeals.filter {
            it.minutes!! <= 15 &&
                     it.nutrition?.totalFat!! < totalFat &&
                         it.nutrition.saturatedFat!! < saturatedFat &&
                             it.nutrition.carbohydrates!! < carbohydrates
        }
        return result

    }

}