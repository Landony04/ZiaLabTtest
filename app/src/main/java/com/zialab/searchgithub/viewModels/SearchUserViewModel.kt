package com.zialab.searchgithub.viewModels

import androidx.lifecycle.*
import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.SearchUserRequestUI
import com.zialab.domain.entities.UserUI
import com.zialab.domain.useCases.ReposByUserUseCase
import com.zialab.domain.useCases.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val reposByUserUseCase: ReposByUserUseCase
) : ViewModel() {
    //Search Users
    //Mutable params search users request
    private val _searchUsersParameters = MutableLiveData<SearchUserRequestUI>()

    private val _searchUsersInformation = _searchUsersParameters.switchMap {
        searchUserUseCase.invokeSearchUser(it).asLiveData(viewModelScope.coroutineContext)
    }

    val searchUsersInformation: LiveData<Result<ResultSearchUI>> get() = _searchUsersInformation

    fun searchUsers(searchUserRequestUI: SearchUserRequestUI) {
        _searchUsersParameters.value = searchUserRequestUI
    }

    //Repos by User
    //Mutable params reposByUser request
    private val _reposByUserInformation = MutableLiveData<Result<UserUI>>()
    val reposByUserInformation: LiveData<Result<UserUI>> get() = _reposByUserInformation

    fun getReposByUser(user: String) {
        viewModelScope.launch {
            reposByUserUseCase.invokeReposByUser(user)
                .collect {
                    _reposByUserInformation.value = it
                }
        }
    }
}