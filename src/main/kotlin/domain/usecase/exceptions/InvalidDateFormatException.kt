package domain.usecase.exceptions

import java.time.LocalDate

class InvalidDateFormatException(input: String)
    : Exception("Date '$input' is not in the format yyyy-MM-dd.")

class NoMealsFoundException(date: LocalDate)
    : Exception("No meals found for date $date.")

class MealNotFoundExceptions : Exception("No Meal Found")