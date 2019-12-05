package com.example.mygifts.activity

import android.R

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem

class GiftActivity : TimelineActivity(), SwipeRefreshLayout.OnRefreshListener {
    protected var swipeHome: SwipeRefreshLayout

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        loadContacts()

        swipeHome = findViewById(R.id.swipe_home) as SwipeRefreshLayout
        swipeHome.setOnRefreshListener(this)
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.info_menu, menu)
        return true
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.action_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.action_add_tweet) {
            val session = getInstance().getSessionManager()
                .getActiveSession()
            val intent = ComposerActivity.Builder(this@StandardTimelineActivity)
                .session(session)
                .darkTheme()
                .createIntent()
            startActivity(intent)
        }
        return true
    }

    override fun onRefresh() {
        Handler().postDelayed({
            initTask()
            loadContacts()
            swipeHome.isRefreshing = false
        }, 800)
    }
}