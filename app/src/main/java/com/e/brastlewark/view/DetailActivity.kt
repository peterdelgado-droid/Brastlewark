package com.e.brastlewark.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.e.brastlewark.R
import com.e.brastlewark.domain.Brastlewark
import com.e.brastlewark.view.adapter.GnomeAdapter
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.gnome_list.*
import kotlinx.android.synthetic.main.gnome_list.view.*

class DetailActivity : AppCompatActivity() {

    private lateinit var vWeight: String
    private lateinit var vName: String
    private lateinit var vProfession: String
    private lateinit var vFriends: String
    private lateinit var vHeight: String
    private lateinit var vImage: String
    lateinit var  mAdapter : GnomeAdapter
    lateinit var townsList: ArrayList<Brastlewark>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        townsList = ArrayList()
        mAdapter = GnomeAdapter(this, townsList, itemTap = {
        })

        val recycler = findViewById<RecyclerView>(R.id.recycler_comic)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = mAdapter

     loadDataFromActivity()
     setComponents()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            vProfession = bundle.getString("professions").toString()
            vHeight = bundle.getString("heightText").toString()
            vWeight = bundle.getString("weight").toString()
            vName = bundle.getString("name").toString()
            vFriends = bundle.getString("friends").toString()
            vImage = bundle.getString("thumbnail").toString()
        }
    }

    private fun setComponents() {
        name.text = vName
        profession.text = vProfession
        heightText.text  = vHeight
        weightText.text = vWeight
        friends.text = vFriends
        val glideUrl = GlideUrl(vImage, LazyHeaders.Builder()
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build())
        Glide.with(this)
                .load(glideUrl)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.error_gnome)
                )
                .into(imageGnome)

    }

}
