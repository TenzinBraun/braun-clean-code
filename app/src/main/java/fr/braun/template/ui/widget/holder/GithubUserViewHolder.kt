package fr.braun.template.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.braun.template.R
import fr.braun.template.data.model.GithubUser
import fr.braun.template.ui.fragment.ListActionCallback
import kotlinx.android.synthetic.main.view_holder_github_user.view.*

class GithubUserViewHolder(
    private val holderView: View,
    private val callback: ListActionCallback
) : RecyclerView.ViewHolder(holderView) {

    fun bindViewWith(githubUserData: GithubUser) {
        holderView.apply {
            this.username.apply { text = githubUserData.userLogin }
            this.userType.apply { text = githubUserData.type }
            this.userID.apply { text = githubUserData.userID.toString() }

            Glide.with(this)
                .load(githubUserData.avatar)
                .circleCrop()
                .into(this.userAvatar)

            this.setOnClickListener{
                callback.onUserClickItem(githubUserData)
            }
        }
    }


    companion object {
        fun create(
            parent: ViewGroup,
            callback: ListActionCallback
        ): GithubUserViewHolder {
            val holderView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_github_user, parent, false)

            return GithubUserViewHolder(holderView, callback)
        }
    }

}
