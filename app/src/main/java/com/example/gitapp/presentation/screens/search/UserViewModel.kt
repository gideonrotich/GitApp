package com.example.gitapp.presentation.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapp.domain.usecases.GetUserUseCase
import com.example.gitapp.presentation.components.SearchWidgetState
import com.example.gitapp.presentation.screens.search.UserState
import com.example.gitapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase
) : ViewModel(){

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private val _searchString = mutableStateOf("")
    val searchString: State<String> = _searchString

    fun setSearchString(value: String) {
        _searchString.value = value
    }

    private val _state = mutableStateOf(UserState())
    val state:State<UserState> = _state


     fun getUsers(user:String){
        userUseCase(user).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = UserState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = UserState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = UserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}