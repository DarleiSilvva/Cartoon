package vm.caatsoft.cartoon.model

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GraphQLClientTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var client: GraphQLClient
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        // Set up MockWebServer and ApolloClient
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apolloClient = ApolloClient.Builder()
            .serverUrl(mockWebServer.url("/").toString())
            .build()

        client = GraphQLClient()
    }

    @Test
    fun `getCharacter returns data when query is successful`() {
        // Given: A valid response for the GetCharacterQuery
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "data": {
                        "character": {
                            "id": "1",
                            "name": "Rick Sanchez",
                            "species": "Human",
                            "status": "Alive",
                            "gender": "Male",
                            "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                        }
                    }
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        val expectedId = "1"
        val expectedName = "Rick Sanchez"
        val expectedSpecies = "Human"
        val expectedStatus = "Alive"
        val expectedGender = "Male"
        val expectedImage = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"

        // When: getCharacter is called
        val character = runBlocking { client.getCharacter(expectedId) }

        // Then: The returned character should match the expected data
        assertNotNull(character)
        assertEquals(expectedId, character?.id)
        assertEquals(expectedName, character?.name)
        assertEquals(expectedSpecies, character?.species)
        assertEquals(expectedStatus, character?.status)
        assertEquals(expectedGender, character?.gender)
        assertEquals(expectedImage, character?.image)
    }

    @Test
    fun `getCharacter returns null when query has errors`() {
        // Given: An error response for the GetCharacterQuery
        val errorResponse = MockResponse()
            .setResponseCode(400)
            .setBody("""
                {
                    "errors": [{
                        "message": "Invalid ID"
                    }]
                }
            """.trimIndent())
        mockWebServer.enqueue(errorResponse)

        val invalidId = "invalid-id"

        // When: getCharacter is called
        val character = runBlocking { client.getCharacter(invalidId) }

        // Then: The returned character should be null
        assertNull(character)
    }

    @After
    fun tearDown() {
        // Shut down the MockWebServer
        mockWebServer.shutdown()
    }
}
