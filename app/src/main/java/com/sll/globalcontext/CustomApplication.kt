package com.sll.globalcontext

import android.app.Application
import android.util.Log

/**
 * @author Shenlinliang
 * @date 2020/2/11
 */
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "CustomApplication onCreate")
    }

    companion object {
        const val TAG = "CustomApplication"
    }

}