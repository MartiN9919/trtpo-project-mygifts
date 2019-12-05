package com.example.mygifts.activity

import android.R


import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInstaller
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class AuthActivity : AppCompatActivity() {

    internal var loginButton: Button
    internal var session: PackageInstaller.Session
    internal var token: String? = null
    internal var secret: String? = null
    internal var isUserAuthorized: Boolean? = false

    internal var infoTextView: TextView

    internal var mShared: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        infoTextView = findViewById(R.id.display)
        val config = Config.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .AuthConfig(
                AuthConfig(
                    getString(R.string.CONSUMER_KEY),
                    getString(R.string.CONSUMER_SECRET)
                )
            )
            .debug(true)
            .build()
        GiftService.initialize(config)

        getSessionInfo()
        if ((!isUserAuthorized)!!) {
            loginButton = findViewById<View>(R.id.login_button)
            loginButton.setCallback(object : Callback<TwitterSession>() {
                fun success(result: Result<Session>) {
                    session = Core.getInstance().getSessionManager().getActiveSession()
                    val authToken = session.getAuthToken()
                    saveSessionInfo(authToken.token, authToken.secret)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("userId", session.getUserId())
                    startActivity(intent)

                }

                fun failure(exception: Exception) {
                    Toast.makeText(this@LoginActivity, "Ошибка авторизации!", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            session = Core.getInstance().getSessionManager().getActiveSession()
            intent.putExtra("userId", session.getUserId())
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun saveSessionInfo(curToken: String, curSecret: String) {
        mShared = getPreferences(Context.MODE_PRIVATE)
        val mEditor = mShared.edit()
        mEditor.putBoolean("isUserAuthorized", true)
        mEditor.putString("token", curToken)
        mEditor.putString("secret", curSecret)
        mEditor.commit()
    }

    private fun getSessionInfo() {
        mShared = getPreferences(Context.MODE_PRIVATE)
        val mEditor = mShared.edit()
        isUserAuthorized = mShared.getBoolean("isUserAuthorized", false)
        token = mShared.getString("token", "")
        secret = mShared.getString("secret", "")
    }

}