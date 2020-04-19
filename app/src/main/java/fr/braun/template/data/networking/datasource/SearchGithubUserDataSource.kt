package fr.braun.template.data.networking.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.braun.template.data.model.GithubUser
import fr.braun.template.data.networking.api.GitHubApi
import fr.braun.template.extension.runWithThrows
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchGithubUserDataSource(
    private val githubAPI: GitHubApi,
    private val accessToken: String,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, GithubUser>() {

    private var pageCounter = FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubUser>
    ) {
        scope.launch(Dispatchers.IO) {
            val response = githubAPI.getSearchGithubUsers(
                accessToken = accessToken,
                query = query,
                firstPage = FIRST_PAGE,
                userPerPage = MAX_USER_PAGE
            ).runWithThrows()
            pageCounter++
            if (params.placeholdersEnabled) {
                callback.onResult(
                    response.results, 0,
                    getMaxPageCount(response.resultCount),
                    null,
                    pageCounter
                )
            } else {
                callback.onResult(response.results, null, pageCounter)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {
        scope.launch(Dispatchers.IO) {
            val response = githubAPI.getSearchGithubUsers(
                    accessToken = accessToken,
                    query = query,
                    firstPage = params.key,
                    userPerPage = MAX_USER_PAGE
                )
                .runWithThrows()
            pageCounter++
            callback.onResult(response.results, pageCounter)
        }
    }

    private fun getMaxPageCount(totalCount: Int) = (totalCount / MAX_USER_PAGE) + 1


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) = Unit
    class Factory(
        private val api: GitHubApi,
        private val query: String,
        private val accessToken: String,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, GithubUser>() {
        override fun create(): DataSource<Int, GithubUser> =
            SearchGithubUserDataSource(api, accessToken, query, scope)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val MAX_USER_PAGE = 20
    }
}
