package dependencyInjection


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import domain.repository.MealsRepository
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import data.repository.MealsRepositoryImplementation
import org.example.presentation.choices.ChoicesManager
import org.example.presentation.choices.IChoicesManager
import org.example.presentation.console_io.IMessagePrinter
import org.example.presentation.console_io.IReadManager
import org.example.presentation.console_io.MessagesPrinter
import org.example.presentation.console_io.ReadManager
import presentation.FoodChangeMoodConsole
import org.koin.dsl.module
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
    single<IReadManager> { ReadManager }
    single<IMessagePrinter>{ MessagesPrinter }
    single<IChoicesManager> { ChoicesManager(get(), get(), get(), get(), get(), get()) }
    single { FoodChangeMoodConsole(get(), get(), get()) }
}