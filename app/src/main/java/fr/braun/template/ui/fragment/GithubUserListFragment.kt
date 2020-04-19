package fr.braun.template.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.braun.template.R
import fr.braun.template.data.model.GithubUser
import fr.braun.template.extension.hide
import fr.braun.template.extension.show
import fr.braun.template.ui.activity.MainActivity
import fr.braun.template.ui.adapter.GithubUserAdapter
import fr.braun.template.ui.viewmodel.GithubUserViewModel
import kotlinx.android.synthetic.main.fragment_github_user_list.*

class GithubUserListFragment : Fragment(), ListActionCallback {

    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubUserViewModel: GithubUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run {
            githubUserViewModel = ViewModelProvider(this, GithubUserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        return inflater.inflate(R.layout.fragment_github_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        showProgressBar()
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.app_toolbar_title)
            this.setDisplayHomeAsUpEnabled(false)
        }

        githubUserAdapter = GithubUserAdapter(this)
        githubUserListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = githubUserAdapter
        }
        setUpFetchLiveData()
    }

    private fun setUpFetchLiveData() {
        githubUserViewModel.githubUsers.observe(this) {
            githubUserAdapter.submitList(it)
            hideProgressBar()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search_item)
        val searchMenuView = searchMenuItem.actionView as SearchView

        searchMenuView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchMenuView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showProgressBar()
                    githubUserViewModel.getUserSearch(query)
                        .observe(this@GithubUserListFragment) {
                            githubUserAdapter.submitList(it)
                            hideProgressBar()
                        }
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    return true
                }
                return false
            }
        })
    }

    private fun showProgressBar() {
        progressBarGithubUserList.show()
        githubUserListRecyclerView.hide()
    }

    private fun hideProgressBar() {
        progressBarGithubUserList.hide()
        githubUserListRecyclerView.show()
    }

    override fun onUserClickItem(githubUser: GithubUser) {
        findNavController().navigate(
            R.id.action_githubUserListFragment_to_gitHubUserDetailFragment,
            bundleOf(
                GitHubUserDetailFragment.LOGIN_KEY to githubUser.userLogin
            )
        )
    }
}

interface ListActionCallback {
    fun onUserClickItem(githubUser: GithubUser)
}