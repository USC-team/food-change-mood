package dependencyInjection

import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetKetoMealUseCase
import data.repository.MockDataMealRepository
import domain.repository.MealsRepository
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import presentation.FoodChangeMoodConsole
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single<MealsRepository> { MockDataMealRepository(get()) }
    single { GetGuessGameUseCase(get()) }
    single { GetSweetsWithNoEggsUseCase(get()) }
    single { GetEasyPreparedMealsUseCase(get()) }
    single { GetKetoMealUseCase(get()) }
    single { FoodChangeMoodConsole(get(), get(), get(), get()) }
}