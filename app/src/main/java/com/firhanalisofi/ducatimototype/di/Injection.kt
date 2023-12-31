package com.firhanalisofi.ducatimototype.di

import com.firhanalisofi.ducatimototype.repository.DucatiTypeRepository

object Injection {
    fun provideRepository(): DucatiTypeRepository {
        return DucatiTypeRepository.getInstance()
    }
}