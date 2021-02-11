package com.e.brastlewark.data.repository

import android.content.Context
import com.e.brastlewark.R
import com.e.brastlewark.data.listener.APIListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.e.brastlewark.domain.Brastlewark
import com.e.brastlewark.utils.Constants

class GnomeRepository(val context: Context): BaseRepository()  {

    private val mRemote = ApiService.createService(ApiService::class.java)

    fun getlist(listener: APIListener<Brastlewark>) {
        val call: Call<Brastlewark> = mRemote.getList()
        if (!isConnectionAvailable(context)) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        call.enqueue(object : Callback<Brastlewark> {
            override fun onFailure(call: Call<Brastlewark>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                    call: Call<Brastlewark>,
                    response: Response<Brastlewark>
            ) {
                if (response.code() != Constants.Const.SUCCESS) {
                    val validation =
                            Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }
        })
    }
}