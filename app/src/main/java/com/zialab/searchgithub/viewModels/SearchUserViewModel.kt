package com.zialab.searchgithub.viewModels

import androidx.lifecycle.*
import com.zialab.domain.common.Result
import com.zialab.domain.entities.ResultSearchUI
import com.zialab.domain.entities.SearchUserRequestUI
import com.zialab.domain.useCases.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase
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
}