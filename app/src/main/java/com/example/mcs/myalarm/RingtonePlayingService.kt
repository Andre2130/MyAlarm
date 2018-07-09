package com.example.mcs.myalarm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.util.Log

class RingtonePlayingService : Service() {

    private var isRunning: Boolean = false
    private val context: Context? = null
    public lateinit var mMediaPlayer: MediaPlayer
    private var startId: Int = 0


    override fun onBind(intent: Intent): IBinder? {
        Log.e("MyActivity", "In the Richard service")
        return null
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var startId = startId


        val mNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent1 = Intent(this.applicationContext, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent1, 0)

        val mNotify = Notification.Builder(this)
                .setContentTitle("WAKE UP WITH THE SAUCE" + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build()

        val state = intent.extras!!.getString("extra")

        Log.e("what is going on here  ", state)

        assert(state != null)
        when (state) {
            "no" -> startId = 0
            "yes" -> startId = 1
            else -> startId = 0
        }


        if (!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start")

            /*  int min = 1;
            int max = 9;

            Random r = new Random();
            int random_number = r.nextInt(max - min + 1) + min;
            Log.e("random number is ", String.valueOf(random_number));

            if (random_number == 1) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 2) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 3) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 4) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 5) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 6) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 7) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 8) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else if (random_number == 9) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }
            else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp);
            }*/
            mMediaPlayer = MediaPlayer.create(this, R.raw.heavey_camp)

            mMediaPlayer.start()


            mNM.notify(0, mNotify)

            this.isRunning = true
            this.startId = 0

        } else if (!this.isRunning && startId == 0) {
            Log.e("if there was not sound ", " and you want end")

            this.isRunning = false
            this.startId = 0

        } else if (this.isRunning && startId == 1) {
            Log.e("if there is sound ", " and you want start")

            this.isRunning = true
            this.startId = 0

        } else {
            Log.e("if there is sound ", " and you want end")

            mMediaPlayer.stop()
            mMediaPlayer.reset()

            this.isRunning = false
            this.startId = 0
        }


        Log.e("MyActivity", "In the service")

        return Service.START_NOT_STICKY

    }


    override fun onDestroy() {
        Log.e("JSLog", "on destroy called")
        super.onDestroy()

        this.isRunning = false
    }


}
