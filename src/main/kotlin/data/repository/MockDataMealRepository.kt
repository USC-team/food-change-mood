package data.repository

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import data.repository.CsvParsers.parseToMeal
import domain.model.Meal
import domain.repository.MealsRepository
import java.io.File

class MockDataMealRepository(private val csvFile: File) : MealsRepository {
    private val reader = csvReader {
        delimiter = ','
        quoteChar = '"'
        skipEmptyLine = true
        skipMissMatchedRow = true
    }

    override fun getAllMeals(): List<Meal> {
        return reader
            .readAllWithHeader(csvFile)
            .map(::parseToMeal)
    }
}