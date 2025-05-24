package dependencyInjection


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import domain.repository.MealsRepository
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import data.repository.MealsRepositoryImplementation
import presentation.choices.ConsoleChoicesManager
import presentation.choices.ChoicesManager
import presentation.console_io.MessagePrinter
import presentation.console_io.ReadManager
import presentation.console_io.ConsoleMessagesPrinter
import presentation.console_io.ConsoleReadManager
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
    single<ReadManager> { ConsoleReadManager }
    single<MessagePrinter>{ ConsoleMessagesPrinter }
    single<ChoicesManager> { ConsoleChoicesManager(get(), get(), get(), get(), get(), get()) }
    single { FoodChangeMoodConsole(get(), get(), get()) }
}