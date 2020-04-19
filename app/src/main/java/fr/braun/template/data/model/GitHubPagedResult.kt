package fr.braun.template.data.model

import com.google.gson.annotations.SerializedName

data class GitHubPagedResult<T>(
    @SerializedName("items") val results: List<T>,
    val resultCount: Int

)
