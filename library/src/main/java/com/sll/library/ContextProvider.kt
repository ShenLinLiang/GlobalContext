package com.sll.library

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * @author Shenlinliang
 * @date 2020/1/20
 */
@SuppressLint("StaticFieldLeak")
class ContextProvider : ContentProvider() {

    companion object {
        
        lateinit var globalContext: Context

        lateinit var application: Application

        private val activityTracker = ActivityTracker()

        fun currentActivity() = activityTracker.currentActivity()
    }

    override fun onCreate(): Boolean {
        globalContext = requireNotNull(context)
        application = globalContext.applicationContext as Application
        activityTracker.beginTracking()
        return true
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = -1

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = -1

    override fun getType(uri: Uri): String? = ""

    class ActivityTracker {
        private val activities = mutableListOf<WeakReference<Activity>>()

        private val lifecycleCallbacks = object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                val remove = activities.find { it.get() === activity }
                activities.remove(remove)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activities.add(WeakReference(activity))
            }

            override fun onActivityResumed(activity: Activity) {
            }
        }

        fun beginTracking() {
            application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
        }

        fun endTracking() {
            application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
        }

        fun currentActivity(): Activity? {
            return activities.reversed().firstOrNull {
                it.get() != null
            }?.get()
        }
    }
}