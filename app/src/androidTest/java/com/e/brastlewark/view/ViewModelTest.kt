package com.e.brastlewark.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.e.brastlewark.GnomesApplication
import com.e.brastlewark.di.repositoriesModule
import com.e.brastlewark.di.useCasesModule
import com.e.brastlewark.di.viewModelModule
import com.e.brastlewark.domain.Brastlewark
import com.e.brastlewark.domain.usecase.GetList
import com.e.brastlewark.utils.Data
import com.e.brastlewark.viewmodel.MainViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.e.brastlewark.utils.Result
import com.e.brastlewark.utils.Status


private const val VALID_ID = 33
private const val INVALID_ID = -1
private const val PAGE_VALID = 1
private const val PER_PAGE_VALID = 80
private const val PER_PAGE_INVALID = 100
private const val NAME_SEARCH_VALID = "e"

class ViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")


    private lateinit var subject: MainViewModel

    @Mock
    lateinit var gnomeValidResult: Result.Success<List<Brastlewark>>

    @Mock
    lateinit var gnomeInvalidResult: Result.Failure

    @Mock
    lateinit var gnomes: List<Brastlewark>

    @Mock
    lateinit var exception: Exception

    private val getGnomeListUseCase: GetList by inject()
    private val context: Context by inject()


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            androidContext(GnomesApplication())
            modules(
                    listOf(repositoriesModule, viewModelModule, useCasesModule)
                ) }

        declareMock<GetList>()

        MockitoAnnotations.initMocks(this)
        subject = MainViewModel(
                context as Application
        )
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }




    @Test
    fun getGnomeListSuccess() {
        Mockito.`when`(gnomeValidResult.data).thenReturn(gnomes)
        runBlocking {
            subject.list()
        }
        val liveDataUnderTest = subject.characters.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.SUCCESSFUL, data = gnomeValidResult.data))
    }

    @Test
    fun getGnomeListFailure() {
        Mockito.`when`(gnomeValidResult.data).thenReturn(gnomes)
        runBlocking {
            subject.list()
        }
        val liveDataUnderTest = subject.characters.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
                .isEqualTo(Data(responseType = Status.ERROR, data = null, error = gnomeInvalidResult.exception))
    }


    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }
}