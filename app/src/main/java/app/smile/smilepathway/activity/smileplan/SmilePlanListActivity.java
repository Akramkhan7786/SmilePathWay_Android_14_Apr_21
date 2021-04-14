package app.smile.smilepathway.activity.smileplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.SmilePlanListAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.TreatmentDetailsModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmilePlanListActivity extends AppCompatActivity implements ResponseDelegate, SmilePlanListAdapter.onClickItem {


    @BindView(R.id.tvTreatment)
    TextView tvTreatment;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvTreatment)
    RecyclerView rvTreatment;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private TreatmentDetailsModel smilePlanModel;
    private String pmsID = "";
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        if (getIntent().hasExtra("pms_patient_id")) {
            pmsID = getIntent().getStringExtra("pms_patient_id");
        }
        smilePlanDetails();
    }

    private void smilePlanDetails() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.TREATMENTDETAIL);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_ID, pmsID);
        baseRequestData.setApiType("treatment");
        baseRequestData.setApiType2("view_treatment_detail");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.TREATMENTDETAIL:
                try {
                    smilePlanModel = new Gson().fromJson(jsondata, TreatmentDetailsModel.class);
                    if (smilePlanModel.getCode() == 200) {
                        setupAdapter(smilePlanModel);
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
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", errorModel.getErrormsg());
            } else {
                AlertDialogUtil.showCustomDialogApiResult(this, "SmilePathway", "oops something went wrong please try again later!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupAdapter(TreatmentDetailsModel smilePlanModel) {
        SmilePlanListAdapter adapter = new SmilePlanListAdapter(this, this, smilePlanModel.getResult().getAppointments_arr());
        rvTreatment.setHasFixedSize(true);
        rvTreatment.setLayoutManager(new LinearLayoutManager(this));
        rvTreatment.setAdapter(adapter);
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

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(this, SmilePlanDetailsActivity.class);
        intent.putExtra("pms_patient_id", smilePlanModel.getResult().getAppointments_arr().get(position).getPms_treatment_id());
        intent.putExtra("tab_status", smilePlanModel.getResult().getAppointments_arr().get(position).getAppointment_status());
        startActivity(intent);
    }

    @Override
    public void onClickPrice(int pos) {
        AlertDialogUtil.estimatorDailog(this, pos, smilePlanModel.getResult());

    }
}
