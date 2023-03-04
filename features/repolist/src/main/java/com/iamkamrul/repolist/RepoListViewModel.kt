package com.iamkamrul.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.usecase.RepoListUseCase
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.RepoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoListUseCase: RepoListUseCase
): ViewModel(){
    val action:(RepoListUiAction)->Unit

    private val viewModelState = MutableStateFlow(RepoListViewModelState(isLoading = true))
    val uiState = viewModelState
        .map(RepoListViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        action = {
            when(it){
                RepoListUiAction.FetchRepoList -> fetchRepoList()
            }
        }
        fetchRepoList()
    }

    private fun fetchRepoList(){
        viewModelScope.launch {
            repoListUseCase.execute(RepoListUseCase.Params(userName = "kamrul3288")).collect{response->
                when(response){
                    is Result.Error -> viewModelState.update {
                        it.copy(error = response.message)
                    }
                    is Result.Loading -> viewModelState.update {
                        it.copy(error = "", isLoading = response.loading)
                    }
                    is Result.Success -> viewModelState.update {
                        it.copy(repoList = response.data)
                    }
                }
            }
        }
    }

}

private data class RepoListViewModelState(
    val isLoading:Boolean = false,
    val error:String = "",
    val repoList:List<RepoItemEntity> = listOf()
){
    fun toUiState():RepoListUiState =
        if (repoList.isEmpty()) RepoListUiState.RepoListEmpty(isLoading = isLoading, error = error)
        else RepoListUiState.HasRepoList(isLoading = isLoading, error = error, repoList = repoList)
}

sealed interface RepoListUiState{
    val isLoading:Boolean
    val error:String

    data class HasRepoList(
        val repoList:List<RepoItemEntity>,
        override val isLoading: Boolean,
        override val error: String
    ):RepoListUiState

    data class RepoListEmpty(
        override val isLoading: Boolean,
        override val error: String
    ):RepoListUiState
}

sealed class RepoListUiAction{
    object FetchRepoList:RepoListUiAction()
}