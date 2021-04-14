package app.smile.smilepathway.activity.smileappointer;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.TreatmentAppoitmentListAdapter;
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

public class AppoitmentDetailsActivity extends AppCompatActivity implements ResponseDelegate, TreatmentAppoitmentListAdapter.onClickItem {


    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tvEditProfile)
    TextView tvEditProfile;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvSchedule)
    TextView tvSchedule;
    @BindView(R.id.tvUnSchedule)
    TextView tvUnSchedule;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.lvTabs)
    LinearLayout lvTabs;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.rvAppointmentList)
    RecyclerView rvAppointmentList;
    @BindView(R.id.tvTiming)
    TextView tvTiming;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvTimingValue)
    TextView tvTimingValue;
    @BindView(R.id.tvAmountValue)
    TextView tvAmountValue;
    @BindView(R.id.cvBottom)
    CardView cvBottom;
    @BindView(R.id.bottomCard)
    ConstraintLayout bottomCard;
    @BindView(R.id.tv_qun_unschdule)
    TextView tvQunUnschdule;
    @BindView(R.id.tv_qun_schdule)
    TextView tvQunSchdule;
    @BindView(R.id.tv_qun_history)
    TextView tvQunHistory;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    @BindView(R.id.clMainContent)
    LinearLayout clMainContent;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private TreatmentDetailsModel treatmentDetailsModel;
    private String fillter = "";

    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoitment_listing);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        fillter = "unscheduled";
        smilePlanDetails("unscheduled");
    }

    @OnClick({R.id.ivBack, R.id.tvSchedule, R.id.tvUnSchedule, R.id.tvHistory, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSchedule:
                tvSchedule.setTextColor(getResources().getColor(R.color.selector));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvHistory.setTextColor(getResources().getColor(R.color.grey_));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                fillter = "scheduled";
                smilePlanDetails("scheduled");
                break;
            case R.id.tvUnSchedule:
                tvSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.selector));
                tvHistory.setTextColor(getResources().getColor(R.color.grey_));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                fillter = "unscheduled";
                smilePlanDetails("unscheduled");
                break;
            case R.id.tvHistory:
                tvSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvHistory.setTextColor(getResources().getColor(R.color.selector));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                fillter = "previous";
                smilePlanDetails("previous");
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;

        }
    }

    private void smilePlanDetails(String plan) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.APPOITMENT_DETAILS);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().FILLTER, plan);
        baseRequestData.setApiType("appointment");
        baseRequestData.setApiType2("view_appointment");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }


    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.APPOITMENT_DETAILS:
                try {
                    treatmentDetailsModel = new Gson().fromJson(jsondata, TreatmentDetailsModel.class);
                    if (treatmentDetailsModel.getCode() == 200) {
                        tvQunUnschdule.setVisibility(View.VISIBLE);
                        tvQunSchdule.setVisibility(View.VISIBLE);
                        tvQunHistory.setVisibility(View.VISIBLE);
                        tvQunUnschdule.setText(treatmentDetailsModel.getResult().getUnscheduled_count());
                        tvQunSchdule.setText(treatmentDetailsModel.getResult().getScheduled_count());
                        tvQunHistory.setText(treatmentDetailsModel.getResult().getPrevious_count());
                        if (fillter.equalsIgnoreCase("scheduled")) {
                            setupAdapterSchdule(false,fillter);
                        } else if (fillter.equalsIgnoreCase("unscheduled")) {
                            setupAdapterSchdule(false,fillter);
                        } else {
                            setupAdapterSchdule(false,fillter);
                        }
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

    private void setupAdapterSchdule(boolean clickStatus, String fillter) {
        TreatmentAppoitmentListAdapter adapter = new TreatmentAppoitmentListAdapter(this, this, treatmentDetailsModel.getResult().getAppointments_arr(), clickStatus,fillter);
        rvAppointmentList.setHasFixedSize(true);
        rvAppointmentList.setLayoutManager(new LinearLayoutManager(this));
        rvAppointmentList.setAdapter(adapter);
    }


    @Override
    public void onClickItem(int position) {
    }

    @Override
    public void onClickPrice(int pos) {
        AlertDialogUtil.estimatorDailog(this, pos, treatmentDetailsModel.getResult());
    }

}
