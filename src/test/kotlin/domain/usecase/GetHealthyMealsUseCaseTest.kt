package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetHealthyMealsUseCase
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetHealthyMealsUseCaseTest {
    private val repository : MealsRepository = mockk(relaxed = true)
    private lateinit var healthyMealsUseCase : GetHealthyMealsUseCase

    @BeforeEach
     fun setup(){
        healthyMealsUseCase = GetHealthyMealsUseCase(repository)
    }
    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals empty`(){
        //Given
        every { repository.getAllMeals() } returns emptyList()
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()

    }


    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take more than 15 min and nutrition null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = OVER_MINUTE,
                nutrition = null
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take zero min and nutrition null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = OVER_MINUTE,
                nutrition = null
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take null min and nutrition null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = null,
                nutrition = null
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }



    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take equal 15 null min and nutrition null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = MINUTE,
                nutrition = null
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take less 15 null min and nutrition null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = MINUTE_LESS,
                nutrition = null
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take less 15 min and nutrition not null but totalFat over`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = MINUTE_LESS,
                nutrition = createNutrition(
                    totalFat = 80.0,
                    carbohydrates = null,
                    saturatedFat = null

                )
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }


    @Test
    fun `getHealthyQuickMealsBelowAverage should return empty list when meals take less 15 min and nutrition not null but carbohydrates over`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                minutes = MINUTE_LESS,
                nutrition = createNutrition(
                    totalFat = null,
                    carbohydrates = 80.0,
                    saturatedFat = null

                )
            )
        )
        //when
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        //Then
        assertThat(result).isEmpty()
    }


    @Test
    fun `getHealthyQuickMealsBelowAverage should return meal when all nutrition values are below average`() {
        //Given
        val mealHigh = createMeal(
            id = 1,
            name = "fatty",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 80.0,
                saturatedFat = 80.0,
                carbohydrates = 80.0
            )
        )

        val mealLow = createMeal(
            id = 2,
            name = "healthy",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 20.0,
                saturatedFat = 20.0,
                carbohydrates = 20.0
            )
        )
        every { repository.getAllMeals() } returns listOf(mealHigh, mealLow)

        //When
        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()

        //Then
        assertThat(result).containsExactly(mealLow)
    }



    @Test
    fun `getHealthyQuickMealsBelowAverage should return meal when only totalFat is below average`() {
        val mealHigh = createMeal(
            id = 1,
            name = "fatty",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 80.0,
                carbohydrates = null,
                saturatedFat = null
            )
        )

        val mealLow = createMeal(
            id = 2,
            name = "light meal",
            minutes = 10,
            nutrition = createNutrition(
                totalFat = 20.0,
                carbohydrates = null,
                saturatedFat = null
            )
        )

        every { repository.getAllMeals() } returns listOf(mealHigh, mealLow)

        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        assertThat(result).containsExactly(mealLow)
    }

    @Test
    fun `getHealthyQuickMealsBelowAverage should return meal when only carbohydrates is below average`() {
        val mealHigh = createMeal(
            id = 1,
            name = "carby",
            minutes = 10,
            nutrition = createNutrition(
                carbohydrates = 100.0,
                saturatedFat = null,
                totalFat = null,


                )
        )

        val mealLow = createMeal(
            id = 2,
            name = "low carb",
            minutes = 10,
            nutrition = createNutrition(
                carbohydrates = 30.0,
                saturatedFat = null,
                totalFat = null,

                )
        )

        every { repository.getAllMeals() } returns listOf(mealHigh, mealLow)

        val result = healthyMealsUseCase.getHealthyQuickMealsBelowAverage()
        assertThat(result).containsExactly(mealLow)
    }




    companion object {
        private const val MINUTE = 15
        private const val MINUTE_LESS = 10
        private const val OVER_MINUTE = 30
    }
}