package org.example.domain.usecase

import domain.model.Meal
import org.example.domain.repository.MealsRepository

class GetGymHelperUseCase(private val repo: MealsRepository) {
    fun gymHelper(calories : Double, protein: Double ): List<Meal>{
        return emptyList()
    }
}