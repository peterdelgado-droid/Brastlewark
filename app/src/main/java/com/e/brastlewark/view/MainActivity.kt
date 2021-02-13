package com.e.brastlewark.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.brastlewark.R
import com.e.brastlewark.data.listener.CharacterListener
import com.e.brastlewark.domain.Brastlewark
import com.e.brastlewark.view.adapter.GnomeAdapter
import com.e.brastlewark.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mListener: CharacterListener
    lateinit var  mAdapter : GnomeAdapter
    lateinit var searchView: SearchView
    lateinit var townsList: ArrayList<Brastlewark>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        townsList = ArrayList()
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mAdapter = GnomeAdapter(this, townsList, itemTap = {
        })

        val recycler = findViewById<RecyclerView>(R.id.recycler_character)
        recycler.layoutManager = GridLayoutManager(this,1)
        recycler.adapter = mAdapter

        mListener = getListener(recycler)
        mListener.let {
            recycler.addOnScrollListener(it)
        }
        mViewModel.characters.observe(this, {
         mAdapter.updateList(it.brastlewark.toMutableList())
            Toast.makeText(this@MainActivity, "Double-click on pic of Gnome to Enlarge!!", Toast.LENGTH_LONG).show()

            progress_bar.visibility = View.INVISIBLE
            text_progress.visibility = View.INVISIBLE
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = "Find Gnomes"
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Int.MAX_VALUE

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    mAdapter.getFilter().filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    mAdapter.getFilter().filter(newText)
                    return false
                }
            })
        }
        return true
    }



    private fun getListener(recycler: RecyclerView): CharacterListener {
        return object : CharacterListener() {
            override fun onListClick(character: Brastlewark) {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("name", character.name)
                bundle.putString("heightText", character.height.toString())
                bundle.putString("professions", character.professions.toString())
                bundle.putString("weight", character.weight.toString())
                bundle.putString("friends", character.friends.toString())
                bundle.putString("thumbnail", character.thumbnail)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.list()
    }

}