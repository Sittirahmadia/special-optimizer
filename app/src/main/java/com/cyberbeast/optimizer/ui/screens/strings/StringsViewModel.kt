package com.cyberbeast.optimizer.ui.screens.strings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberbeast.optimizer.data.model.OptimizerString
import com.cyberbeast.optimizer.data.repository.StringsRepository
import com.cyberbeast.optimizer.shizuku.CommandResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StringsViewModel @Inject constructor(
    private val stringsRepository: StringsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _lastResult = MutableStateFlow<CommandResult?>(null)
    val lastResult: StateFlow<CommandResult?> = _lastResult

    val strings: StateFlow<List<OptimizerString>> = combine(
        _searchQuery,
        _selectedCategory,
        stringsRepository.getAllStrings()
    ) { query, category, allStrings ->
        var filtered = allStrings
        if (category != "all") {
            filtered = filtered.filter { it.category == category }
        }
        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(query, true) ||
                it.key.contains(query, true) ||
                it.description.contains(query, true)
            }
        }
        filtered
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val categories: StateFlow<List<String>> = stringsRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            stringsRepository.initializeDatabase()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun applyString(string: OptimizerString) {
        viewModelScope.launch {
            _isLoading.value = true
            _lastResult.value = stringsRepository.applyString(string)
            _isLoading.value = false
        }
    }

    fun addCustomString(string: OptimizerString) {
        viewModelScope.launch {
            stringsRepository.addCustomString(string)
        }
    }

    fun deleteString(string: OptimizerString) {
        viewModelScope.launch {
            stringsRepository.deleteString(string)
        }
    }
}
