package com.e.brastlewark.domain.usecase

import com.e.brastlewark.data.listener.APIListener
import com.e.brastlewark.data.repository.GnomeRepository
import com.e.brastlewark.domain.Brastlewark
import org.koin.core.KoinComponent

class GetList(private val gnomeRepository: GnomeRepository) : KoinComponent {

    operator fun invoke(listener: APIListener<Brastlewark>) = gnomeRepository.getlist(listener)}