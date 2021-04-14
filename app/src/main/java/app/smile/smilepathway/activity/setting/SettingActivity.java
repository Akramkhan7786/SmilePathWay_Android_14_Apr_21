package app.smile.smilepathway.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.smile.smilepathway.R;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.apirequest.ServiceGenerator;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.UserDataModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity implements ResponseDelegate {


    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tvNotification)
    TextView tvNotification;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ll_aboutUs)
    LinearLayout llAboutUs;
    @BindView(R.id.ll_terms_service)
    LinearLayout llTermsService;
    @BindView(R.id.ll_privacy)
    LinearLayout llPrivacy;
    @BindView(R.id.switch_compat_push)
    SwitchCompat switchCompatPush;
    @BindView(R.id.ll_pushNotificationButton)
    LinearLayout llPushNotificationButton;
    @BindView(R.id.switch_compat_tWay)
    SwitchCompat switchCompatTWay;
    @BindView(R.id.ll_twayAuth)
    LinearLayout llTwayAuth;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private UserDataModel userResponse;
    private RequestedServiceDataModel requestedServiceDataModel;
    private Intent intent;
    private String notificationStatus = "";
    private String twoWayAuth = "";
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        if (getIntent().hasExtra("notification_enabled")) {
            notificationStatus = getIntent().getStringExtra("notification_enabled");
            twoWayAuth = getIntent().getStringExtra("two_way");
            if (notificationStatus.equalsIgnoreCase("true")) {
                switchCompatPush.setChecked(true);
            } else {
                switchCompatPush.setChecked(false);
            }
            if (twoWayAuth.equalsIgnoreCase("true")) {
                switchCompatTWay.setChecked(true);
            } else {
                switchCompatTWay.setChecked(false);
            }
        }
        userResponse = new Gson().fromJson(Common.getPreferences(this, "userData"), UserDataModel.class);

        switchCompatPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        notificationStatus("notification_enabled", "true");
                        switchCompatPush.setChecked(true);
                    } else {
                        notificationStatus("notification_enabled", "false");
                        switchCompatPush.setChecked(false);
                    }
                }
            }
        });
        switchCompatTWay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        notificationStatus("2FA", "true");
                        switchCompatTWay.setChecked(true);
                    } else {
                        notificationStatus("2FA", "false");
                        switchCompatTWay.setChecked(false);
                    }
                }
            }
        });

    }

    private void notificationStatus(String key, String value) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFICATIONPERMISSION);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry("key", key);
        requestedServiceDataModel.putQurry("value", value);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("update_setting");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();

    }


    @OnClick({R.id.ivBack, R.id.ll_change_pwd, R.id.ll_aboutUs, R.id.ll_terms_service, R.id.ll_privacy, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ll_change_pwd:
                intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("type", "changePassword");
                intent.putExtra("user_id", userResponse.getId());
                startActivity(intent);
                break;
            case R.id.ll_aboutUs:
                intent = new Intent(this, webViewActivity.class);
                intent.putExtra("type", getResources().getString(R.string.about_us));
                intent.putExtra("url", ServiceGenerator.ABOUTUS_URL);
                startActivity(intent);
                break;
            case R.id.ll_terms_service:
                intent = new Intent(this, webViewActivity.class);
                intent.putExtra("type", getResources().getString(R.string.terms_conditions));
                intent.putExtra("url", ServiceGenerator.TermsAndCondition_URL);
                startActivity(intent);
                break;
            case R.id.ll_privacy:
                intent = new Intent(this, webViewActivity.class);
                intent.putExtra("type", getResources().getString(R.string.privacy_policy));
                intent.putExtra("url", ServiceGenerator.PRIVACY_URL);
                startActivity(intent);
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.NOTIFICATIONPERMISSION:
                try {
                  /*  if (notificationStatus.equalsIgnoreCase("true")) {
                        notificationStatus = "false";
                    } else {
                        notificationStatus = "true";
                    }
                    if (twoWayAuth.equalsIgnoreCase("true")) {
                        twoWayAuth = "false";
                    } else {
                        twoWayAuth = "true";
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onFailure(String jsondata, String message, BaseRequestData baseRequestData) {
        try {
            JSONObject object = new JSONObject(jsondata);
            ErrorModel errorModel = new Gson().fromJson(String.valueOf(object.getJSONObject("error")), ErrorModel.class);
            if (errorModel.getError() == 400) {
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", errorModel.getErrormsg());
            } else {
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", "oops something went wrong please try again later!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
