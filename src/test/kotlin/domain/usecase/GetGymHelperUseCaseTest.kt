package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.usecase.exceptions.MealNotFoundExceptions
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetGymHelperUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetGymHelperUseCaseTest {
    private val repository: MealsRepository = mockk(relaxed = true)
    private lateinit var useCase: GetGymHelperUseCase

    @BeforeEach
    fun setup() {
        useCase = GetGymHelperUseCase(repository)
    }
    @Test
    fun `should throw MealNotFoundExceptions when the meal list os empty`(){
        //Given
        every { repository.getAllMeals() } returns emptyList()
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when meals in the list have null Nutrition`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nIngredients = 3, nSteps = 2),
            createMeal(id = 2, name = "Pizza", nIngredients = 5, nSteps = 6))
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when nutrition of meals have null calories`(){
        //Given
        val nutrition= createNutrition(calories = null, protein = 5.0)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =nutrition))
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when nutrition of meals have null protein`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = null)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =nutrition))
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }

    @Test
    fun `should throw MealNotFoundExceptions when no meals match the criteria`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =nutrition))
        val protein=10.0
        val calories=10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when protein entered is negative`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =nutrition))
        val protein=-10.0
        val calories=10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when calories entered is negative`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =nutrition))
        val protein=-10.0
        val calories=-10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should return the list when all meals meet the criteria`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        val meal=createMeal(id = 1, name = "Salad", nutrition =nutrition)
        every { repository.getAllMeals() } returns listOf(meal)
        val protein=5.0
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).contains(meal)
    }
    @Test
    fun `should return only the meals that meet the criteria when list contains some that far away from the criteria`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        val nutritionNotMatching= createNutrition(calories = 10.0, protein = 10.0)
        val meal=createMeal(id = 1, name = "Salad", nutrition =nutrition)
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =nutritionNotMatching)
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val protein=5.0
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
    @Test
    fun `should return the meals that are close to protein value when list contains multiple meals`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        val nutritionNotMatching= createNutrition(calories = 10.0, protein = 10.0)
        val meal=createMeal(id = 1, name = "Salad", nutrition =nutrition)
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =nutritionNotMatching)
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val protein=5.2
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
    @Test
    fun `should return the meals that are close to calories value when list contains multiple meals`(){
        //Given
        val nutrition= createNutrition(calories = 5.0, protein = 5.0)
        val nutritionNotMatching= createNutrition(calories = 10.0, protein = 10.0)
        val meal=createMeal(id = 1, name = "Salad", nutrition =nutrition)
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =nutritionNotMatching)
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val protein=5.0
        val calories=5.2
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
}