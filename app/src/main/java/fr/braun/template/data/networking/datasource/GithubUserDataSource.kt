package fr.braun.template.data.networking.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.braun.template.data.model.GithubUser
import fr.braun.template.data.networking.api.GitHubApi
import fr.braun.template.extension.runWithThrows
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GithubUserDataSource(
    private val githubAPI: GitHubApi,
    private val scope: CoroutineScope,
    private val accessToken: String
) : PageKeyedDataSource<Int, GithubUser>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubUser>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = githubAPI.getGithubUsers(
                        accessToken = accessToken,
                        firstPage = FIRST_PAGE,
                        userPerPage = USER_PER_PAGE
                    )
                    .runWithThrows()
                if (params.placeholdersEnabled) {
                    callback.onResult(response,
                        0, MAX_PAGE,
                        null,
                        response.last().userID)
                } else {
                    callback.onResult(
                        response,
                        null,
                        response.last().userID)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = githubAPI.getGithubUsers(
                        accessToken = accessToken,
                        firstPage = params.key,
                        userPerPage = USER_PER_PAGE
                    )
                    .runWithThrows()
                callback.onResult(response, response.last().userID)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) = Unit

    class Factory(
        private val api: GitHubApi,
        private val scope: CoroutineScope,
        private val accessToken: String
    ) : DataSource.Factory<Int, GithubUser>() {
        override fun create(): DataSource<Int, GithubUser> =
            GithubUserDataSource(api, scope, accessToken)
    }


    companion object PageInfo {
        private const val FIRST_PAGE = 0
        private const val USER_PER_PAGE = 20
        private const val MAX_PAGE = 9999999
    }
}


