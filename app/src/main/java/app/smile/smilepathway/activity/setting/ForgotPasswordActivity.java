package app.smile.smilepathway.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

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

public class ForgotPasswordActivity extends AppCompatActivity implements ResponseDelegate {


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
    @BindView(R.id.endGuideline)
    Guideline endGuideline;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.tvNeedHelp)
    TextView tvNeedHelp;
    private Intent intent;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        if (!Common.getConnectivityStatus(this))
            Utils.showInfoMsg(this, getString(R.string.internet_connection_msg));
        else if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_name_id_msg));
        } else
            serverRequestForSignin();
    }


    private void serverRequestForSignin() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.FORGOT);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        baseRequestData.setLoginApi(Constant.LOGIN_API);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().USERNAME, edtEmail.getText().toString().trim());
        baseRequestData.setApiType("forgotpassword");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.FORGOT:
                intent = new Intent(this, OtpVerifyActivity.class);
                intent.putExtra("type", "forgot");
                intent.putExtra("userData", jsondata);
                startActivity(intent);
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
