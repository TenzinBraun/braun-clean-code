package fr.braun.template.data.model


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") val userLogin: String,
    @PrimaryKey @SerializedName("id") val userID: Int,
    @SerializedName("node_id") val nodeID: String,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("gravatar_id") val avatarID: String,
    @SerializedName("url") val userUrl: String,
    @SerializedName("html_url") val userHtmlUrl: String,
    @SerializedName("followers_url") val userFollowers: String,
    @SerializedName("following_url") val userFollowing: String,
    @SerializedName("gists_url") val gists: String,
    @SerializedName("starred_url") val starred: String,
    @SerializedName("subscriptions_url") val subscriptions: String,
    @SerializedName("organizations_url") val organization: String,
    @SerializedName("repos_url") val repositories: String,
    @SerializedName("events_url") val events: String,
    @SerializedName("received_events_url") val receivedEvent: String,
    @SerializedName("type") val type: String,
    @SerializedName("site_admin") val adminSite: Boolean,
    @SerializedName("created_at") val createdAt: String?= null,
    @SerializedName("public_repos") val publicRepositories: Int?= null,
    @SerializedName("followers") val followers: Int?= null,
    @SerializedName("location") val location: String?= null,
    @SerializedName("email") val userEmail: String?= null,
    @SerializedName("name") val username: String? = null,
    @SerializedName("blog") val blog: String? = null
)
