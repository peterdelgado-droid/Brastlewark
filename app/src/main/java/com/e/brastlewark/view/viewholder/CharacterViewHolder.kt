package com.e.brastlewark.view.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.e.brastlewark.R
import com.e.brastlewark.data.listener.CharacterListener
import com.e.brastlewark.domain.Brastlewark
import com.github.chrisbanes.photoview.PhotoView




class CharacterViewHolder(val context: Context, itemView: View, contactListFiltered: ArrayList<Brastlewark>, val itemTap: (Brastlewark) -> Unit, public val listener: CharacterListener) :
    RecyclerView.ViewHolder(itemView) {

    private var mTextName: TextView = itemView.findViewById(R.id.text_name)
    private var mTextAge: TextView = itemView.findViewById(R.id.age)
    private var mImage: PhotoView = itemView.findViewById(R.id.image)
    private var mHairColor: TextView = itemView.findViewById(R.id.hairColor)


   

    @SuppressLint("CheckResult")
    fun bindData(character: Brastlewark) {
        this.mTextName.text = character.name
        this.mTextAge.text = character.age.toString()
        this.mHairColor.text = character.hair_color

        mImage.isZoomable

        val glideUrl = GlideUrl(character.thumbnail, LazyHeaders.Builder()
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build())

        Glide.with(itemView)
                .load(glideUrl)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.error_gnome)
                        )
                        .into(mImage)


        itemView.setOnClickListener { listener.onListClick(character) }

        }
    }






