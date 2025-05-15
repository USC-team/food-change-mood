package org.example.domain.usecase

import domain.model.Meal
import domain.usecase.exceptions.InvalidDateFormatException
import domain.usecase.exceptions.NoMealsFoundException
import org.example.domain.repository.MealsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class SearchMealsByDateUseCase(private val repo: MealsRepository) {
    private val formattedDate = DateTimeFormatter.ISO_LOCAL_DATE

    fun searchMealOn(dateString: String): List<Meal> {
        val date = try {
            LocalDate.parse(dateString, formattedDate)
        } catch (e: DateTimeParseException) {
            throw InvalidDateFormatException(dateString)
        }

        val matches = repo.getAllMeals().filter { meal ->
            (meal.submitted ?: "") == date.toString()
        }
        if (matches.isEmpty()) throw NoMealsFoundException(date)
        return matches
    }
}