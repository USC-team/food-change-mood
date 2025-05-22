package domain.usecase

import com.google.common.truth.Truth.assertThat
import domain.repository.MealsRepository
import domain.usecase.exceptions.MealNotFoundExceptions
import io.mockk.every
import io.mockk.mockk
import org.example.domain.usecase.GetGymHelperUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = null, protein = 5.0)))
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when nutrition of meals have null protein`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = null)))
        val protein=5.0
        val calories=5.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }

    @Test
    fun `should throw MealNotFoundExceptions when no meals match the criteria with greater values`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0)))
        val protein=10.0
        val calories=10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when no meals match the criteria with lower values`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0)))
        val protein=2.0
        val calories=2.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when protein entered is negative`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0)))
        val protein=-10.0
        val calories=10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should throw MealNotFoundExceptions when calories entered is negative`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0)))
        val protein=-10.0
        val calories=-10.0
        //When &Then
        assertThrows<MealNotFoundExceptions> { useCase.gymHelper(calories, protein) }
    }
    @Test
    fun `should return the list when all meals exactly meet the criteria`(){
        //Given
        val meal=createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0))
        every { repository.getAllMeals() } returns listOf(meal)
        val protein=5.0
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).contains(meal)
    }
    @Test
    fun `should return only the meals that exactly meet the criteria when list contains some that far away from the criteria`(){
        //Given
        val meal=createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0))
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =createNutrition("calories,protein",calories = 10.0, protein = 10.0))
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val protein=5.0
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
    @ParameterizedTest
    @ValueSource(doubles = [5.0, 5.2, 5.9,6.0,4.0,4.6])
    fun `should return the meals that are in tolerance value of protein when list contains multiple meals`(protein:Double){
        //Given
        val meal=createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0))
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =createNutrition("calories,protein",calories = 10.0, protein = 10.0))
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val calories=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
    @ParameterizedTest
    @ValueSource(doubles = [5.0, 5.2, 5.9,6.0,4.0,4.6])
    fun `should return the meals that are in tolerance value of calories when list contains multiple meals`(calories:Double){
        //Given
        val meal=createMeal(id = 1, name = "Salad", nutrition =createNutrition("calories,protein",calories = 5.0, protein = 5.0))
        val mealNotMatching=createMeal(id = 2, name = "Salad", nutrition =createNutrition("calories,protein",calories = 10.0, protein = 10.0))
        every { repository.getAllMeals() } returns listOf(meal,mealNotMatching)
        val protein=5.0
        //When
        val mealList=useCase.gymHelper(calories, protein)
        //Then
        assertThat(mealList).containsExactly(meal)
    }
    @Test
    fun `should return all meals that are within tolerance when multiple meals are found`() {
        // Given
        val matchingMeal1 = createMeal(id = 1, name = "Chicken", nutrition = createNutrition("calories,protein",calories = 5.1, protein = 4.9))
        val matchingMeal2 = createMeal(id = 2, name = "Fish", nutrition = createNutrition("calories,protein",calories = 4.8, protein = 5.2))
        every { repository.getAllMeals() } returns listOf(matchingMeal1, matchingMeal2)
        // When
        val result = useCase.gymHelper(5.0, 5.0)
        // Then
        assertThat(result).containsExactly(matchingMeal1, matchingMeal2)
    }
    @Test
    fun `should return meal when calories and protein are exactly at greater TOLERANCE difference`() {
        // Given
        val meal = createMeal(id = 1, name = "Tuna", nutrition = createNutrition("calories,protein",calories = 6.0, protein = 6.0))
        every { repository.getAllMeals() } returns listOf(meal)
        val targetCalories = 5.0
        val targetProtein = 5.0
        // When
        val result = useCase.gymHelper(targetCalories, targetProtein)
        // Then
        assertThat(result).containsExactly(meal)
    }
    @Test
    fun `should throw MealNotFoundExceptions when calories larger than TOLERANCE difference`() {
        // Given
        val meal = createMeal(id = 1, name = "Tuna", nutrition = createNutrition("calories,protein",calories = 6.0, protein = 6.0))
        every { repository.getAllMeals() } returns listOf(meal)
        val targetCalories = 7.2
        val targetProtein = 7.0
        // When &Then
        assertThrows<MealNotFoundExceptions>{useCase.gymHelper(targetCalories, targetProtein)}
    }
    @Test
    fun `should throw MealNotFoundExceptions when calories less than TOLERANCE difference`() {
        // Given
        val meal = createMeal(id = 1, name = "Tuna", nutrition = createNutrition("calories,protein",calories = 6.0, protein = 6.0))
        every { repository.getAllMeals() } returns listOf(meal)
        val targetCalories = 4.8
        val targetProtein = 7.0
        // When &Then
        assertThrows<MealNotFoundExceptions>{useCase.gymHelper(targetCalories, targetProtein)}
    }
}