package com.example.mcs.myalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.extras!!.getString("extra")
        Log.e("MyActivity", "In the receiver with " + state!!)

        val serviceIntent = Intent(context, RingtonePlayingService::class.java)
        serviceIntent.putExtra("extra", state)

        context.startService(serviceIntent)
    }
}