package org.example.domain.usecase

import domain.model.Meal
import org.example.data.repository.data.repository.MockDataMealRepository

class GetSpecialIraqMealsUseCae (private val repo: MockDataMealRepository){
    fun getSpecialIraqMeals(country : String):List<Meal>{
        val allMeals = repo.getAllMeals().filter { it.name != null || it.description !=null}
        val iraqMeals = allMeals.filter { it.name?.contains(country) == true || it.description?.contains(country) == true }
        return iraqMeals
    }
}