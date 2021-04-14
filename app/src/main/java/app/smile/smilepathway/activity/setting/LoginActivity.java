package app.smile.smilepathway.activity.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.adapter.MultipalePracticeAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.LoginModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ResponseDelegate, MultipalePracticeAdapter.onClickItem {


    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.ivBackground)
    ImageView ivBackground;
    @BindView(R.id.tvLoginText)
    TextView tvLoginText;
    @BindView(R.id.topGuideline)
    Guideline topGuideline;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvHeaderTitle)
    TextView tvHeaderTitle;
    @BindView(R.id.guidelineLeft)
    Guideline guidelineLeft;
    @BindView(R.id.guidelineRight)
    Guideline guidelineRight;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.guidelineLeftNew)
    Guideline guidelineLeftNew;
    @BindView(R.id.guidelineRightNew)
    Guideline guidelineRightNew;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.endGuideline)
    Guideline endGuideline;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.viewOrLeft)
    View viewOrLeft;
    @BindView(R.id.tvOr)
    TextView tvOr;
    @BindView(R.id.viewOrRight)
    View viewOrRight;
    @BindView(R.id.ivFacebook)
    ImageView ivFacebook;
    @BindView(R.id.ivGoogle)
    ImageView ivGoogle;
    @BindView(R.id.tvCreateAccount)
    TextView tvCreateAccount;
    @BindView(R.id.tvNeedHelp)
    TextView tvNeedHelp;
    RequestedServiceDataModel requestedServiceDataModel;
    BaseRequestData baseRequestData;
    private Intent intent;
    private LoginModel userResponse;
    private RecyclerView rvMultipalPracticeList;
    private LinearLayout suggestionLayoutMain;
    private String PRACTICE_ID = "";
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        ButterKnife.bind(this);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                if (!TextUtils.isEmpty(token)) {
                    Log.d(TAG, ", retrieve token successful : " + token);
                    Common.SetPreferences(LoginActivity.this.getApplicationContext(), ApiClass.TOKEN, token);
                } else {
                    Log.w(TAG, ", token should not be null...");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //handle e
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                //handle cancel
            }
        }).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
            }
        });

    }

    @OnClick({R.id.btnLogin, R.id.tvForgotPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                /*Intent i = new Intent(this, DashboardHomeActivity.class);
                startActivity(i);*/
                checkValidation();
                break;
            case R.id.tvForgotPassword:
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkValidation() {
        if (!Common.getConnectivityStatus(LoginActivity.this))
            Utils.showInfoMsg(this, getString(R.string.internet_connection_msg));
        else if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_name_id_msg));
        } else if (edtPassword.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_password_msg));
        } else
            serverRequestForSignin();
    }

    private void serverRequestForSignin() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.SIGNIN);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        baseRequestData.setLoginApi(Constant.LOGIN_API);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().USERNAME, edtEmail.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PASSWORD, edtPassword.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PRACTICE_ID, PRACTICE_ID);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DEVICE_TYPE, ApiClass.getmApiClass().ANDROID);
        if (TextUtils.isEmpty(Common.getPreferences(this, ApiClass.TOKEN)) ||
                Common.getPreferences(this, ApiClass.TOKEN).equalsIgnoreCase("0")) {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DEVICE_ID, FirebaseInstanceId.getInstance().getToken());
        } else {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DEVICE_ID, Common.getPreferences(this, ApiClass.TOKEN));
        }
        baseRequestData.setApiType("login");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }


    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.SIGNIN:
                try {
                    userResponse = new Gson().fromJson(jsondata, LoginModel.class);
                    Common.SetChatDeatils(this, ApiClass.CHAT_DATA,  new Gson().toJson(userResponse.getResult().getChat_details()));
                    if (userResponse.getResult().getIs_multi_practice().equalsIgnoreCase("1")) {
                        if (userResponse.getResult().getPractice().size() > 1) {
                            showPraticeDialog();
                            setMultipalePraticeList();
                        } else {
                            reDirectLogin(jsondata);
                        }
                    } else {
                        reDirectLogin(jsondata);
                    }
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
                PRACTICE_ID = "";
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", errorModel.getErrormsg());
            } else {
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", "oops something went wrong please try again later!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void reDirectLogin(String jsondata) {
        if (userResponse.getResult().getIs_password_generated().equalsIgnoreCase("0")) {
            intent = new Intent(LoginActivity.this, OtpVerifyActivity.class);
            intent.putExtra("type", "login");
            intent.putExtra("userData", jsondata);
            startActivity(intent);
            finishAffinity();
        } else if (userResponse.getResult().getOtp().get_$2FA().equalsIgnoreCase("false")) {
            Common.SetPreferences(this, "userData", new Gson().toJson(userResponse.getResult().getUserdata()));
            Common.SetPreferencesToken(this, ApiClass.TOKEN, userResponse.getResult().getToken());
            intent = new Intent(LoginActivity.this, DashboardHomeActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            intent = new Intent(LoginActivity.this, OtpVerifyActivity.class);
            intent.putExtra("type", "login");
            intent.putExtra("userData", jsondata);
            startActivity(intent);
            finishAffinity();
        }
    }

    private void showPraticeDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_multipale_practice);

        suggestionLayoutMain = dialog.findViewById(R.id.mainLayoutSuggestion);
        rvMultipalPracticeList = dialog.findViewById(R.id.rvMultipalPracticeList);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void setMultipalePraticeList() {
        suggestionLayoutMain.setVisibility(View.VISIBLE);
        MultipalePracticeAdapter adapter = new MultipalePracticeAdapter(this, this, userResponse.getResult().getPractice());
        rvMultipalPracticeList.setHasFixedSize(true);
        rvMultipalPracticeList.setLayoutManager(new LinearLayoutManager(this));
        rvMultipalPracticeList.setAdapter(adapter);
    }

    @Override
    public void itemOnClick(int position) {
        PRACTICE_ID = userResponse.getResult().getPractice().get(position).getPractice_id();
        dialog.dismiss();
        serverRequestForSignin();
    }
}
