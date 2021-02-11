package com.e.brastlewark.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.e.brastlewark.R
import com.e.brastlewark.data.listener.CharacterListener
import com.e.brastlewark.domain.Brastlewark
import com.e.brastlewark.view.viewholder.CharacterViewHolder

class GnomeAdapter(val context: Context,
                   var townsList: ArrayList<Brastlewark>,
                   val itemTap: (Brastlewark) -> Unit) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var mList: MutableList<Brastlewark> = arrayListOf()
    private lateinit var mListener: CharacterListener

    var contactListFiltered: ArrayList<Brastlewark>

    init {
        contactListFiltered = townsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.gnome_list, parent, false)
        return CharacterViewHolder(context,item, contactListFiltered,itemTap, mListener )
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindData(contactListFiltered[position])
       //the list fades as it goes up and down
        holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_scale_animation)

    }

    override fun getItemCount(): Int = contactListFiltered.size

    fun attachListener(listener: CharacterListener) {
        mListener = listener
    }

    fun updateList(list: MutableList<Brastlewark>) {
        contactListFiltered.addAll(list)
        notifyDataSetChanged()
    }

// method filters the current list
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) contactListFiltered = townsList else {
                    val filteredList = ArrayList<Brastlewark>()
                    townsList
                            .filter { it.name!!.toLowerCase().contains(charString.toLowerCase()) or it.hair_color!!.contains(constraint!!) }
                            .forEach { filteredList.add(it) }
                    contactListFiltered = filteredList
                }
                return FilterResults().apply { values = contactListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactListFiltered = results?.values as ArrayList<Brastlewark>
                notifyDataSetChanged()
            }
        }
    }
                   }
