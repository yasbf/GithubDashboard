package com.ahmedbenfadhel.githubdashboard

import android.content.Context
import android.widget.EditText
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.ahmedbenfadhel.githubdashboard.data.local.GithubDatabase
import com.ahmedbenfadhel.githubdashboard.data.local.dao.ProjectDao
import com.ahmedbenfadhel.githubdashboard.data.local.dao.UserDao
import com.ahmedbenfadhel.githubdashboard.data.local.entity.ProjectEntity
import com.ahmedbenfadhel.githubdashboard.data.local.entity.UserEntity
import com.ahmedbenfadhel.githubdashboard.data.remote.GitHubService
import com.ahmedbenfadhel.githubdashboard.data.repository.GithubRepository
import com.ahmedbenfadhel.githubdashboard.view.adapter.ProjectAdapter
import com.ahmedbenfadhel.githubdashboard.view.ui.MainActivity
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@LargeTest
class ProjectListTest : AndroidJUnitRunner() {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var db: GithubDatabase
    private lateinit var uDAO: UserDao
    private lateinit var pDAO: ProjectDao

    private lateinit var firstUser: UserEntity
    private lateinit var secondUser: UserEntity
    private lateinit var firstProject: ProjectEntity
    private lateinit var secondProject: ProjectEntity
    private lateinit var projects: List<ProjectEntity>

    @Mock
    private lateinit var gitHubService: GitHubService


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java).build()
        uDAO = db.userDao()
        pDAO = db.projectDao()

        firstUser = UserEntity(1, "firstUser")
        secondUser = UserEntity(2, "user2")

        firstProject =
            ProjectEntity(1, "GithubProject", firstUser.id, firstUser, "GithubProject Description", "Kotlin", "git_URL", "3")
        secondProject =
            ProjectEntity(2, "SecondProject", firstUser.id, firstUser, "SecondProject Description", "Java", "git_URL", "2")

        projects = listOf(firstProject, secondProject)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun testUserHasRepos() {
        // Focus on SearchView
        onView(withId(R.id.searchView))
            .perform(click())

        // Type text and click search button
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("GOOGLE"), pressImeActionButton())
        // Wait for results
        Thread.sleep(5000)
        onView(withId(R.id.project_list))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProjectAdapter.ProjectViewHolder>(0, click()))
        Thread.sleep(5000)
    }

    @Test
    fun testUserNotFound() {
        // Focus on SearchView
        onView(withId(R.id.searchView))
            .perform(click())

        // Type text and click search button
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("qaz"), pressImeActionButton())
        Thread.sleep(5000)
        onView(withId(R.id.loading_projects))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_insert_user_and_project_list_into_database() {

        val githubRepository = GithubRepository(gitHubService, userDao = uDAO, projectDao = pDAO)
        Assert.assertNotNull(githubRepository)

        githubRepository.saveData(firstUser.name, projects)

        val userFromDb = uDAO.getUserByName("firstUser")
        Assert.assertNotNull(userFromDb)
        Assert.assertEquals(userFromDb?.name, firstUser.name)

        val projectsFromDb = pDAO.getProjectsByUser(userFromDb!!.id)
        Assert.assertNotNull(projectsFromDb)
        Thread.sleep(10000)
        Assert.assertEquals(2, projectsFromDb.size)
    }

    @org.junit.Test
    fun should_insert_user_into_db() {
        uDAO.insertUser(firstUser)
        val userFromDb = uDAO.getUserByName("firstUser")
        Assert.assertNotNull(userFromDb)
        Assert.assertEquals(userFromDb?.name, firstUser.name)
    }

    /*@Test
    fun display_content_after_receiving() = runBlocking {
        val repository = mock(GithubRepository::class.java)
        val view = mock(ProjectListFragment::class.java)

        val firstUser = UserEntity(1, "FirstUser")
        val firstProject =
            ProjectEntity(1, "GithubProject", firstUser, "GithubProject Description", "Kotlin", "avatar_URL")
        val secondProject =
            ProjectEntity(2, "SecondProject", firstUser, "SecondProject Description", "Java", "avatar_URL")

        val projects = listOf(firstProject, secondProject)
        val expectedResult = MutableLiveData<List<ProjectEntity>>()
        expectedResult.value = projects
        `when`(repository.fetchUserByName("ahmed")).thenReturn(expectedResult)
    }*/
}
