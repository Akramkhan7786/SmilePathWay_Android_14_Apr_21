package app.smile.smilepathway.connectionService;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by gakwaya on 4/28/2016.
 */
public class SocketConnectionClass {
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
    Activity activity;
    Socket mSocket;

    //
    private ProgressBar progressBar;
    private Thread mThread;

    public void doSocketWork(Activity activity, Socket mSocket) {
        try {

            this.activity = activity;
            this.mSocket = mSocket;
            if (mSocket != null && !mSocket.connected() == true) {
                Log.d(TAG, "trying socket connection");
                stop();
                mSocket.on(Socket.EVENT_CONNECT, eventConnect);
                mSocket.on(Socket.EVENT_DISCONNECT, eventDisconnect);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, eventError);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
                mSocket.on("receive_message", chatMessage);
                mSocket.on("send_message", sendMessage);
                mSocket.on("get_thread_message", GetAllComment);
                mSocket.on("thread_message", threadMessage);
                mSocket.connect();
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
        if (mSocket != null) {
            Log.d("stop: ", "OnSocketStop");
            //  stoptimertask();
            // ObjectPreference.getSocket(context).emit("id", userids);
            mSocket.disconnect();
            mSocket.off(Socket.EVENT_CONNECT, eventConnect);
            mSocket.off(Socket.EVENT_DISCONNECT, eventDisconnect);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, eventError);
            mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
            mSocket.off("receive_message", chatMessage);
            mSocket.off("thread_message", threadMessage);
            mSocket.off("send_message", sendMessage);
            mSocket.off("get_thread_message", GetAllComment);
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
                        try {
                            Toast.makeText(context, "socket Connection timeout error.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } else if (tag == StreamComment) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        try {
                            Log.e("StreamComment", response + "");
//                            if (!jsonObject.getString("status").equalsIgnoreCase("false")) {

                            if (isAppIsInBackground(activity) == false) {

                                Intent intent = new Intent();
                                intent.setAction("streamComment");
                                intent.putExtra("commentList", jsonObject.toString());
                                LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
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

                            if (isAppIsInBackground(activity) == false) {

                                Intent intent = new Intent();
                                intent.setAction("streamAllComment");
                                intent.putExtra("commentListAll", jsonObject.toString());
                                LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
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

