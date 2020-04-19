package fr.braun.template.data.networking.api

import fr.braun.template.data.model.GitHubPagedResult
import fr.braun.template.data.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET(ALL_USER)
    suspend fun getGithubUsers(
        @Header("Authorization") accessToken: String,
        @Query("since") firstPage: Int,
        @Query("per_page") userPerPage: Int
    ): Response<List<GithubUser>>

    fun getSearchGithubUsers(
        accessToken: String,
        query: String,
        firstPage: Int,
        userPerPage: Int
    ): Response<GitHubPagedResult<GithubUser>>

    @GET(DETAIL_USER)
    suspend fun getUserDetails(
        @Path("username") username: String,
        @Header("Authorization") accessToken: String
    ): Response<GithubUser>

    companion object {
        const val ALL_USER = "users"
        const val DETAIL_USER = "users/{username}"
    }
}
