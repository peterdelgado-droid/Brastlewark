package com.e.brastlewark.data.listener
import androidx.recyclerview.widget.RecyclerView
import com.e.brastlewark.domain.Brastlewark

abstract class CharacterListener : RecyclerView.OnScrollListener() {
    abstract fun onListClick(character: Brastlewark)
}