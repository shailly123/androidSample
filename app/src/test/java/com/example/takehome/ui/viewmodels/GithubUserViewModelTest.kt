package com.example.takehome.ui.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos
import com.example.takehome.data.remote.repository.GithubUserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*

import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class GithubUserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GithubUserViewModel
    private lateinit var repository: GithubUserRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = GithubUserViewModel(repository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `fetchGithubUser Success`() = testDispatcher.runBlockingTest {
        // Given
        val userId = "example_user_id"
        val githubUser = GithubUser("df", "df")
        val githubUserRepos = listOf<GithubUserRepos>(GithubUserRepos(1,"dd","df","df",2,2))
        val expectedUiState = UiState(isLoading = false, githubUser = githubUser, userRepos = listOf(),error =null)

        coEvery { repository.fetchGithubUser(userId) } returns githubUser
        coEvery { repository.fetchGithubUserRepos(userId) } returns githubUserRepos

        // When
        viewModel.fetchGithubUser(userId)

        // Then
        assertEquals(expectedUiState.isLoading, false)
        assertEquals(expectedUiState.githubUser, viewModel.uiState.value.githubUser)
    }

    @Test
    fun `fetchGithubUserRepos Success`() = testDispatcher.runBlockingTest {
        // Given
        val userId = "example_user_id"
        val githubUserRepos = listOf<GithubUserRepos>(GithubUserRepos(1,"dd","df","df",2,2))
        val expectedUiState = UiState(isLoading = false, githubUser = null, userRepos = githubUserRepos,error =null)

        coEvery { repository.fetchGithubUserRepos(userId) } returns githubUserRepos

        // When
        viewModel.fetchGithubUserRepos(userId)

        // Then
        assertEquals(expectedUiState.isLoading, false)
        assertEquals(expectedUiState.userRepos, viewModel.uiState.value.userRepos)
    }
    @Test
    fun `fetchGithubUser Failure`() = testDispatcher.runBlockingTest {
        // Given
        val userId = "example_user_id"
        val githubUserRepos = listOf<GithubUserRepos>(GithubUserRepos(1,"dd","df","df",2,2))
        val expectedUiState = UiState(isLoading = false, githubUser = null, userRepos = githubUserRepos,error ="User not found")

        coEvery { repository.fetchGithubUserRepos(userId) } returns githubUserRepos

        // When
        viewModel.fetchGithubUserRepos(userId)

        // Then
        assertEquals(expectedUiState.isLoading, false)
        assertEquals(expectedUiState.error, viewModel.uiState.value.userRepos)
    }
}
