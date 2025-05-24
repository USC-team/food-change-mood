package domain.usecase.exceptions

class MealNotFoundExceptions : Exception("No Meal Found")
class InvalidChoiceException : Exception("Choice is invalid")
class IncorrectGuessException : Exception("Three tries of incorrect guesses")
