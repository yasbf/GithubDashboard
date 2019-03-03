package com.ahmedbenfadhel.githubdashboard.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedbenfadhel.githubdashboard.R
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project
import com.ahmedbenfadhel.githubdashboard.data.remote.model.User
import com.ahmedbenfadhel.githubdashboard.databinding.ProjectListItemBinding
import com.ahmedbenfadhel.githubdashboard.view.callback.ProjectClickCallback

class ProjectAdapter(private val projectClickCallback: ProjectClickCallback?) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var projectList: List<ProjectEntity>? = null
    fun setProjectList(projectList: List<ProjectEntity>){

            this.projectList = projectList
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : ProjectListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.project_list_item, parent, false)

        binding.callback = projectClickCallback

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val projectEntity = projectList!![position]
        val owner = User(avatar_url = projectEntity.owner.avatar_url, name = projectEntity.owner.name, html_url = projectEntity.owner.html_url)
        val project = Project(projectEntity.name, owner, projectEntity.description, projectEntity.language, projectEntity.git_url, projectEntity.stargazers_count)
        holder.binding.project = project
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (projectList == null) 0 else projectList!!.size
    }

    class ProjectViewHolder(val binding: ProjectListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
