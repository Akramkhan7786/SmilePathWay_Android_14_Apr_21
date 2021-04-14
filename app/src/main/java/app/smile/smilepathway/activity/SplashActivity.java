package app.smile.smilepathway.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.apirequest.Common;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "Home Pad";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 124;
    @BindView(R.id.img_splash)
    GifImageView imgSplash;
    private SplashActivity activity;
    private String pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        ButterKnife.bind(this);
        activity = SplashActivity.this;

        Glide.with(this)
                .load(R.drawable.splash_bg)
                .into(imgSplash);

        pref = Common.getPreferences(activity, "userData");
        //location class
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("1234", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("1234", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("1234", "printHashKey()", e);
        }
        handlerActivity();
    }

    public void handlerActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Log.e("userid>>>>>",utils.getPreferences(SplashActivity.this, "userid"));

                if (pref != null && (!pref.equalsIgnoreCase("")) && (!pref.equalsIgnoreCase("0"))) {
                    Intent i = new Intent(SplashActivity.this, DashboardHomeActivity.class);
                    startActivity(i);
                    finishAffinity();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finishAffinity();
                }
            }
        }, 6000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
