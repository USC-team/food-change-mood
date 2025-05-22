package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.repository.MealsRepository
import org.example.domain.usecase.GetSpecialIraqMealsUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class GetSpecialIraqMealsUseCaseTest {
    private val repository : MealsRepository = mockk(relaxed = true)
    private lateinit var getSpecialIraqMealsUseCase: GetSpecialIraqMealsUseCase

    @BeforeEach
    fun setup(){
        getSpecialIraqMealsUseCase = GetSpecialIraqMealsUseCase(repository)
    }

    @Test
    fun `getSpecialIraqMeals should return false when meals empty`(){
        //Given
        every { repository.getAllMeals() } returns emptyList()
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when all tags and descriptions of meals empty`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = emptyList(),
                description = ""
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when all tags and descriptions of meals null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = null,
                description = null
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when tags of meals null and descriptions not null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = null,
                description =ANY_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when descriptions of meals null and tags not null `(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(ANY_TAG),
                description = null
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when tags of meals not contain Iraq and descriptions empty`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(ANY_TAG),
                description = ""
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when descriptions of meals not contain Iraq and tags empty`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = emptyList(),
                description = ANY_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when tags of meals not contain Iraq and descriptions null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(IRAQ_TAG),
                description = null
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when descriptions of meals not contain Iraq and tags null`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = null,
                description = IRAQI_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return empty list when tags and descriptions of meals not contain Iraq or iraqi`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(ANY_TAG),
                description = ANY_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return list when tags and descriptions of meals not empty and correct tag and description`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(IRAQ_TAG),
                description = IRAQI_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return list when tags of meals not empty and correct tag`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(IRAQ_TAG),
                description = ""
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return list when descriptions of meals not empty and correct description`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = emptyList(),
                description = IRAQI_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()

    }

    @Test
    fun `getSpecialIraqMeals should return list when tags lower Case`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(IRAQ_TAG.lowercase()),
                description = ANY_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getSpecialIraqMeals should return list when descriptions lower Case`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(ANY_TAG),
                description = IRAQI_DESCRIPTION.lowercase()
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getSpecialIraqMeals should return list when tags Upper Case`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(IRAQ_TAG.uppercase()),
                description = ANY_DESCRIPTION
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getSpecialIraqMeals should return list when descriptions Upper Case`(){
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(
                id = 1,
                name = "pasta",
                tags = listOf<String>(ANY_TAG),
                description = IRAQI_DESCRIPTION.uppercase()
            )
        )
        //when
        val result = getSpecialIraqMealsUseCase.getSpecialIraqMeals()
        //Then
        assertThat(result).isNotEmpty()
    }


    companion object{
       private const  val IRAQ_TAG ="iraq"
       private const  val IRAQI_DESCRIPTION ="iraqi"
       private const val ANY_TAG ="egypt"
       private const val ANY_DESCRIPTION ="egypt"
    }
}