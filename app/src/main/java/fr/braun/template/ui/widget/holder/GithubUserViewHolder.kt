package fr.braun.template.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.braun.template.R
import fr.braun.template.data.model.GithubUser
import kotlinx.android.synthetic.main.view_holder_github_user.view.*

class GithubUserViewHolder(private val holderView: View) : RecyclerView.ViewHolder(holderView) {

    fun bindViewWith(githubUserData: GithubUser) {
        holderView.apply {
            Glide.with(this)
                .load(githubUserData.avatar)
                .circleCrop()
                .into(this.userAvatar)
        }

    }


    companion object {
        fun create(parent: ViewGroup): GithubUserViewHolder {
            val holderView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_github_user, parent, false)

            return GithubUserViewHolder(holderView)
        }
    }

}
