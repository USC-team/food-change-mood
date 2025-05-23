package org.example.presentation.console_io

import domain.model.Meal

interface MessagePrinter {
    fun sayGoodBye()
    fun explainChoice(choice:Int)
    fun printMealName(meal: Meal)
    fun wrongGuessMessage(meal: Meal)
    fun askUserToEnter(label: String)
    fun successMessage()
    fun printMessage(message:String)
    fun askUserIfLikedMeal()
    fun showMealsDetails(meal:Meal)
    fun greet()
    fun showOptions()
}