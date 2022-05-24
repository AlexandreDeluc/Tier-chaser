package fr.isen.deluc.tierchaser

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.*
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "fr.isen.deluc.tierchaser"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private  lateinit var database: DatabaseReference

    @RequiresApi(VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if(remoteMessage.getNotification() != null) {
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }

    }

    @RequiresApi(VERSION_CODES.O)
    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, FilActuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // On utilise cet intent qu'une fois (flag_on-shot)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // On construit la notification depuis le drawable
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true) // on enlève la notif en cliquant dessus
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message)) // On construit la notification n appelant le builder

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews("fr.isen.deluc.tierchaser", R.layout.notification)

        // on remplace le titre et la description par ce qui nous intéresse
        remoteView.setTextViewText(R.id.notif_title, title)
        remoteView.setTextViewText(R.id.notif_description, message)
        remoteView.setImageViewResource(R.id.notif_logo, R.drawable.logo)

        return remoteView
    }


}