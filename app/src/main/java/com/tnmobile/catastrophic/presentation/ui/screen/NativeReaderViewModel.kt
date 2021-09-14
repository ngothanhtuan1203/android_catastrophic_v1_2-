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
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.domain.model.ReaderViewItem
import com.tnmobile.catastrophic.domain.usecase.TNUseCase
import com.tnmobile.catastrophic.utilily.TNLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

@ExperimentalPagingApi
class NativeReaderViewModel
@ViewModelInject constructor(
    private val tnUseCase: TNUseCase
) : ViewModel() {
    private val _readData =
        MutableLiveData<ReaderViewItem>().apply { value = null }
    val data: LiveData<ReaderViewItem> = _readData


    fun loadSmartViewData(url:String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            tnUseCase.parseData(url).collectLatest {
                withContext(Dispatchers.Main) {
                    _readData.value = it
                }

            }
        }

    }
}