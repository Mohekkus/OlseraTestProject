package me.mohekkus.olsera_testproject.ui.list

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mohekkus.olsera_testproject.RetailSource
import me.mohekkus.olsera_testproject.database.dao.RetailDAO
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import me.mohekkus.olsera_testproject.database.repository.RetailRepository
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class RetailViewModel @Inject constructor(private val repository: RetailRepository, private val dao: RetailDAO) : ViewModel() {

    private val _allStatus = MutableLiveData<String>().apply {
        value = "All Status"
    }
    val allStatus: LiveData<String> = _allStatus

    private val _activeStatus = MutableLiveData<String>().apply {
        value = "Active"
    }
    val activeStatus: LiveData<String> = _activeStatus

    private val _inactiveStatus = MutableLiveData<String>().apply {
        value = "Inactive"
    }
    val inactiveStatus: LiveData<String> = _inactiveStatus

    private val _addItem = MutableLiveData<Boolean>().apply {
        value = true
    }
    val addItem: LiveData<Boolean> = _addItem


    val getAllRetail = Pager(
        config = PagingConfig(
            pageSize = 20, enablePlaceholders = false, initialLoadSize = 20
        ),
    ) { repository.get() }.flow

//    private val _getCountItem = MutableLiveData<Int>().apply {
//        value = repository.getCount()
//    }
//    val getCountItem = _getCountItem

    fun insert(retailEntity: RetailEntity) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(retailEntity)
    }

    fun delete(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteId(id)
    }

}

class RetailVMFactory(private val repository: RetailRepository, private val dao: RetailDAO): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RetailViewModel(repository, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}