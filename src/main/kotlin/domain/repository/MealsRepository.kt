package org.example.domain.repository

import domain.model.Meal

interface MealsRepository {
    fun getAllMeals(): List<Meal>
}