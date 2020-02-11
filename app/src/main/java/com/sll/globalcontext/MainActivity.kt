package com.sll.globalcontext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sll.library.ContextProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(
            TAG,
            "currentActivity = ${ContextProvider.currentActivity()}, application = ${ContextProvider.application}, context = ${ContextProvider.globalContext}"
        )
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
