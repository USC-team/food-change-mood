package org.example.data.repository

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import data.repository.CsvParsers
import data.repository.CsvParsers.parseToMeal
import domain.model.Meal
import domain.model.Nutrition
import org.example.domain.repository.MealsRepository
import java.io.File
import javax.swing.text.html.parser.Parser

class MockDataMealRepository(csvFileParser: CsvParsers) : MealsRepository {
    private val reader = csvReader {
        delimiter = ','
        quoteChar = '"'              // respect quoted fields
        skipEmptyLine = true             // drops totally blank lines
        skipMissMatchedRow = true            // drops any row with wrong # of columns
    }
    override fun getAllMeals(): List<Meal> {
        return reader
            .readAllWithHeader(File("food.csv"))
            .map(::parseToMeal)
    }
}