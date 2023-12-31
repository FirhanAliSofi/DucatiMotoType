package com.firhanalisofi.ducatimototype.screen.cart

import com.firhanalisofi.ducatimototype.model.OrderType

data class CartState(
    val orderType: List<OrderType>,
    val totalRequiredPrice: Int
)