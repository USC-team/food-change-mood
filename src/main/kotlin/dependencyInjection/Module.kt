package dependencyInjection


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import domain.repository.MealsRepository
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import data.repository.MealsRepositoryImplementation
import org.koin.dsl.module
import presentation.FoodChangeMoodConsole
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single<CsvReader> {
        csvReader {
            delimiter = ','
            quoteChar = '"'
            skipEmptyLine = true
            skipMissMatchedRow = true
        }
    }
    single<MealsRepository> { MealsRepositoryImplementation(get(), get()) }
    single { GetGuessGameUseCase(get()) }
    single { GetSweetsWithNoEggsUseCase(get()) }
    single { GetEasyPreparedMealsUseCase(get()) }
    single { GetKetoMealUseCase(get()) }
    single { FoodChangeMoodConsole(get(), get(), get(), get()) }
}