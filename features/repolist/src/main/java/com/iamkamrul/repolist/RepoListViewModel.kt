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
    private val _repoListUiState = MutableStateFlow<RepoListUiState>(RepoListUiState.Loading)
    val repoListUiState get() = _repoListUiState.asStateFlow()

    init {
        fetchRepoList()
    }

    private fun fetchRepoList(){
        viewModelScope.launch {
            repoListUseCase.execute(RepoListUseCase.Params(userName = "kamrul3288")).collect{response->
                when(response){
                    is Result.Error -> _repoListUiState.value  = RepoListUiState.Error(response.message)
                    is Result.Loading -> _repoListUiState.value  = RepoListUiState.Loading
                    is Result.Success ->{
                        if (response.data.isEmpty()){
                            _repoListUiState.value = RepoListUiState.RepoListEmpty
                            return@collect
                        }
                        _repoListUiState.value = RepoListUiState.HasRepoList(response.data)
                    }
                }
            }
        }
    }

    fun handleAction(action: RepoListUiAction){
        when(action){
            RepoListUiAction.FetchRepoList -> fetchRepoList()
        }
    }
}

sealed interface RepoListUiState{
    data object Loading:RepoListUiState
    data class HasRepoList(val repoList:List<RepoItemEntity>):RepoListUiState
    data object RepoListEmpty:RepoListUiState
    data class Error(val message:String):RepoListUiState
}

sealed interface RepoListUiAction{
    data object FetchRepoList:RepoListUiAction
}