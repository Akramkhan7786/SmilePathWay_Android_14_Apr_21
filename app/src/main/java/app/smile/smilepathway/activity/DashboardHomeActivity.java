package app.smile.smilepathway.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.smile.smilepathway.Dialog.ConfirmationDailoge;
import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.profile.EditProfileActivity;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.activity.setting.SettingActivity;
import app.smile.smilepathway.activity.setting.webViewActivity;
import app.smile.smilepathway.activity.smileappointer.AppoitmentDetailsActivity;
import app.smile.smilepathway.activity.smilecredit.SmileCreditActivity;
import app.smile.smilepathway.activity.smileestimator.EstimatorActivity;
import app.smile.smilepathway.activity.smileplan.SmilePlanActivity;
import app.smile.smilepathway.activity.smileverify.SmileVerfifyActivity;
import app.smile.smilepathway.adapter.HomeUpdatesAppoitmentAdapter;
import app.smile.smilepathway.adapter.HomeUpdatesSmilePlanAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.apirequest.ServiceGenerator;
import app.smile.smilepathway.model.ChatDetailsModel;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.HomeResponseModel;
import app.smile.smilepathway.model.UserDataModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardHomeActivity extends AppCompatActivity implements ResponseDelegate, HomeUpdatesSmilePlanAdapter.onClickItem, HomeUpdatesAppoitmentAdapter.onClickItem {

    @BindView(R.id.tvDashboard)
    TextView tvDashboard;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivUser)
    CircleImageView ivUser;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvFirstNameLastName)
    TextView tvFirstNameLastName;
    @BindView(R.id.tvUserEmail)
    TextView tvUserEmail;
    @BindView(R.id.ivEditProfile)
    ImageView ivEditProfile;
    @BindView(R.id.cvDashboard)
    CardView cvDashboard;
    @BindView(R.id.ivUserProfile)
    ImageView ivUserProfile;
    @BindView(R.id.cvProfile)
    CardView cvProfile;
    @BindView(R.id.ivCreditHistory)
    ImageView ivCreditHistory;
    @BindView(R.id.cvCreditHistory)
    CardView cvCreditHistory;
    @BindView(R.id.ivAppointment)
    ImageView ivAppointment;
    @BindView(R.id.cvAppointment)
    CardView cvAppointment;
    @BindView(R.id.ivTreatmentPlan)
    ImageView ivTreatmentPlan;
    @BindView(R.id.cvTreatmentPlan)
    CardView cvTreatmentPlan;
    @BindView(R.id.btnSetting)
    Button btnSetting;
    @BindView(R.id.btnLogOut)
    Button btnLogOut;
    @BindView(R.id.contantProfile)
    LinearLayout contantProfile;
    @BindView(R.id.constantCredit)
    LinearLayout constantCredit;
    @BindView(R.id.constantAppointment)
    LinearLayout constantAppointment;
    @BindView(R.id.constantTreatment)
    LinearLayout constantTreatment;
    @BindView(R.id.ivEstimatar)
    ImageView ivEstimatar;
    @BindView(R.id.contantEstimatar)
    LinearLayout contantEstimatar;
    @BindView(R.id.cvEstimatar)
    CardView cvEstimatar;
    @BindView(R.id.ivSmileVerify)
    ImageView ivSmileVerify;
    @BindView(R.id.constantSmileVerify)
    LinearLayout constantSmileVerify;
    @BindView(R.id.cvSmileVerify)
    CardView cvSmileVerify;
    @BindView(R.id.cvImpUpdate)
    CardView cvImpUpdate;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private UserDataModel userResponse;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private Intent intent;
    private HomeResponseModel homeResponseModel;
    private RecyclerView rvNewSmilePlan;
    private RecyclerView rvUpcomingUppoitment;
    private int notifiTotalCount = 0;
    private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getNotificationCount();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        registerReceiver(refreshReceiver, new IntentFilter("seen_notification"));
        getNotificationCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshReceiver);
    }


    private void setView() {
        try {
            if (!Common.getPreferences(DashboardHomeActivity.this, "userData").equalsIgnoreCase("0")) {
                userResponse = new Gson().fromJson(Common.getPreferences(this, "userData"), UserDataModel.class);
                if (userResponse.getProfile_image() != null && !userResponse.getProfile_image().equalsIgnoreCase("")) {
                    Glide.with(this).load(userResponse.getProfile_image())
                            .apply(new RequestOptions().placeholder(R.drawable.usertopbar).error(R.drawable.usertopbar))
                            .into(ivUser);
                }
                tvFirstNameLastName.setText(userResponse.getFirstname() + " " + userResponse.getLastname());
                tvUserName.setText("SM-" +userResponse.getUsername());
                tvUserEmail.setText(userResponse.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            setView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        homeStatus();
    }

    @OnClick({R.id.ivEditProfile, R.id.btnSetting, R.id.btnLogOut, R.id.contantProfile, R.id.constantAppointment,
            R.id.ivNotification, R.id.constantCredit, R.id.ivUser, R.id.constantTreatment, R.id.constantSmileVerify, R.id.cvImpUpdate, R.id.contantEstimatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEditProfile:
                String chatDeatils = Common.getChatDeatils(this, ApiClass.CHAT_DATA);
                ChatDetailsModel chatDetailsModel = new Gson().fromJson(chatDeatils, ChatDetailsModel.class);
                intent = new Intent(this, webViewActivity.class);
                intent.putExtra("type",chatDetailsModel.getName());
                intent.putExtra("url", ServiceGenerator.CHAT_URL+chatDetailsModel.getChat_key()+"/"+chatDetailsModel.getChat_room());
                startActivity(intent);
                break;
            case R.id.btnSetting:
                Intent intent = new Intent(this, SettingActivity.class);
                intent.putExtra("notification_enabled", homeResponseModel.getResult().getSetting().getNotification_enabled());
                intent.putExtra("two_way", homeResponseModel.getResult().getSetting().get_$2FA());
                startActivity(intent);
                break;
            case R.id.btnLogOut:
                logout();
                break;
            case R.id.contantProfile:
                Intent intent1 = new Intent(this, EditProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.constantAppointment:
                Intent intent13 = new Intent(this, AppoitmentDetailsActivity.class);
                startActivity(intent13);
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
            case R.id.constantCredit:
                Intent intent15 = new Intent(this, SmileCreditActivity.class);
                startActivity(intent15);
                break;
            case R.id.ivUser:
                Intent intent16 = new Intent(this, EditProfileActivity.class);
                startActivity(intent16);
                break;
            case R.id.constantTreatment:
                Intent intent17 = new Intent(this, SmilePlanActivity.class);
                startActivity(intent17);
                break;
            case R.id.constantSmileVerify:
                Intent intent18 = new Intent(this, SmileVerfifyActivity.class);
                startActivity(intent18);
                break;
            case R.id.cvImpUpdate:
                showHomeUpdatesNotification();
                break;
            case R.id.contantEstimatar:
                Intent intent19 = new Intent(this, EstimatorActivity.class);
                startActivity(intent19);
                break;
        }
    }

    private void logout() {
        new ConfirmationDailoge(this, getResources().getString(R.string.are_you_sure_you_want_to_logout), "No", "Yes", new ConfirmationCallBack() {
            @Override
            public void onAccept() {
                serverRequestForLogout();
            }

            @Override
            public void onDecline() {
            }
        }).show();
    }

    private void serverRequestForLogout() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.LOGOUT);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        baseRequestData.setLoginApi(Constant.LOGIN_API);
        if (TextUtils.isEmpty(Common.getPreferences(this, ApiClass.TOKEN)) ||
                Common.getPreferences(this, ApiClass.TOKEN).equalsIgnoreCase("0")) {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DEVICE_ID, FirebaseInstanceId.getInstance().getToken());
        } else {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DEVICE_ID, Common.getPreferences(this, ApiClass.TOKEN));
        }
        baseRequestData.setApiType("logout");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void homeStatus() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.DASHBOARD);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiType("home");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void getNotificationCount() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFITOTALCOUNT);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiTag(ResponseType.GET_MULTIPATH);
        baseRequestData.setApiType("notification");
        baseRequestData.setApiType2("notification_counter");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.LOGOUT:
                try {
                    getSharedPreferences("prefs_login", Activity.MODE_PRIVATE).edit().clear().commit();
                    Common.SetPreferencesToken(this, ApiClass.TOKEN, "");
                    Common.SetPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT, "");
                    Common.SetChatDeatils(this, ApiClass.CHAT_DATA, "");
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.DASHBOARD:
                try {
                    homeResponseModel = new Gson().fromJson(jsondata, HomeResponseModel.class);
                    if (homeResponseModel.getCode() == 200) {
                        if (homeResponseModel.getResult().isDisplay_check()) {
                            cvImpUpdate.setVisibility(View.VISIBLE);
                        } else {
                            cvImpUpdate.setVisibility(View.GONE);
                        }
                        Common.SetPreferences(this, "userData", new Gson().toJson(homeResponseModel.getResult().getUserdata()));
                        setView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.NOTIFITOTALCOUNT:
                try {
                    JSONObject object = new JSONObject(jsondata);
                    notifiTotalCount = Integer.parseInt(object.getString("result"));
                    if (notifiTotalCount >0) {
                        notifyCountVenue.setText(notifiTotalCount + "");
                        notifyCountVenue.setVisibility(View.VISIBLE);
                    } else {
                        notifyCountVenue.setVisibility(View.GONE);
                    }
                    Common.SetPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT, String.valueOf(notifiTotalCount));
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

    private void showHomeUpdatesNotification() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_dashboard_updates);

        rvNewSmilePlan = dialog.findViewById(R.id.rvNewSmilePlan);
        rvUpcomingUppoitment = dialog.findViewById(R.id.rvUpcomingUppoitment);
        LinearLayout invoiceLayout = dialog.findViewById(R.id.layout_invoice);
        TextView invoiceAmount = dialog.findViewById(R.id.txt_invoiceAmount);
        TextView viewButton = dialog.findViewById(R.id.tv_view);
        View view1 = dialog.findViewById(R.id.view1);
        View view2 = dialog.findViewById(R.id.view2);

        if (homeResponseModel.getResult().getInvoice_amount() != null && homeResponseModel.getResult().getInvoice_amount().getGross_amount() != null) {
            invoiceLayout.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            invoiceAmount.setText("$" + homeResponseModel.getResult().getInvoice_amount().getGross_amount());
        } else {
            view1.setVisibility(View.GONE);
            invoiceLayout.setVisibility(View.GONE);
        }
        if (homeResponseModel.getResult().getSelected_treatment_plan().size() > 0) {
            setAdapterPlanHome();
        }
        if (homeResponseModel.getResult().getUpcomming_appointment().getAppointments_arr().size() > 0) {
            setAdapterAppoitHome();
            view2.setVisibility(View.VISIBLE);
        } else {
            view2.setVisibility(View.GONE);
        }
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashboardHomeActivity.this, SmileCreditActivity.class);
                intent.putExtra("tab_status", "invoice_tab");
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void setAdapterPlanHome() {
        HomeUpdatesSmilePlanAdapter adapter = new HomeUpdatesSmilePlanAdapter(this, this, homeResponseModel.getResult().getSelected_treatment_plan());
        rvNewSmilePlan.setHasFixedSize(true);
        rvNewSmilePlan.setLayoutManager(new LinearLayoutManager(this));
        rvNewSmilePlan.setAdapter(adapter);
    }

    private void setAdapterAppoitHome() {
        HomeUpdatesAppoitmentAdapter adapter = new HomeUpdatesAppoitmentAdapter(this, this, homeResponseModel.getResult().getUpcomming_appointment().getAppointments_arr());
        rvUpcomingUppoitment.setHasFixedSize(true);
        rvUpcomingUppoitment.setLayoutManager(new LinearLayoutManager(this));
        rvUpcomingUppoitment.setAdapter(adapter);
    }

    @Override
    public void viewOnClickSmile(int pos) {
        Intent intent17 = new Intent(this, SmilePlanActivity.class);
        startActivity(intent17);
    }

    @Override
    public void viewOnClickAppointer(int pos) {
        Intent intent13 = new Intent(this, AppoitmentDetailsActivity.class);
        startActivity(intent13);
    }
}
