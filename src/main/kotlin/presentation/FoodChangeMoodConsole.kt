package presentation

import org.example.presentation.choices.IChoicesManager
import org.example.presentation.console_io.IMessagePrinter
import org.example.presentation.console_io.ReadManager

class FoodChangeMoodConsole(private val iChoicesManager: IChoicesManager,
                            private val iMessagePrinter: IMessagePrinter,
                            private val iReadManager: ReadManager){

    fun start() {
        iMessagePrinter.greet()
        chooseOption()
    }

    fun chooseOption() {
        iMessagePrinter.showOptions()
        iMessagePrinter.askUserToEnter("Choice")
        val input= iReadManager.myReadInt()
        iChoicesManager.chooseOption(input)
        if (input!=0) chooseOption()
    }
}