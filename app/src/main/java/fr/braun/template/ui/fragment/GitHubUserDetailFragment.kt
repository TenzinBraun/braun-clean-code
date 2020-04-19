package fr.braun.template.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import fr.braun.template.R
import fr.braun.template.extension.hide
import fr.braun.template.extension.show
import fr.braun.template.ui.activity.MainActivity
import fr.braun.template.ui.viewmodel.GithubUserViewModel
import kotlinx.android.synthetic.main.fragment_github_user_details.*
import java.lang.IllegalStateException

class GitHubUserDetailFragment : Fragment() {

    private lateinit var username: String
    private lateinit var githubUserViewModel: GithubUserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run {
            githubUserViewModel = ViewModelProvider(this, GithubUserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        username = arguments?.getString(LOGIN_KEY) ?: throw IllegalStateException("No username found")
        return inflater.inflate(R.layout.fragment_github_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.supportActionBar?.apply {
            this.title = username
            this.setDisplayHomeAsUpEnabled(true)
        }
        setupUserDetailInfoInto()
    }

    private fun setupUserDetailInfoInto() {
        githubUserViewModel.getDetailUserWith(username) {
            it?.let { user ->
                Glide.with(this)
                    .load(user.avatar)
                    .circleCrop()
                    .into(this.userAvatar)
                userMainInfoContainer.show()
                detailInfoContainer.show()
                userLogin.apply { text = user.userLogin }
                userType.apply { text = user.type }
                createdAtContent.apply { text = user.createdAt }
                countPublicRepositoryContent.apply { text = user.publicRepositories.toString() }
                countFollowersContent.apply { text = user.followers.toString() }
                userEmailContent.apply { text = user.userEmail }
                progressBarDetail.hide()
            }
        }
    }

    companion object {
        const val LOGIN_KEY = "login_key"
    }
}