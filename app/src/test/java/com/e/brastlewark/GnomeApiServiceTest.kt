package com.e.brastlewark

import com.e.brastlewark.data.repository.ApiService
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GnomeApiServiceTest {

    private val testSubject = ApiService

    @Test
    @Throws(Exception::class)
    fun getList() {
        Assert.assertNotNull(getList())
        }
}


@Test
@Throws(java.lang.Exception::class)
fun testHttpInterceptor() {
    val mockWebServer = MockWebServer()
    mockWebServer.start()
    mockWebServer.enqueue(MockResponse())
    val okHttpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
    okHttpClient.build()
    val request: RecordedRequest = mockWebServer.takeRequest()
    assertEquals(Locale.getDefault().getLanguage(), request.getHeader("Accept-Language"))
    assertEquals(ApiService, request.getHeader("Accept"))
    mockWebServer.shutdown()
}

