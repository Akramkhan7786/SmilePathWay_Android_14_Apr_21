package app.smile.smilepathway.activity.smileverify;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import app.smile.smilepathway.model.SmileVerifyModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmileVerfifyActivity extends AppCompatActivity implements ResponseDelegate {


    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tvCreditHistory)
    TextView tvCreditHistory;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tv_annualmax)
    TextView tvAnnualmax;
    @BindView(R.id.tv_orthomax)
    TextView tvOrthomax;
    @BindView(R.id.tv_fir)
    TextView tvFir;
    @BindView(R.id.tv_sec)
    TextView tvSec;
    @BindView(R.id.tv_thi)
    TextView tvThi;
    @BindView(R.id.tv_fou)
    TextView tvFou;
    @BindView(R.id.tv_fif)
    TextView tvFif;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_sev)
    TextView tvSev;
    @BindView(R.id.tv_eat)
    TextView tvEat;
    @BindView(R.id.tv_nin)
    TextView tvNin;
    @BindView(R.id.tv_ten)
    TextView tvTen;
    @BindView(R.id.clMainContent)
    LinearLayout clMainContent;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;

    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    @BindView(R.id.tv_profile_message)
    TextView tvProfileMessage;
    @BindView(R.id.ll_messageLayout)
    LinearLayout llMessageLayout;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private SmileVerifyModel smileVerifyModel;
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_verify);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        smileVerifyDetials();
    }


    @OnClick({R.id.ivBack, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }


    private void smileVerifyDetials() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.GET_SMILE_VERIFIY);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiType("smile_verify");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.GET_SMILE_VERIFIY:
                try {
                    smileVerifyModel = new Gson().fromJson(jsondata, SmileVerifyModel.class);
                    if (smileVerifyModel.getCode() == 200) {
                        setViewData(smileVerifyModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setViewData(SmileVerifyModel smileVerifyModel) {
        clMainContent.setVisibility(View.VISIBLE);
        if (smileVerifyModel.getResult().getMessage() != null) {
            if (!smileVerifyModel.getResult().getMessage().equalsIgnoreCase("")) {
                tvProfileMessage.setVisibility(View.VISIBLE);
                tvProfileMessage.setText(smileVerifyModel.getResult().getMessage());
            } else {
                tvProfileMessage.setVisibility(View.GONE);
            }
        }
        if (smileVerifyModel.getResult().getAnnual() != null) {
            tvAnnualmax.setText(smileVerifyModel.getResult().getAnnual());
        } else {
            tvAnnualmax.setText("0%");
        }
        if (smileVerifyModel.getResult().getOrtho_lifetime() != null) {
            tvOrthomax.setText(smileVerifyModel.getResult().getOrtho_lifetime());
        } else {
            tvOrthomax.setText("0%");
        }
        tvFir.setText(smileVerifyModel.getResult().getDiagnostic());
        tvSec.setText(smileVerifyModel.getResult().getPreventive());
        tvThi.setText(smileVerifyModel.getResult().getRestroative_direct());
        tvFou.setText(smileVerifyModel.getResult().getRestroative_indirect());
        tvFif.setText(smileVerifyModel.getResult().getEndo());
        tvSex.setText(smileVerifyModel.getResult().getPerio());
        tvSev.setText(smileVerifyModel.getResult().getOral_surgery());
        tvEat.setText(smileVerifyModel.getResult().getProsth_fixed());
        tvNin.setText(smileVerifyModel.getResult().getProsth_removable());
        tvTen.setText(smileVerifyModel.getResult().getOrtho());
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
