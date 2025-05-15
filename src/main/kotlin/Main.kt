package org.example
import org.example.presentation.FoodChangeMoodConsole
import org.example.dependencyInjection.appModule
import org.example.domain.usecase.SearchMealsByDateUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
    modules(appModule)
    }

    val programConsole: FoodChangeMoodConsole = getKoin().get()
    programConsole.start()

    val searchUseCase= SearchMealsByDateUseCase(getKoin().get())
    searchUseCase.searchMealOn("2002-06-17").forEach {
        print(it)
    }
}