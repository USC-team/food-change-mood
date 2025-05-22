package domain.usecase

import domain.repository.MealsRepository

class GetMealsUseCase(private val repo: MealsRepository) {

    fun getAllMeals() = repo.getAllMeals()
}
