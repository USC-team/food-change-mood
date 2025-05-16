package org.example.domain.usecase

import org.example.domain.repository.MealsRepository

class GetMealsUseCase(private val repo: MealsRepository) {

    fun getAllMeals() = repo.getAllMeals()
}
