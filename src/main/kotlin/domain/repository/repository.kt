package domain.repository

import domain.model.Meal

interface FoodRepository {
    fun getAllMeals(): List<Meal>
}
