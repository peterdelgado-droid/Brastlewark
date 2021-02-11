package com.e.brastlewark.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.e.brastlewark.data.listener.APIListener
import com.e.brastlewark.data.repository.GnomeRepository
import com.e.brastlewark.domain.Brastlewark
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import timber.log.Timber


class MainViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val mCharacterRepository = GnomeRepository(application)
    private val mResponse = MutableLiveData<Brastlewark>()
    private var limit = 50
    private var offset = 0
    var characters: LiveData<Brastlewark> = mResponse

    private val job = Job()
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO + job

    fun list() {
        launch {
            try {
                val listener = object : APIListener<Brastlewark> {
                        override fun onSuccess(model: Brastlewark) {
                            mResponse.value = model

                        }
                        override fun onFailure(str: String) {
                            mResponse.value = null
                        }
                    }
                    limit.let { mCharacterRepository.getlist(listener) }
                    offset += limit
                } catch (e: Exception) {
                    Timber.e(e)

                }
            }
        }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}