package com.firhanalisofi.ducatimototype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firhanalisofi.ducatimototype.repository.DucatiTypeRepository
import com.firhanalisofi.ducatimototype.screen.cart.CartViewModel
import com.firhanalisofi.ducatimototype.screen.detail.DetailTypeViewModel
import com.firhanalisofi.ducatimototype.screen.home.HomeViewModel

class ViewModelFactory(private val repository: DucatiTypeRepository) :
ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailTypeViewModel::class.java)) {
            return DetailTypeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}