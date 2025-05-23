package org.example.presentation.choices

import domain.model.GuessResult
import domain.model.Meal
import domain.usecase.GetEasyPreparedMealsUseCase
import domain.usecase.GetGuessGameUseCase
import domain.usecase.GetKetoMealUseCase
import domain.usecase.GetSweetsWithNoEggsUseCase
import domain.usecase.exceptions.InvalidChoiceException
import domain.usecase.exceptions.MealNotFoundExceptions
import org.example.presentation.choices.IChoicesManager
import org.example.presentation.console_io.IMessagePrinter
import org.example.presentation.console_io.IReadManager

class ChoicesManager(private val getGuessGameUseCase: GetGuessGameUseCase,
                     private val getSweetsWithNoEggsUseCase: GetSweetsWithNoEggsUseCase,
                     private val getEasyPreparedMealsUseCase: GetEasyPreparedMealsUseCase,
                     private val getKetoMealUseCase: GetKetoMealUseCase,
                     private val iMessagesPrinter: IMessagePrinter,
                     private val iReadManager: IReadManager
): IChoicesManager {


    override fun chooseOption(input: Int) {
        when (input) {
            0 -> {
                iMessagesPrinter.sayGoodBye()
                return
            }
            4 -> {
                iMessagesPrinter.explainChoice(4)
                easyPrepareMeals()
            }

            5 -> {
                iMessagesPrinter.explainChoice(4)
                guessGame()
            }
            6 -> {
                iMessagesPrinter.explainChoice(6)
                noEggsSweet()
            }
            7 -> {
                iMessagesPrinter.explainChoice(7)
                getKetoMeal()
            }
            else -> {
                throw InvalidChoiceException()
            }
        }

    }
    private fun easyPrepareMeals() {
        getEasyPreparedMealsUseCase.getEasyPreparedMeals().takeIf { it.isNotEmpty() }?.forEach{
            iMessagesPrinter.printMealName(it)
        }?:throw MealNotFoundExceptions()
    }

    private fun guessGame(){
        val meal= getGuessGameUseCase.getRandomMeal()
        iMessagesPrinter.printMealName(meal)
       if(!isCorrectGuess(meal)){
           iMessagesPrinter.wrongGuessMessage(meal)
       }
    }
    private fun isCorrectGuess(meal: Meal, tries: Int=3): Boolean {
       if (tries > 0) {
            iMessagesPrinter.askUserToEnter("Guess Minutes")
            val guessResult = getGuessGameUseCase.isGuessCorrectHighOrLow(meal, iReadManager.myReadInt())
            when (guessResult) {
                GuessResult.Correct -> {
                   iMessagesPrinter.successMessage()
                    return true
                }
                GuessResult.TooHigh -> {
                    iMessagesPrinter.printMessage("You are Wrong! It's too high!")
                    isCorrectGuess(meal,tries - 1)
                }

                GuessResult.TooLow -> {
                    iMessagesPrinter.printMessage("You are Wrong! It's too low!")
                    isCorrectGuess(meal,tries - 1)
                }
            }
        }
        return false
    }
    private fun noEggsSweet(){
        val meal = getSweetsWithNoEggsUseCase.getMealHasNoEggs()
        iMessagesPrinter.printMealName(meal)
        iMessagesPrinter.askUserIfLikedMeal()
        if(didYouLikeIt(iReadManager.myReadLine())) iMessagesPrinter.showMealsDetails(meal)
        else noEggsSweet()
    }
    private fun didYouLikeIt(input:String):Boolean {
        if(input.equals("y", ignoreCase = true))
            return true
        return false
    }
    private fun getKetoMeal(){
        val meal = getKetoMealUseCase.getKetoMeal()
        iMessagesPrinter.printMealName(meal)
        iMessagesPrinter.askUserIfLikedMeal()
        followKeto(iReadManager.myReadLine(), meal)
    }
    private fun followKeto(input:String, meal: Meal){
        if(didYouLikeIt(input)) iMessagesPrinter.showMealsDetails(meal)
        else getKetoMeal()
    }
}