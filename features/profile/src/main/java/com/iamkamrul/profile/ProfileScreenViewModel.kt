package com.iamkamrul.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.usecase.ProfileUseCase
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
): ViewModel(){
    val action:(ProfileUiAction)->Unit
    private val _uiState = MutableStateFlow<ProfileUiState<*>>(ProfileUiState.Loading(false))
    val uiState get() = _uiState.asStateFlow()

    init {
        action = {
            when(it){
                ProfileUiAction.FetchProfile -> fetchProfile()
            }
        }
        fetchProfile()
    }

    private fun fetchProfile(){
        viewModelScope.launch {
            profileUseCase.execute(ProfileUseCase.Params(userName = "kamrul3288")).collect{ result->
                when(result){
                    is Result.Error -> _uiState.value = ProfileUiState.Error(result.message)
                    is Result.Loading -> _uiState.value = ProfileUiState.Loading(result.loading)
                    is Result.Success -> _uiState.value = ProfileUiState.Success(result.data)
                }
            }
        }
    }
}

sealed class ProfileUiState<out R>{
    data class Loading(val isLoading:Boolean):ProfileUiState<Loading>()
    data class Success(val profile:ProfileEntity):ProfileUiState<Success>()
    data class Error(val message:String):ProfileUiState<Error>()
}

sealed class ProfileUiAction{
    object FetchProfile:ProfileUiAction()
}