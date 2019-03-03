package com.ahmedbenfadhel.githubdashboard.view.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedbenfadhel.githubdashboard.R
import com.ahmedbenfadhel.githubdashboard.data.local.GithubDatabase
import com.ahmedbenfadhel.githubdashboard.data.remote.NetworkService
import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project
import com.ahmedbenfadhel.githubdashboard.data.repository.GithubRepository
import com.ahmedbenfadhel.githubdashboard.databinding.FragmentProjectListBinding
import com.ahmedbenfadhel.githubdashboard.view.adapter.ProjectAdapter
import com.ahmedbenfadhel.githubdashboard.view.callback.ProjectClickCallback
import com.ahmedbenfadhel.githubdashboard.viewmodel.ProjectListViewModel
import com.ahmedbenfadhel.githubdashboard.viewmodel.ProjectListViewModelFactory
import kotlinx.android.synthetic.main.fragment_project_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Fragment with a list of GitHub repositories.
 */
class ProjectListFragment : Fragment() {

    companion object {
        const val TAG = "ProjectListFragment"
    }

    private lateinit var viewModelFactory: ProjectListViewModelFactory
    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var binding: FragmentProjectListBinding

    private val projectClickCallback = object : ProjectClickCallback {
        override fun onClick(project: Project) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(project)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)

        projectAdapter = ProjectAdapter(projectClickCallback)
        binding.projectList.adapter = projectAdapter
        binding.projectList.layoutManager = LinearLayoutManager(activity)

        binding.isLoading = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setIconifiedByDefault(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val db = GithubDatabase.getDataBase(activity!!.applicationContext)
        val userDao = db!!.userDao()
        val projectDao = db.projectDao()
        val githubService = NetworkService(context!!).service

        val repository = GithubRepository(githubService, userDao, projectDao)
        viewModelFactory = ProjectListViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProjectListViewModel::class.java)
        observeViewModel(viewModel)

        searchView.setOnQueryTextListener(QueryTextListener {
            binding.isLoading = true
            if (isOnline()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val response = viewModel.search(it)
                    if (response == null || response.isEmpty()) {
                        loading_projects.text = this@ProjectListFragment.resources.getString(R.string.user_not_found)
                        binding.isLoading = true
                    } else {
                        loading_projects.visibility = View.GONE
                        binding.isLoading = false
                    }
                }
            } else {
                loading_projects.text = this@ProjectListFragment.resources.getString(R.string.internet_access_error)
                binding.isLoading = true
            }
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchView.windowToken, 0)
        })
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        // Update the list when the data changes
        viewModel.githubRepository.data.observe(this, Observer { projects ->
            if (projects != null && projects.isNotEmpty())  {
                binding.isLoading = false
                projectAdapter.setProjectList(projects)
            }
        })
    }

    class QueryTextListener(private val action: (String) -> Unit) : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null && query.isNotBlank()) action(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false

    }

    private fun isOnline(): Boolean {
        val cm = activity?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        return nInfo != null && nInfo.isConnected
    }
}