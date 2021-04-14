package app.smile.smilepathway.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.google.gson.Gson;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.model.ForgotModel;
import app.smile.smilepathway.model.LoginModel;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpVerifyActivity extends AppCompatActivity {


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
    @BindView(R.id.guidelineLeftNew)
    Guideline guidelineLeftNew;
    @BindView(R.id.guidelineRightNew)
    Guideline guidelineRightNew;
    @BindView(R.id.edtPassword1)
    EditText edtPassword1;
    @BindView(R.id.edtPassword2)
    EditText edtPassword2;
    @BindView(R.id.edtPassword3)
    EditText edtPassword3;
    @BindView(R.id.edtPassword4)
    EditText edtPassword4;
    @BindView(R.id.endGuideline)
    Guideline endGuideline;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.tvNeedHelp)
    TextView tvNeedHelp;
    @BindView(R.id.edtPassword31)
    EditText edtPassword31;
    @BindView(R.id.edtPassword32)
    EditText edtPassword32;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private Intent intent;
    private LoginModel userResponse;
    private ForgotModel forgotModel;
    private String screenType = "";
    private String userData = "";
    private String forgotData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("type")) {
            screenType = getIntent().getStringExtra("type");
            if (screenType.equalsIgnoreCase("login")) {
                userData = getIntent().getStringExtra("userData");
            } else {
                forgotData = getIntent().getStringExtra("userData");
            }
        }
        setOtpEdit();
    }

    private void setOtpEdit() {

        edtPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword1.getText().toString().trim().length() > 0) {
                    edtPassword1.clearFocus();
                    edtPassword2.requestFocus();
                }
            }
        });
        edtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword2.getText().toString().trim().length() > 0) {
                    edtPassword2.clearFocus();
                    edtPassword3.requestFocus();
                }
                if (edtPassword2.getText().toString().trim().equals("")) {
                    edtPassword2.clearFocus();
                    edtPassword1.requestFocus();
                }

            }
        });
        edtPassword3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword3.getText().toString().trim().length() > 0) {
                    edtPassword3.clearFocus();
                    edtPassword31.requestFocus();
                }
                if (edtPassword3.getText().toString().trim().equals("")) {
                    edtPassword3.clearFocus();
                    edtPassword2.requestFocus();
                }
            }
        });
        edtPassword31.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword31.getText().toString().trim().length() > 0) {
                    edtPassword31.clearFocus();
                    edtPassword32.requestFocus();
                }
                if (edtPassword31.getText().toString().trim().equals("")) {
                    edtPassword31.clearFocus();
                    edtPassword3.requestFocus();
                }
            }
        });
        edtPassword32.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword32.getText().toString().trim().length() > 0) {
                    edtPassword32.clearFocus();
                    edtPassword4.requestFocus();
                }
                if (edtPassword32.getText().toString().trim().equals("")) {
                    edtPassword32.clearFocus();
                    edtPassword31.requestFocus();
                }
            }
        });
        edtPassword4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword4.getText().toString().trim().equals("")) {
                    edtPassword4.clearFocus();
                    edtPassword32.requestFocus();
                }
            }
        });
    }

    @OnClick({R.id.btnSubmit, R.id.tvForgotPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                checkValidation();
                break;
            case R.id.tvForgotPassword:
                Intent inte = new Intent(this, ForgotPasswordActivity.class);
                startActivity(inte);
                finish();
                break;
        }
    }

    private void checkValidation() {
        if (!Common.getConnectivityStatus(OtpVerifyActivity.this))
            Utils.showInfoMsg(this, getString(R.string.internet_connection_msg));
        else if (edtPassword1.getText().toString().trim().isEmpty()) {
            Utils.showInfoMsg(this, getString(R.string.otpvalueblank));
        } else if (edtPassword2.getText().toString().trim().isEmpty()) {
            Utils.showInfoMsg(this, getString(R.string.otpvalueblank));
        } else if (edtPassword3.getText().toString().trim().isEmpty()) {
            Utils.showInfoMsg(this, getString(R.string.otpvalueblank));
        } else if (edtPassword4.getText().toString().trim().isEmpty()) {
            Utils.showInfoMsg(this, getString(R.string.otpvalueblank));
        } else {
            checkOtpValidate();
        }
    }

    private void checkOtpValidate() {
        if (screenType.equalsIgnoreCase("login")) {
            userResponse = new Gson().fromJson(userData, LoginModel.class);
            if (userResponse.getResult().getIs_password_generated().equalsIgnoreCase("0")) {
                intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("type", "reSetPassword");
                intent.putExtra("user_id", userResponse.getResult().getUserdata().getId());
                // send user id to reset password dynamic from forgot password
                startActivity(intent);
            } else if (userResponse.getResult().getOtp().getOtp_code().
                    equalsIgnoreCase(
                            edtPassword1.getText().toString() +
                                    edtPassword2.getText().toString() +
                                    edtPassword3.getText().toString() +
                                    edtPassword31.getText().toString() +
                                    edtPassword32.getText().toString() +
                                    edtPassword4.getText().toString())) {
                //Common.SetPreferences(this, "userData", userData);
                //login save another value
                Common.SetPreferences(this, "userData", new Gson().toJson(userResponse.getResult().getUserdata()));
                Common.SetPreferencesToken(this, ApiClass.TOKEN, userResponse.getResult().getToken());
                intent = new Intent(this, DashboardHomeActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                Utils.showInfoMsg(this, getString(R.string.valid_otp));
            }
        } else {
            forgotModel = new Gson().fromJson(forgotData, ForgotModel.class);
            if (forgotModel.getResult().getOtp().getOtp_code().equalsIgnoreCase(
                    edtPassword1.getText().toString() +
                            edtPassword2.getText().toString() +
                            edtPassword3.getText().toString() +
                            edtPassword31.getText().toString() +
                            edtPassword32.getText().toString() +
                            edtPassword4.getText().toString())) {
                intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("type", "reSetPassword");
                intent.putExtra("user_id", forgotModel.getResult().getId());
                startActivity(intent);
            } else {
                Utils.showInfoMsg(this, getString(R.string.valid_otp));
            }
        }
    }

}
