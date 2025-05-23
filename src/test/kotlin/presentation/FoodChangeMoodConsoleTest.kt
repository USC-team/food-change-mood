package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.example.presentation.choices.ChoicesManager
import org.example.presentation.console_io.MessagePrinter
import org.example.presentation.console_io.ConsoleReadManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoodChangeMoodConsoleTest {

    private val choicesManager: ChoicesManager = mockk(relaxed = true)
    private val messagePrinter: MessagePrinter = mockk(relaxed = true)
    private val readManager: ConsoleReadManager = mockk(relaxed = true)
    private lateinit var foodChangeMoodConsole: FoodChangeMoodConsole

    @BeforeEach
    fun setUp() {
        foodChangeMoodConsole = FoodChangeMoodConsole(choicesManager, messagePrinter, readManager)
    }

    @Test
    fun `should greet and show options on start`() {
        every { readManager.readInt() } returns 0

        foodChangeMoodConsole.start()

        verify { messagePrinter.greet() }
        verify { messagePrinter.showOptions() }
        verify { messagePrinter.askUserToEnter("Choice") }
        verify { readManager.readInt() }
        verify { choicesManager.chooseOption(0) }
    }

    @Test
    fun `should keep prompting until user enters 0`() {
        every { readManager.readInt() } returnsMany listOf(4, 5, 0)

        foodChangeMoodConsole.start()

        verify { messagePrinter.greet() }
        verify(exactly = 3) { messagePrinter.showOptions() }
        verify(exactly = 3) { messagePrinter.askUserToEnter("Choice") }
        verifySequence {
            choicesManager.chooseOption(4)
            choicesManager.chooseOption(5)
            choicesManager.chooseOption(0)
        }
    }

    @Test
    fun `should call chooseOption with the correct input`() {
        every { readManager.readInt() } returns 7

        foodChangeMoodConsole.chooseOption()

        verify { choicesManager.chooseOption(7) }
    }
}
