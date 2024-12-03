package vm.caatsoft.cartoon.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vm.caatsoft.cartoon.model.GraphQLClient
import vm.caatsoft.cartoon.graphql.GetCharacterQuery
import javax.inject.Inject
import kotlin.random.Random

class MainController @Inject constructor(
    private val graphQLClient: GraphQLClient,
    private val view: ViewContract
) {

    interface ViewContract {
        fun showLoading()
        fun hideLoading()
        fun showCharacter(character: GetCharacterQuery.Character)
        fun showError(message: String)
    }

    suspend fun fetchRandomCharacter() {
        view.showLoading()
        val maximumCharacters = 826
        val minimumCharacters = 1
        val randomId = Random.nextInt(minimumCharacters, maximumCharacters).toString()
        try {
            val character = withContext(Dispatchers.IO) {
                graphQLClient.getCharacter(randomId)
            }
            character?.let {
                view.showCharacter(it)
            } ?: view.showError("Character not found!")
        } catch (e: Exception) {
            view.showError("Error fetching data: ${e.message}")
        } finally {
            view.hideLoading()
        }
    }
}
