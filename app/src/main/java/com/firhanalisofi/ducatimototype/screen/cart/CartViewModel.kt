package com.firhanalisofi.ducatimototype.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firhanalisofi.ducatimototype.repository.DucatiTypeRepository
import com.firhanalisofi.ducatimototype.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: DucatiTypeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderType()
                .collect { orderType ->
                    val totalRequiredPrice =
                        orderType.sumOf { it.type.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderType, totalRequiredPrice))
                }
        }
    }

    fun updateOrderType(typeId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderType(typeId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRewards()
                    }
                }
        }
    }
}