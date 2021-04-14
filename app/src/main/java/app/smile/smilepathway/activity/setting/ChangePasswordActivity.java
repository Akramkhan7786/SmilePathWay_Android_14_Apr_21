package app.smile.smilepathway.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity implements ResponseDelegate {


    @BindView(R.id.tvChangePassword)
    TextView tvChangePassword;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivChange)
    ImageView ivChange;
    @BindView(R.id.tvOldPassword)
    TextView tvOldPassword;
    @BindView(R.id.edtOldPassword)
    EditText edtOldPassword;
    @BindView(R.id.ivToggleOldPassword)
    ImageView ivToggleOldPassword;
    @BindView(R.id.viewPassword)
    View viewPassword;
    @BindView(R.id.tvNewPassword)
    TextView tvNewPassword;
    @BindView(R.id.edtNewPassword)
    EditText edtNewPassword;
    @BindView(R.id.ivToggleNewPassword)
    ImageView ivToggleNewPassword;
    @BindView(R.id.viewNewPassword)
    View viewNewPassword;
    @BindView(R.id.tvConfirmPassword)
    TextView tvConfirmPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.ivToggleConfirmPassword)
    ImageView ivToggleConfirmPassword;
    @BindView(R.id.viewConfirmPassword)
    View viewConfirmPassword;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.cvDashboard)
    CardView cvDashboard;
    boolean isShow = false;
    boolean isShow1 = false;
    boolean isShow2 = false;
    private String screenType = "";
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private String userId = "";
    private Intent intent;
    private JSONObject object = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("type")) {
            screenType = getIntent().getStringExtra("type");
            if (screenType.equalsIgnoreCase("reSetPassword")) {
                tvOldPassword.setVisibility(View.GONE);
                edtOldPassword.setVisibility(View.GONE);
                ivToggleOldPassword.setVisibility(View.GONE);
                userId = getIntent().getStringExtra("user_id");
            } else {
                tvOldPassword.setVisibility(View.VISIBLE);
                edtOldPassword.setVisibility(View.VISIBLE);
                ivToggleOldPassword.setVisibility(View.VISIBLE);
                userId = getIntent().getStringExtra("user_id");
            }
        }
    }

    @OnClick({R.id.ivBack, R.id.btnSubmit, R.id.ivToggleOldPassword, R.id.ivToggleNewPassword, R.id.ivToggleConfirmPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSubmit:
                checkValidation();
                break;
            case R.id.ivToggleOldPassword:
                if (isShow) {
                    isShow = false;
                    edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivToggleOldPassword.setImageResource(R.mipmap.is_cross_icon);
                } else {
                    isShow = true;
                    edtOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivToggleOldPassword.setImageResource(R.mipmap.view);
                }
                break;
            case R.id.ivToggleNewPassword:
                if (isShow1) {
                    isShow1 = false;
                    edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivToggleNewPassword.setImageResource(R.mipmap.is_cross_icon);
                } else {
                    isShow1 = true;
                    edtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivToggleNewPassword.setImageResource(R.mipmap.view);
                }
                break;
            case R.id.ivToggleConfirmPassword:
                if (isShow2) {
                    isShow2 = false;
                    edtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivToggleConfirmPassword.setImageResource(R.mipmap.is_cross_icon);
                } else {
                    isShow2 = true;
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivToggleConfirmPassword.setImageResource(R.mipmap.view);
                }
                break;
        }
    }

    private void checkValidation() {
        if (screenType.equalsIgnoreCase("reSetPassword")) {
            if (edtNewPassword.getText().toString().trim().isEmpty()) {
                Utils.showInfoMsg(this, getString(R.string.newpasswordblank));
            } else if (edtNewPassword.getText().toString().trim().length() < 8) {
                Utils.showInfoMsg(this, getString(R.string.newpassgretheneight));
            } else if (edtConfirmPassword.getText().toString().trim().isEmpty()) {
                Utils.showInfoMsg(this, getString(R.string.confirmpasswordblank));
            } else if (edtConfirmPassword.getText().toString().trim().length() < 8) {
                Utils.showInfoMsg(this, getString(R.string.confirmpassgrethneight));
            } else if (!edtNewPassword.getText().toString().trim().equalsIgnoreCase(edtConfirmPassword.getText().toString().trim())) {
                Utils.showInfoMsg(this, getString(R.string.bothpasswordshoubsame));
            } else {
                changePasswordApi();
            }
        } else {
            if (edtOldPassword.getText().toString().trim().isEmpty()) {
                Utils.showInfoMsg(this, getString(R.string.oldpasswordblank));
            } else if (edtOldPassword.getText().toString().trim().length() < 8) {
                Utils.showInfoMsg(this, getString(R.string.oldpassgretheneight));
            } else if (edtNewPassword.getText().toString().trim().isEmpty()) {
                Utils.showInfoMsg(this, getString(R.string.newpasswordblank));
            } else if (edtNewPassword.getText().toString().trim().length() < 8) {
                Utils.showInfoMsg(this, getString(R.string.newpassgretheneight));
            } else if (edtConfirmPassword.getText().toString().trim().isEmpty()) {
                Utils.showInfoMsg(this, getString(R.string.confirmpasswordblank));
            } else if (edtConfirmPassword.getText().toString().trim().length() < 8) {
                Utils.showInfoMsg(this, getString(R.string.confirmpassgrethneight));
            } else if (!edtNewPassword.getText().toString().trim().equalsIgnoreCase(edtConfirmPassword.getText().toString().trim())) {
                Utils.showInfoMsg(this, getString(R.string.bothpasswordshoubsame));
            } else {
                changePasswordApi();
            }
        }
    }

    private void changePasswordApi() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.CHANGE_PASSWORD);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        baseRequestData.setLoginApi(Constant.LOGIN_API);

        if (screenType.equalsIgnoreCase("reSetPassword")) {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().USER_ID, userId);
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().ACTION, "resetpassword");
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PASSWORD, edtNewPassword.getText().toString().trim());
        } else {
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().USER_ID, userId);
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().ACTION, "changepassword");
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().OLDPASSWORD, edtOldPassword.getText().toString().trim());
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PASSWORD, edtNewPassword.getText().toString().trim());
        }
        baseRequestData.setApiType("resetpassword");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.CHANGE_PASSWORD:
                try {
                    object = new JSONObject(jsondata);
                    if (screenType.equalsIgnoreCase("reSetPassword")) {
                        Utils.showInfoMsg(this, object.getString("result"));
                        intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Utils.showInfoMsg(this, object.getString("result"));
                        getSharedPreferences("prefs_login", Activity.MODE_PRIVATE).edit().clear().commit();
                        Common.SetPreferencesToken(this, ApiClass.TOKEN, "");
                        Common.SetPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT, "");
                        Common.SetChatDeatils(this, ApiClass.CHAT_DATA, "");
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
