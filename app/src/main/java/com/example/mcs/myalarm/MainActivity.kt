package com.example.mcs.myalarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    var alarmManager: AlarmManager? = null
    private var pending_intent: PendingIntent? = null

    private var alarmTimePicker: TimePicker? = null
    private var alarmTextView: TextView? = null

    private val alarm: AlarmReceiver? = null


    var inst: MainActivity? = null
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Ask for permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        } else {
            //createPlayer()
            this.context = this

            //alarm = new AlarmReceiver()
            alarmTextView = findViewById(R.id.alarmText) as TextView

            val myIntent = Intent(this.context, AlarmReceiver::class.java)

            // Get the alarm manager service
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // set the alarm to the time that you picked
            val calendar = Calendar.getInstance()

            alarmTimePicker = findViewById(R.id.alarmTimePicker) as TimePicker


            val start_alarm = findViewById(R.id.start_alarm) as Button
            start_alarm.setOnClickListener {
                calendar.add(Calendar.SECOND, 3)
                //setAlarmText("You clicked a button");

                val hour = alarmTimePicker!!.currentHour
                val minute = alarmTimePicker!!.currentMinute

                Log.e("MyActivity", "In the receiver with $hour and $minute")
                setAlarmText("You clicked a $hour and $minute")


                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker!!.currentHour)
                calendar.set(Calendar.MINUTE, alarmTimePicker!!.currentMinute)

                myIntent.putExtra("extra", "yes")
                pending_intent = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                println("pending inten should be called")
                alarmManager!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending_intent)
                println("Wake up should be called")


                // now you should change the set Alarm text so it says something nice


                setAlarmText("Alarm set to $hour:$minute")
                //Toast.makeText(getApplicationContext(), "You set the alarm", Toast.LENGTH_SHORT).show();
            }

            val stop_alarm = findViewById(R.id.stop_alarm) as Button
            stop_alarm.setOnClickListener {
                val min = 1
                val max = 9

                val r = Random()
                val random_number = r.nextInt(max - min + 1) + min
                Log.e("random number is ", random_number.toString())

                myIntent.putExtra("extra", "no")
                sendBroadcast(myIntent)

                alarmManager!!.cancel(pending_intent)
                setAlarmText("Alarm canceled")
                //setAlarmText("You clicked a " + " canceled");
            }
        }


    }

    fun setAlarmText(alarmText: String) {
        alarmTextView!!.text = alarmText
    }


    public override fun onStart() {
        super.onStart()
        inst = this
    }

    public override fun onDestroy() {
        super.onDestroy()

        Log.e("MyActivity", "on Destroy")
    }
}
