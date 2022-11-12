package com.iamkamrul.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamkamrul.domain.usecase.RepoListUseCase
import com.iamkamrul.domain.utils.Result
import com.iamkamrul.entity.RepoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoListUseCase: RepoListUseCase
): ViewModel(){
    val action:(RepoListUiAction)->Unit
    private val _uiState = MutableStateFlow<RepoListUiState<*>>(RepoListUiState.Loading(false))
    val uiState get() = _uiState.asStateFlow()

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
            repoListUseCase.execute(RepoListUseCase.Params(userName = "kamrul3288")).collect{result->
                when(result){
                    is Result.Error -> _uiState.value = RepoListUiState.Error(result.message)
                    is Result.Loading -> _uiState.value = RepoListUiState.Loading(result.loading)
                    is Result.Success -> _uiState.value = RepoListUiState.Success(result.data)
                }
            }
        }
    }

}

sealed class RepoListUiState<out R>{
    data class Loading(val isLoading:Boolean):RepoListUiState<Loading>()
    data class Success(val repoList:List<RepoItemEntity>):RepoListUiState<Success>()
    data class Error(val message:String):RepoListUiState<Error>()
}

sealed class RepoListUiAction{
    object FetchRepoList:RepoListUiAction()
}