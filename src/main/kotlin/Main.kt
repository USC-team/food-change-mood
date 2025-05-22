import presentation.FoodChangeMoodConsole
import dependencyInjection.appModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
    modules(appModule)
    }

    val programConsole: FoodChangeMoodConsole = getKoin().get()
    programConsole.start()
}