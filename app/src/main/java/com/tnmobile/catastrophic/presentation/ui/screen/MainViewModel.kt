package com.tnmobile.catastrophic.presentation.ui.screen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.usecase.TNUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainViewModel
@ViewModelInject constructor(
    private val tnUseCase: TNUseCase
) : ViewModel() {
    private val _cats =
        MutableLiveData<PagingData<Cat>>().apply { value = PagingData.empty() }
    val cats: LiveData<PagingData<Cat>> = _cats

    fun loadCatsData() = viewModelScope.launch {
        tnUseCase.getAllCats().cachedIn(viewModelScope).distinctUntilChanged().collectLatest {
            _cats.value = it
        }
    }
}