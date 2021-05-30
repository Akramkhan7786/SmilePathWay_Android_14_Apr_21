package app.smile.smilepathway;


import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;

import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.ServiceGenerator;
import app.smile.smilepathway.model.ChatDetailsModel;
import app.smile.smilepathway.model.UserDataModel;
import io.socket.client.IO;
import io.socket.client.Socket;

public class SmilePathWayApplicaion extends MultiDexApplication {
    private static final String TAG = "SmilePathWayApplication";
    public static io.socket.client.Socket mSocket;
    static String streamId;
    static String mainUrl = "";
    static SmilePathWayApplicaion objectPreference = null;
    private static Context context;

    public static SmilePathWayApplicaion getObject() {
        return objectPreference;
    }

    public static Context getContext() {
        return context;
    }

    public static Socket getSocket(Context mcontext) {

        //SharePref sharePref = SharePref.getInstance(mcontext);
        if (Common.getChatDeatils(mcontext, "chatData") != null) {
            //  userId = sharePref.getUserdetail().getUserDetails().getId();
//            SocketUrl = "http://192.168.0.94:9125";
            String chatDeatils = Common.getChatDeatils(mcontext, ApiClass.CHAT_DATA);
            ChatDetailsModel chatDetailsModel = new Gson().fromJson(chatDeatils, ChatDetailsModel.class);
            String socketUrl = ServiceGenerator.CHATURL + "" + "?auth=" + chatDetailsModel.getChat_key();
            if (mSocket == null) {
                try {
                    mainUrl = socketUrl;
                    mSocket = IO.socket(mainUrl);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return mSocket;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        MultiDex.install(getApplicationContext());
        PaymentConfiguration.init(
                getApplicationContext(),
                Constant.STRIP_PAYMENT_KEY
        );

    }

    @Override
    public void onTerminate() {
        Log.e("onStop", "OnStopApplication");
        super.onTerminate();
    }
}
