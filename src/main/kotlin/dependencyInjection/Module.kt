package dependencyInjection


import data.repository.MockDataMealRepository
import domain.repository.MealsRepository
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsole
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