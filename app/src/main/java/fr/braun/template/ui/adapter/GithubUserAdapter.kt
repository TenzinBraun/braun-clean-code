package fr.braun.template.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import fr.braun.template.data.model.GithubUser
import fr.braun.template.ui.widget.holder.GithubUserViewHolder

class GithubUserAdapter : PagedListAdapter<GithubUser, GithubUserViewHolder>(Companion) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder =
        GithubUserViewHolder.create(parent)

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        getItem(position)?.run { holder.bindViewWith(this) }

    }

    companion object : DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem == newItem
        }
    }
}
