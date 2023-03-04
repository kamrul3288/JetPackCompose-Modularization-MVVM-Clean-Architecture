package com.iamkamrul.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.usecase.ProfileUseCase
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
): ViewModel(){
    val action:(ProfileUiAction)->Unit

    private val viewModelState = MutableStateFlow(ProfileViewModelState(isLoading = true))
    val uiState = viewModelState
        .map(ProfileViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

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
            profileUseCase.execute(ProfileUseCase.Params(userName = "kamrul3288")).collect{ response->
                when(response){
                    is Result.Error -> viewModelState.update {
                        it.copy(error = response.message)
                    }
                    is Result.Loading -> viewModelState.update {
                        it.copy(error = "", isLoading = response.loading)
                    }
                    is Result.Success -> viewModelState.update {
                        it.copy(profileEntity = response.data)
                    }
                }
            }
        }
    }
}

private data class ProfileViewModelState(
    val isLoading:Boolean = false,
    val error:String = "",
    val profileEntity:ProfileEntity? = null
){
    fun toUiState():ProfileUiState =
        if (profileEntity != null) ProfileUiState.Success(isLoading = isLoading, error = error, profileEntity = profileEntity)
        else ProfileUiState.Error(isLoading = isLoading, error = error)
}

sealed interface ProfileUiState{
    val isLoading:Boolean
    val error:String

    data class Success(
        val profileEntity:ProfileEntity,
        override val isLoading: Boolean,
        override val error: String
    ):ProfileUiState

    data class Error(
        override val isLoading: Boolean,
        override val error: String
    ):ProfileUiState
}

sealed class ProfileUiAction{
    object FetchProfile:ProfileUiAction()
}