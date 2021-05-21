package com.tnmobile.catastrophic.presentation.ui.screen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.usecase.TNUseCase
import com.tnmobile.catastrophic.utilily.TNLog
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val tnUseCase: TNUseCase
) : ViewModel() {
    private val _cats =
        MutableLiveData<List<Cat>>().apply { value = emptyList() }
    val cats: LiveData<List<Cat>> = _cats

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadCatsData() = viewModelScope.launch() {
        TNLog.d("MainViewModel","DEBUG loadCatsData");
        fetchCat()
    }

    private suspend fun fetchCat() {
        _isViewLoading.value = true

        when (val result: TNUseCase.Result = tnUseCase.getAllCats()) {
            is TNUseCase.Result.Success<*> -> {
                _isViewLoading.value = false
                val data = result.data as List<Cat>
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _cats.value = data
                }
            }
            is TNUseCase.Result.Failure -> {
                _isViewLoading.value = false
                _isEmptyList.value = true
                _onMessageError.value = result.message
            }
        }
    }
}