package fr.braun.template.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import fr.braun.template.BuildConfig
import fr.braun.template.data.model.GithubUser
import fr.braun.template.data.repository.GithubUserRepository

class GithubUserViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    fun getUserSearch(query: String): LiveData<PagedList<GithubUser>> =
        githubUserRepository.getSearchedPagedListUsers(accessToken, query, viewModelScope)

    private val accessToken: String = BuildConfig.GITHUB_API_TOKEN

    val githubUsers = githubUserRepository.getGitHubUsers(viewModelScope, accessToken)

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GithubUserViewModel(GithubUserRepository.githubUserRepoInstance) as T
        }
    }
}
