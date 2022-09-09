package com.example.gitapp.presentation.screens.repositories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapp.domain.usecases.GetUserRepos
import com.example.gitapp.presentation.screens.repositories.UserRepositoriesState
import com.example.gitapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserRepositoriesViewModel @Inject constructor(
    private val getUserRepos: GetUserRepos,

):ViewModel() {

    private val _state = mutableStateOf(UserRepositoriesState())
    val state:State<UserRepositoriesState> = _state

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    fun setUserName(value: String) {
        _username.value = value
    }


     fun getUserRepositories(username:String){
        getUserRepos(username).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = UserRepositoriesState(repos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = UserRepositoriesState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = UserRepositoriesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}