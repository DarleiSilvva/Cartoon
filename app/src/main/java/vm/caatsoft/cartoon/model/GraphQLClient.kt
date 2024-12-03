package vm.caatsoft.cartoon.model

import com.apollographql.apollo3.ApolloClient
import vm.caatsoft.cartoon.BuildConfig
import vm.caatsoft.cartoon.graphql.GetCharacterQuery
import javax.inject.Inject

class GraphQLClient @Inject constructor() {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl(BuildConfig.GRAPHQL_BASE_URL)
        .build()

    suspend fun getCharacter(id: String): GetCharacterQuery.Character? {
        val response = apolloClient.query(GetCharacterQuery(id)).execute()
        return response.data?.character
    }
}
