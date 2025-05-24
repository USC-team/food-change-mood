package data.repository

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import domain.model.Meal
import domain.repository.MealsRepository
import java.io.File

class MealsRepositoryImplementation(
    private val csvReader: CsvReader,
    private val csvFile: File
) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return csvReader
            .readAllWithHeader(csvFile)
            .map(CsvParsers::parseToMeal)
    }
}