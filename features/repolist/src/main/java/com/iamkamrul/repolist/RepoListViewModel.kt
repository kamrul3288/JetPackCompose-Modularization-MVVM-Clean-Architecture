package com.iamkamrul.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.entity.RepoItemEntity
import com.iamkamrul.domain.outcome.DataError
import com.iamkamrul.domain.outcome.Resource
import com.iamkamrul.domain.usecase.RepoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoListUseCase: RepoListUseCase
) : ViewModel() {
    private val _repoListUiState = MutableStateFlow<RepoListUiState>(RepoListUiState.Loading)
    val repoListUiState get() = _repoListUiState.asStateFlow()

    init {
        fetchRepoList()
    }

    private fun fetchRepoList() {
        viewModelScope.launch {
            repoListUseCase.execute(RepoListUseCase.Params(userName = "kamrul3288"))
                .collect { response ->
                    when (response) {
                        is Resource.Error -> _repoListUiState.value =
                            RepoListUiState.Error(response.error)

                        is Resource.Loading -> _repoListUiState.value = RepoListUiState.Loading
                        is Resource.Success -> {
                            if (response.data.isEmpty()) {
                                _repoListUiState.value = RepoListUiState.RepoListEmpty
                                return@collect
                            }
                            _repoListUiState.value = RepoListUiState.HasRepoList(response.data)
                        }
                    }
                }
        }
    }

    fun handleAction(action: RepoListUiAction) {
        when (action) {
            RepoListUiAction.FetchRepoList -> fetchRepoList()
        }
    }
}

sealed interface RepoListUiState {
    data object Loading : RepoListUiState
    data class HasRepoList(val repoList: List<RepoItemEntity>) : RepoListUiState
    data object RepoListEmpty : RepoListUiState
    data class Error(val error: DataError) : RepoListUiState
}

sealed interface RepoListUiAction {
    data object FetchRepoList : RepoListUiAction
}