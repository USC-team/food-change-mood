package presentation

import org.example.presentation.choices.ChoicesManager
import org.example.presentation.console_io.MessagePrinter
import org.example.presentation.console_io.ReadManager

class FoodChangeMoodConsole(private val choicesManager: ChoicesManager,
                            private val messagePrinter: MessagePrinter,
                            private val readManager: ReadManager){

    fun start() {
        messagePrinter.greet()
        chooseOption()
    }

    fun chooseOption() {
        messagePrinter.showOptions()
        messagePrinter.askUserToEnter("Choice")
        val input= readManager.readInt()
        choicesManager.chooseOption(input)
        if (input!=0) chooseOption()
    }
}