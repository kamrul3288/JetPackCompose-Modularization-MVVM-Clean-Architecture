package com.iamkamrul.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.entity.ProfileEntity
import com.iamkamrul.domain.outcome.DataError
import com.iamkamrul.domain.outcome.Resource
import com.iamkamrul.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState get() = _profileUiState.asStateFlow()


    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            profileUseCase.execute(ProfileUseCase.Params(userName = "kamrul3288"))
                .collect { response ->
                    when (response) {
                        is Resource.Error -> _profileUiState.value =
                            ProfileUiState.Error(response.error)

                        Resource.Loading -> _profileUiState.value = ProfileUiState.Loading
                        is Resource.Success -> _profileUiState.value =
                            ProfileUiState.Success(response.data)
                    }
                }
        }
    }

    fun handleAction(action: ProfileUiAction) {
        when (action) {
            ProfileUiAction.FetchProfile -> fetchProfile()
        }
    }
}


sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val data: ProfileEntity) : ProfileUiState
    data class Error(val error: DataError) : ProfileUiState
}

sealed interface ProfileUiAction {
    data object FetchProfile : ProfileUiAction
}