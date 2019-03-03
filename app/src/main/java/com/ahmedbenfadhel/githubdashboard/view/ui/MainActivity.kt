package com.ahmedbenfadhel.githubdashboard.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedbenfadhel.githubdashboard.R
import com.ahmedbenfadhel.githubdashboard.data.remote.model.Project

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add project list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = ProjectListFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                .commit()
        }
    }

    /** Shows the project detail fragment  */
    fun show(project: Project?) {
        val projectFragment = ProjectDetailsFragment.newInstance(project)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack("project")
            .replace(
                R.id.fragment_container,
                projectFragment, null).commit()
    }
}
