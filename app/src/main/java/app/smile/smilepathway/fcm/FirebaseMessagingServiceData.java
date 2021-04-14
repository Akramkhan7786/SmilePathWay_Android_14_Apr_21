package app.smile.smilepathway.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.SplashActivity;
import app.smile.smilepathway.activity.smileappointer.AppoitmentDetailsActivity;
import app.smile.smilepathway.activity.smilecredit.SmileCreditActivity;
import app.smile.smilepathway.activity.smileplan.SmilePlanActivity;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.model.DictionaryData;
import app.smile.smilepathway.utils.Utils;

public class FirebaseMessagingServiceData extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingServiceData.class.getSimpleName();
    String type, message, title;

    DictionaryData dictionaryData;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("firebaseToken", token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server
        Common.SetPreferences(getApplicationContext(), ApiClass.TOKEN, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("onMessageReceived", "called");
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // if need for notification data object direct message user title and body
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        JSONObject data = new JSONObject();
        try {
            JSONObject dictionary = new JSONObject(data.getString("dictionary"));
            Log.e("pushNotificationData", dictionary.toString());
            dictionaryData = new Gson().fromJson(String.valueOf(dictionary), DictionaryData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Message data payload: " + remoteMessage.getData());
                data = new JSONObject(remoteMessage.getData());
                title = data.getString("title");
                type = data.getString("type");
                message = data.getString("message");
                showNotification(type, message, title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String type, String message, String title) {
        NotificationChannel mChannel = null;
        int notifyID = 1;
        Intent notificationIntent = null;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "SmilePathway";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        NotificationManager manager;
        if (type.equalsIgnoreCase("smileplan")) {
            notificationIntent = new Intent(getApplicationContext(), SmilePlanActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (type.equalsIgnoreCase("treatment")) {
            notificationIntent = new Intent(getApplicationContext(), AppoitmentDetailsActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (type.equalsIgnoreCase("invoice")) {
            notificationIntent = new Intent(getApplicationContext(), SmileCreditActivity.class);
            notificationIntent.putExtra("tab_status", "invoice_tab");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)
                System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
   /*     PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.orange))
                .setSmallIcon(R.mipmap.logo)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(mChannel);
        } else {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        PowerManager.WakeLock screenOn = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "example");
        screenOn.acquire();
        manager.notify((int) Calendar.getInstance().getTimeInMillis(), builder.build());
        Intent intent1 = new Intent("seen_notification");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

        if (!isAppIsInBackground(getApplicationContext())) {
            Log.e("isAppIsInBackground", "called");
          /*  Intent intent = new Intent(getApplicationContext(), NotificaitonBroadcast.class);
            intent.setAction("com.sowgood.NOTIFICATION");
            intent.putExtra("message", message);
            getApplicationContext().sendBroadcast(intent);*/
            Intent intent = new Intent("seen_notification");
            getApplicationContext().sendBroadcast(intent);
        }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}
