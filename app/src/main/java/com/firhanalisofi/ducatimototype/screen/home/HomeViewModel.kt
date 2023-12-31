package com.firhanalisofi.ducatimototype.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firhanalisofi.ducatimototype.repository.DucatiTypeRepository
import com.firhanalisofi.ducatimototype.model.OrderType
import com.firhanalisofi.ducatimototype.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DucatiTypeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderType>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderType>>>
        get() = _uiState

    fun getAllType() {
        viewModelScope.launch {
            repository.getAllType()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderTypes ->
                    _uiState.value = UiState.Success(orderTypes)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.search(newQuery)
                .catch{ throwable ->
                    _uiState.value = UiState.Error(throwable.message.toString())
                }
                .collect { type ->
                    _uiState.value = UiState.Success(type.map { OrderType(it, 0) })
                }
        }
    }
}