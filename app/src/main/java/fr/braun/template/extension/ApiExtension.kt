package fr.braun.template.extension

import fr.braun.template.data.model.GitHubPagedResult
import fr.braun.template.data.model.GithubUser
import retrofit2.Response

fun Response<List<GithubUser>>.runWithThrows() : List<GithubUser> {
    return this.run {
        if (this.isSuccessful) this.body()
            ?: throw IllegalStateException("Body is null")
        else throw IllegalStateException("Response is not successful : code = ${this.code()}")
    }
}

inline fun <reified T> Response<GitHubPagedResult<T>>.runWithThrows() : GitHubPagedResult<T> {
    return this.run {
        if (this.isSuccessful) this.body()
            ?: throw IllegalStateException("Body is null")
        else throw IllegalStateException("Response is not successful : code = ${this.code()}")
    }
}