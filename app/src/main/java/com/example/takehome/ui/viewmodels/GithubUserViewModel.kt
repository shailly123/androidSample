package com.example.takehome.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos
import com.example.takehome.data.remote.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserViewModel @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModel() {

    val _uiState : MutableStateFlow<UiState> = MutableStateFlow(
        UiState(false,null,
            listOf(),null
        )
    )
    val uiState : StateFlow<UiState> = _uiState

    private val _githubUser = MutableLiveData<GithubUser>()
    private val githubUser: LiveData<GithubUser> = _githubUser

    private val _githubUserRepos = MutableLiveData<List<GithubUserRepos>>()
    private val githubUserRepos: LiveData<List<GithubUserRepos>> = _githubUserRepos

     fun fetchGithubUser(userId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                _githubUser.value = githubUserRepository.fetchGithubUser(userId)
                _uiState.value = _uiState.value.copy(isLoading = false, githubUser = githubUser.value)
                 fetchGithubUserRepos(userId = userId)
            }
            catch (e :Exception){
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message.toString(), githubUser = null, userRepos = listOf())
            }

        }
    }
     fun fetchGithubUserRepos(userId: String) {
        viewModelScope.launch {
            try { _uiState.value.copy(isLoading = true)
                _githubUserRepos.value = githubUserRepository.fetchGithubUserRepos(userId)
                _uiState.value = _uiState.value.copy(isLoading = false, userRepos = githubUserRepos.value.orEmpty())
            }
            catch (e : java.lang.Exception){
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message.toString(),githubUser = null, userRepos = listOf())
            }
        }
    }

    fun getItemById(itemId: Long): GithubUserRepos? {
        return githubUserRepos.value?.find { it.id == itemId.toLong() }
    }
}