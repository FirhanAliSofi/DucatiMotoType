package com.firhanalisofi.ducatimototype.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firhanalisofi.ducatimototype.repository.DucatiTypeRepository
import com.firhanalisofi.ducatimototype.model.OrderType
import com.firhanalisofi.ducatimototype.model.Type
import com.firhanalisofi.ducatimototype.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailTypeViewModel(
    private val repository: DucatiTypeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderType>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderType>>
        get() = _uiState

    fun getRewardById(TypeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderTypeById(TypeId))
        }
    }

    fun addToCart(type: Type, count: Int) {
        viewModelScope.launch {
            repository.updateOrderType(type.id, count)
        }
    }
}