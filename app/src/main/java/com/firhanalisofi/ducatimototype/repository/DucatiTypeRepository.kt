package com.firhanalisofi.ducatimototype.repository

import com.firhanalisofi.ducatimototype.model.TypeDataSource
import com.firhanalisofi.ducatimototype.model.OrderType
import com.firhanalisofi.ducatimototype.model.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DucatiTypeRepository {

    private val orderType = mutableListOf<OrderType>()

    init {
        if (orderType.isEmpty()) {
            TypeDataSource.dummyTypes.forEach{
                orderType.add(OrderType(it,0))
            }
        }
    }

    fun getAllType(): Flow<List<OrderType>> {
        return flowOf(orderType)
    }

    fun search(query: String): Flow<List<Type>> {
        return flow {
            val filteredList = orderType.filter { it.type.name.contains(query, ignoreCase = true) }
                .map { it.type }
            emit(filteredList)
        }
    }

    fun getOrderTypeById(rewardId: Long): OrderType {
        return orderType.first {
            it.type.id == rewardId
        }
    }

    fun updateOrderType(typeId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderType.indexOfFirst { it.type.id == typeId }
        val result = if (index >= 0) {
            val orderTypes = orderType[index]
            orderType[index] =
                orderTypes.copy(type = orderTypes.type, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderType(): Flow<List<OrderType>> {
        return getAllType()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }


    companion object {
        @Volatile
        private var instance: DucatiTypeRepository? = null

        fun getInstance(): DucatiTypeRepository =
            instance ?: synchronized(this) {
                DucatiTypeRepository().apply {
                    instance = this
                }
            }
    }
}
