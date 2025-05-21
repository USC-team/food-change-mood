package domain.usecase

import domain.model.Meal
import domain.model.Nutrition

fun createMeal(
    id: Int,
    name: String,
    nutrition: Nutrition
) = Meal(
    id = id,
    name = name,
    nutrition = nutrition,
    nSteps = null,
    submitted = null,
    tags = null,
    minutes = null,
    nIngredients = null,
    steps = null,
    description = null,
    ingredients = null,
    contributorId = null
)

fun createNutrition(
    carbohydrates: Double,
    protein: Double
) = Nutrition(
    carbohydrates = carbohydrates,
    protein = protein,
    sugar = null,
    sodium = null,
    calories = null,
    totalFat = null,
    saturatedFat = null
)

fun createMeal(
    id: Int,
    name: String,
    minutes: Int,
    nIngredients: Int,
    nSteps: Int
) = Meal(
    id = id,
    name = name,
    nutrition = null,
    nSteps = nSteps,
    submitted = null,
    tags = null,
    minutes = minutes,
    nIngredients = nIngredients,
    steps = null,
    description = null,
    ingredients = null,
    contributorId = null
)
fun createMeal(
    id: Int,
    name: String,
    ingredients: List<String>
) = Meal(
    id = id,
    name = name,
    nutrition = null,
    nSteps = null,
    submitted = null,
    tags = null,
    minutes = null,
    nIngredients = null,
    steps = null,
    description = null,
    ingredients = ingredients,
    contributorId = null
)
fun createMeal(
    id: Int,
    name: String
) = Meal(
    id = id,
    name = name,
    nutrition = null,
    nSteps = null,
    submitted = null,
    tags = null,
    minutes = null,
    nIngredients = null,
    steps = null,
    description = null,
    ingredients = null,
    contributorId = null
)