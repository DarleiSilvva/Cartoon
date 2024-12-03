package vm.caatsoft.cartoon.controller

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import vm.caatsoft.cartoon.graphql.GetCharacterQuery
import vm.caatsoft.cartoon.model.GraphQLClient

class MainControllerTest {

    @Mock
    private lateinit var graphQLClient: GraphQLClient

    @Mock
    private lateinit var view: MainController.ViewContract

    private lateinit var controller: MainController

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        controller = MainController(graphQLClient, view)
    }

    @Test
    fun `fetchRandomCharacter should show character when API call is successful`() = runTest(testDispatcher) {
        // Given: A random character ID and a successful API response
        val character = GetCharacterQuery.Character(
            "1", "Character Name", "Human", "Alive", "Male", "https://image.url"
        )
        `when`(graphQLClient.getCharacter(anyString())).thenReturn(character)

        // When: fetchRandomCharacter is called
        controller.fetchRandomCharacter()

        // Then: The view should display the character
        verify(view).showLoading()
        verify(view).showCharacter(character)
        verify(view).hideLoading()
        verifyNoMoreInteractions(view)

    }

    @Test
    fun `fetchRandomCharacter should show error when character not found`() = runTest(testDispatcher) {
        // Given: A random character ID and a null API response
        `when`(graphQLClient.getCharacter(anyString())).thenReturn(null)

        // When: fetchRandomCharacter is called
        controller.fetchRandomCharacter()

        // Then: The view should display an error message
        verify(view).showLoading()
        verify(view).showError("Character not found!")
        verify(view).hideLoading()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `fetchRandomCharacter should show error when API call fails`() = runTest(testDispatcher) {
        // Given: A random character ID and an exception thrown by the API
        `when`(graphQLClient.getCharacter(anyString())).thenThrow(RuntimeException("Network error"))

        // When: fetchRandomCharacter is called
        controller.fetchRandomCharacter()

        // Then: The view should display an error message
        verify(view).showLoading()
        verify(view).showError("Error fetching data: Network error")
        verify(view).hideLoading()
        verifyNoMoreInteractions(view)
    }
}

