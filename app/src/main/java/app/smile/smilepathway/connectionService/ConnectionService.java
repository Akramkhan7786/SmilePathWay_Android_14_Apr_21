package app.smile.smilepathway.connectionService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import app.smile.smilepathway.SmilePathWayApplicaion;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by gakwaya on 4/28/2016.
 */
public class ConnectionService extends Service {
    private static final String TAG = "Edgilifeconnection";
    public static Context context;
    public int EventConnect = 1, EventDisconnect = 2, EventError = 3,
            ConnectionTimeOut = 4,
            StreamComment = 5, AllComment = 6, sendMessageTag = 7, threadMessageTag = 8;
    Timer timer;
    TimerTask timerTask;
    //    " http://198.74.62.156:9114/"
    int userid;
    String userids;
    //            BookingStatus = 6,
//            cancellRequest = 7, completeAlert = 8, cancelRequestDriver = 9, userUpdateLoc = 10, userdriverRide = 11, userCancel = 12, userSceduleResponse = 13, userStatusLogout = 14;
    Emitter.Listener eventOnlineLatest = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (args[0] != null) Log.e("eventOnlineLatest", args[0].toString());
        }
    };
    //Chat Handlers and Runnables
    Handler chatHandler = new Handler(Looper.getMainLooper());
    Emitter.Listener GetAllComment = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                response = args[0].toString();
                runAllComment.setRun(response, AllComment);
                chatHandler.post(runAllComment);
            }
        }
    };
    Emitter.Listener chatMessage = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                response = args[0].toString();
                runChatMessage.setRun(response, StreamComment);
                chatHandler.post(runChatMessage);
            }
        }
    };
    Emitter.Listener threadMessage = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                response = args[0].toString();
                runThreadMessage.setRun(response, threadMessageTag);
                chatHandler.post(runThreadMessage);
            }
        }
    };
    Emitter.Listener sendMessage = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                response = args[0].toString();
                runSendMessage.setRun(response, sendMessageTag);
                chatHandler.post(runSendMessage);
            }
        }
    };
    Emitter.Listener eventDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runEventDisconnect.setRun("Disconnected", EventDisconnect);
            chatHandler.post(runEventDisconnect);

        }
    };
    Emitter.Listener eventError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runEventError.setRun("FailedToConnect", EventError);
            chatHandler.post(runEventError);
        }
    };
    Emitter.Listener connectionTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runConnectionTimeOut.setRun("ConnectionTimeOut", ConnectionTimeOut);
            chatHandler.post(runConnectionTimeOut);

        }
    };
    ChatRunnable runEventConnect = new ChatRunnable(),
            runEventDisconnect = new ChatRunnable(),
            runEventError = new ChatRunnable(),
            runConnectionTimeOut = new ChatRunnable(),
            runChatMessage = new ChatRunnable(),
            runThreadMessage = new ChatRunnable(),

    runAllComment = new ChatRunnable(),
            runSendMessage = new ChatRunnable(),
            runOnlineUserLatest = new ChatRunnable();
    Emitter.Listener eventConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runEventConnect.setRun("Connected", EventConnect);
            chatHandler.post(runEventConnect);
        }
    };

    private ProgressBar progressBar;
    private Thread mThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        context = getApplicationContext();
//        startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        context = getApplicationContext();
        stop();
        start();
        return Service.START_STICKY;
        //RETURNING START_STICKY CAUSES OUR CODE TO STICK AROUND WHEN THE APP ACTIVITY HAS DIED.
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        stop();
//        stoptimertask();
    }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 10000); //
    }

    private void initializeTimerTask() {

        timerTask = new TimerTask() {

            public void run() {

                start();

            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void doSocketWork() {
        try {
            if (SmilePathWayApplicaion.getSocket(context) != null && !SmilePathWayApplicaion.getSocket(context).connected() == true) {
                Log.d(TAG, "trying socket connection");
                stop();
                SmilePathWayApplicaion.getSocket(context).on(Socket.EVENT_CONNECT, eventConnect);
                SmilePathWayApplicaion.getSocket(context).on(Socket.EVENT_DISCONNECT, eventDisconnect);
                SmilePathWayApplicaion.getSocket(context).on(Socket.EVENT_CONNECT_ERROR, eventError);
                SmilePathWayApplicaion.getSocket(context).on(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
                SmilePathWayApplicaion.getSocket(context).on("receive_message", chatMessage);
                SmilePathWayApplicaion.getSocket(context).on("send_message", sendMessage);
                SmilePathWayApplicaion.getSocket(context).on("get_thread_message", GetAllComment);
                SmilePathWayApplicaion.getSocket(context).on("thread_message", threadMessage);
                SmilePathWayApplicaion.getSocket(context).connect();
            } else {
//                Log.d(TAG, "socket already connected" + (Common.getPreferences(this,"currentlat")) + (Common.getPreferences(this,"currentlng")));
//
//                //location
////                ObjectPreference.getSocket(context).emit("update_driver_log",g)
//
//                if (Common.getPreferences(this,"currentlat") != null && Common.getPreferences(this,"currentlat") != "" && !Common.getPreferences(this,"currentlat").equalsIgnoreCase("0.0")) {
//                    JSONObject object = new JSONObject();
//                    object.put("user_id", Common.getPreferences(this,"user_id"));
//                    object.put("lat", Common.getPreferences(this,"currentlat"));
//                    object.put("lang", Common.getPreferences(this,"currentlng"));
//
//
//                    if (Common.getPreferences(this,"car_id") != null && !Common.getPreferences(this,"car_id").equalsIgnoreCase("")) {
//                        object.put("car_id", Common.getPreferences(this,"car_id"));
//                    }
//
//                    Log.d(TAG, "socket_object" + object.toString());
//
//                  ObjectPreference.getSocket(context).emit("send_message", object);
                // }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (SmilePathWayApplicaion.getSocket(context) != null) {
            Log.d("stop: ", "OnSocketStop");
            //  stoptimertask();
            // ObjectPreference.getSocket(context).emit("id", userids);
            SmilePathWayApplicaion.getSocket(context).disconnect();
            SmilePathWayApplicaion.getSocket(context).off(Socket.EVENT_CONNECT, eventConnect);
            SmilePathWayApplicaion.getSocket(context).off(Socket.EVENT_DISCONNECT, eventDisconnect);
            SmilePathWayApplicaion.getSocket(context).off(Socket.EVENT_CONNECT_ERROR, eventError);
            SmilePathWayApplicaion.getSocket(context).off(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
            SmilePathWayApplicaion.getSocket(context).off("receive_message", chatMessage);
            SmilePathWayApplicaion.getSocket(context).off("thread_message", threadMessage);
            SmilePathWayApplicaion.getSocket(context).off("send_message", sendMessage);
            SmilePathWayApplicaion.getSocket(context).off("get_thread_message", GetAllComment);
            chatHandler.removeCallbacks(runEventConnect);
            chatHandler.removeCallbacks(runEventDisconnect);
            chatHandler.removeCallbacks(runEventError);
            chatHandler.removeCallbacks(runConnectionTimeOut);
            chatHandler.removeCallbacks(runChatMessage);
            chatHandler.removeCallbacks(runAllComment);
            chatHandler.removeCallbacks(runSendMessage);
            chatHandler.removeCallbacks(runThreadMessage);

        }
    }


    private boolean isAppIsInBackground(Context context) {///false if app is not in background
        // true if app is in background
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


    public void start() {
        context = getApplicationContext();
//        if (mThread != null) {
//            mThread.interrupt();
//            mThread = null;
//        }

//        mThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
        doSocketWork();
//            }
//        });
//        mThread.start();
    }


    public class ChatRunnable implements Runnable {
        String response = "";
        int tag = -1;

        public void setRun(String response, int tag) {
            this.response = response;
            this.tag = tag;
        }

        @Override
        public void run() {
            //Log.e("Waselconnection" + tag + " ", response);


            if (tag == EventConnect) {


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(context, "EventConnect.", Toast.LENGTH_SHORT).show();

                    }
                });


                //ObjectPreference.getSocket(context).emit("id", userids);
            } else if (tag == EventDisconnect) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (tag == EventError) {
                Log.e("Waselconnection", "Failed To Connect.");
            } else if (tag == ConnectionTimeOut) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "socket Connection timeout error.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (tag == StreamComment) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        try {
                            Log.e("StreamComment", response + "");
//                            if (!jsonObject.getString("status").equalsIgnoreCase("false")) {

                            if (isAppIsInBackground(ConnectionService.this) == false) {

                                Intent intent = new Intent();
                                intent.setAction("streamComment");
                                intent.putExtra("commentList", jsonObject.toString());
                                LocalBroadcastManager.getInstance(ConnectionService.this).sendBroadcast(intent);
                            } else {
                                Log.e("isappinforground", "true else");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (tag == AllComment) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        try {
                            Log.e("StreamComment", response + "");
//                            if (!jsonObject.getString("status").equalsIgnoreCase("false")) {

                            if (isAppIsInBackground(ConnectionService.this) == false) {

                                Intent intent = new Intent();
                                intent.setAction("streamAllComment");
                                intent.putExtra("commentListAll", jsonObject.toString());
                                LocalBroadcastManager.getInstance(ConnectionService.this).sendBroadcast(intent);
                            } else {
                                Log.e("isappinforground", "true else");

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (tag == threadMessageTag) {
                Log.e("THREADMESSAGE", "inthreadMessage");
                Log.e("response", response);
            }

        }
    }
}

