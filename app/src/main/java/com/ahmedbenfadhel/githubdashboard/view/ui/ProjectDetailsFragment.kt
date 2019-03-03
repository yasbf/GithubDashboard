package com.ahmedbenfadhel.githubdashboard.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedbenfadhel.githubdashboard.R
import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project
import com.ahmedbenfadhel.githubdashboard.databinding.FragmentProjectDetailsBinding
import com.ahmedbenfadhel.githubdashboard.viewmodel.ProjectViewModel

/**
 * Fragment for a detailed info about a GitHub Repository.
 */
class ProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsBinding

    companion object {

        private const val KEY_PROJECT = "project"

        /** Creates project fragment for specific project  */
        fun newInstance(project: Project?): ProjectDetailsFragment {
            val fragment = ProjectDetailsFragment()
            val args = Bundle()

            args.putParcelable(KEY_PROJECT, project)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)

        // Create and set the adapter for the RecyclerView.
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this)
            .get(ProjectViewModel::class.java)

        observeViewModel(viewModel)

        binding.isLoading = true

        val project = arguments!!.getParcelable<Project>(KEY_PROJECT)
        viewModel.setSelectedProject(project)
        binding.project = viewModel.selectedProject.value
    }

    private fun observeViewModel(viewModel: ProjectViewModel) {
        // Observe project data
        viewModel.selectedProject.observe(this, Observer { project ->
            if (project != null) {
                binding.isLoading = false
            }
        })
    }
}