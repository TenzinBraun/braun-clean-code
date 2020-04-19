package fr.braun.template.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.braun.template.data.model.GithubUser
import fr.braun.template.data.networking.HttpClientManager
import fr.braun.template.data.networking.api.GitHubApi
import fr.braun.template.data.networking.createGithubApi
import fr.braun.template.data.networking.datasource.GithubUserDataSource
import fr.braun.template.data.networking.datasource.SearchGithubUserDataSource
import kotlinx.coroutines.CoroutineScope


private class GithubUserRepositoryImpl(
    private val gitHubApi: GitHubApi
) : GithubUserRepository {

    private val pagedListConfig = PagedList.Config
        .Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    override fun getGitHubUsers(
        viewModelScope: CoroutineScope,
        accessToken: String
    ): LiveData<PagedList<GithubUser>> {
        return LivePagedListBuilder(
            GithubUserDataSource.Factory(
                gitHubApi,
                viewModelScope,
                accessToken
            ), pagedListConfig
        ).build()
    }

    override fun getSearchedPagedListUsers(
        accessToken: String,
        query: String,
        viewModelScope: CoroutineScope
    ): LiveData<PagedList<GithubUser>> {
        return LivePagedListBuilder(
            SearchGithubUserDataSource.Factory(
                gitHubApi,
                query,
                accessToken,
                viewModelScope
            ), pagedListConfig
        ).build()
    }
}


interface GithubUserRepository {
    fun getGitHubUsers(viewModelScope: CoroutineScope, accessToken: String): LiveData<PagedList<GithubUser>>
    fun getSearchedPagedListUsers(
        accessToken: String,
        query: String,
        viewModelScope: CoroutineScope
    ): LiveData<PagedList<GithubUser>>

    companion object {
        val githubUserRepoInstance: GithubUserRepository by lazy {
            GithubUserRepositoryImpl(HttpClientManager.githubInstance.createGithubApi())
        }
    }

}
