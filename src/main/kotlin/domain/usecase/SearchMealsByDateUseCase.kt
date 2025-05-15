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
        return runCatching {
            LocalDate.parse(dateString, formattedDate)
        }.getOrElse { throw InvalidDateFormatException(dateString) }
            .let { parsedDate ->
                repo.getAllMeals()
                    .filter { it.submitted == parsedDate.toString() }
                    .takeIf { it.isNotEmpty() }
                    ?: throw NoMealsFoundException(parsedDate)
            }
    }
}