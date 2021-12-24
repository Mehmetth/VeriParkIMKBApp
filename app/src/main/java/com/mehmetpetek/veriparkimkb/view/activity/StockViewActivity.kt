package com.mehmetpetek.veriparkimkb.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.mehmetpetek.veriparkimkb.R
import com.mehmetpetek.veriparkimkb.view.fragment.*
import kotlinx.android.synthetic.main.activity_stock_view.*

class StockViewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_view)

        supportFragmentManager.beginTransaction().replace(frameLayout.id, StocksIndicesFragment()).commit()
        setSupportActionBar(toolbar)

        val actionbarDrawerTogle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawer.addDrawerListener(actionbarDrawerTogle)
        actionbarDrawerTogle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)
        navigation_view.setCheckedItem(R.id.home)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.all -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, StocksIndicesFragment(), "home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.increasing -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, IncreasingFragment()).addToBackStack("home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.decreasing -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, DecreasingFragment()).addToBackStack("home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.volume30 -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, Volume30Fragment()).addToBackStack("home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.volume50 -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, Volume50Fragment()).addToBackStack("home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.volume100 -> {
                supportFragmentManager.beginTransaction().replace(frameLayout.id, Volume100Fragment()).addToBackStack("home").commit()
                drawer.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }
}