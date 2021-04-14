package app.smile.smilepathway.activity.smileplan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
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

import app.smile.smilepathway.Dialog.ApiResultDialog;
import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.BookingSuggestionAdapter;
import app.smile.smilepathway.adapter.TreatmentAppoitmentListAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.BookingModel;
import app.smile.smilepathway.model.BookingSuggestionModel;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.TreatmentDetailsModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmilePlanDetailsActivity extends AppCompatActivity implements ResponseDelegate, TreatmentAppoitmentListAdapter.onClickItem, BookingSuggestionAdapter.onClickItem {


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
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private TreatmentDetailsModel treatmentDetailsModel;
    private BookingSuggestionModel bookingSuggestionModel;
    private String pmsID = "";
    private String fillter = "";
    private RecyclerView rvSuggestionList;
    private LinearLayout suggestionLayoutMain;
    private String tabStatus;
    private RecyclerView rvEstimator;
    private Dialog dialog;
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_listing);
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
        if (getIntent().hasExtra("tab_status")) {
            tabStatus = getIntent().getStringExtra("tab_status");
            if (tabStatus.equalsIgnoreCase("0") || tabStatus.equalsIgnoreCase("1")) {
                tvSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.selector));
                tvHistory.setTextColor(getResources().getColor(R.color.grey_));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                fillter = "unscheduled";
                smilePlanDetails("unscheduled");

            } else if (tabStatus.equalsIgnoreCase("2")) {
                tvSchedule.setTextColor(getResources().getColor(R.color.selector));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvHistory.setTextColor(getResources().getColor(R.color.grey_));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                fillter = "scheduled";
                smilePlanDetails("scheduled");

            } else if (tabStatus.equalsIgnoreCase("3")) {
                tvSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvUnSchedule.setTextColor(getResources().getColor(R.color.grey_));
                tvHistory.setTextColor(getResources().getColor(R.color.selector));

                tvSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvUnSchedule.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvHistory.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                fillter = "previous";
                smilePlanDetails("previous");
            }
        } else {
            fillter = "unscheduled";
            smilePlanDetails("unscheduled");
        }
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

    private void showSuggestionDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_booking_appoitment);

        suggestionLayoutMain = dialog.findViewById(R.id.mainLayoutSuggestion);
        TextView yes = dialog.findViewById(R.id.tv_booking);
        rvSuggestionList = dialog.findViewById(R.id.rvSuggestionList);

        dialog.show();
        yes.setOnClickListener(v -> {
            dialog.dismiss();
            anotherBookingDialog();
        });
        dialog.setCanceledOnTouchOutside(true);
    }

    private void anotherBookingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialauge_report);

        TextView editInput = dialog.findViewById(R.id.edit_comment);
        Button submit = dialog.findViewById(R.id.btn_report);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        dialog.show();
        submit.setOnClickListener(v -> {
            if (!editInput.getText().toString().equalsIgnoreCase("")) {
                dialog.dismiss();
                anotherBookingAppoitment(editInput.getText().toString().trim());
            } else {
                Utils.showInfoMsg(this, getResources().getString(R.string.enter_message));
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void smilePlanDetails(String plan) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.TREATMENTDETAIL);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_ID, pmsID);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().FILLTER, plan);
        baseRequestData.setApiType("treatment");
        baseRequestData.setApiType2("view_treatment_detail");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void bookingSuggestion(int position) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.APPOITMENT_SUGGESTION);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().ID, treatmentDetailsModel.getResult().getAppointments_arr().get(position).getId());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_ID, treatmentDetailsModel.getResult().getAppointments_arr().get(position).getPms_treatment_id());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_PTNT_ID, treatmentDetailsModel.getResult().getAppointments_arr().get(position).getPms_patient_id());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().APPOITMENT_NUMBER, treatmentDetailsModel.getResult().getAppointments_arr().get(position).getAppointment_number());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().APPOITMENT_TIME, treatmentDetailsModel.getResult().getAppointments_arr().get(position).getAppointment_time());
        baseRequestData.setApiType("treatment");
        baseRequestData.setApiType2("book_appointment_suggestion");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void appoitmentBooking(int position) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.BOOKING_APPOITMENT);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_ID, bookingSuggestionModel.getResult().getPms_treatment_id());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PMS_PTNT_ID, bookingSuggestionModel.getResult().getPms_patient_id());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().APPOITMENT_NUMBER, bookingSuggestionModel.getResult().getAppointment_number());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().VALUE, bookingSuggestionModel.getResult().getSuggestion().get(position));
        baseRequestData.setApiType("treatment");
        baseRequestData.setApiType2("book_appointment");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void anotherBookingAppoitment(String message) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.ANOTHER_BOOKING_APPOITMENT);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().MESSAGE, message);
        baseRequestData.setApiType("treatment");
        baseRequestData.setApiType2("another_appointment");
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
                    treatmentDetailsModel = new Gson().fromJson(jsondata, TreatmentDetailsModel.class);
                    if (treatmentDetailsModel.getCode() == 200) {
                        tvQunUnschdule.setVisibility(View.VISIBLE);
                        tvQunSchdule.setVisibility(View.VISIBLE);
                        tvQunHistory.setVisibility(View.VISIBLE);
                        tvQunUnschdule.setText(treatmentDetailsModel.getResult().getUnscheduled_count());
                        tvQunSchdule.setText(treatmentDetailsModel.getResult().getScheduled_count());
                        tvQunHistory.setText(treatmentDetailsModel.getResult().getPrevious_count());
                        if (fillter.equalsIgnoreCase("scheduled")) {
                            setupAdapterSchdule(false, fillter);
                        } else if (fillter.equalsIgnoreCase("unscheduled")) {
                            setupAdapterSchdule(true, fillter);
                        } else {
                            setupAdapterSchdule(false, fillter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.APPOITMENT_SUGGESTION:
                try {
                    bookingSuggestionModel = new Gson().fromJson(jsondata, BookingSuggestionModel.class);
                    if (bookingSuggestionModel.getCode() == 200) {
                        setViewBookingSuggestion();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ResponseType.BOOKING_APPOITMENT:
                try {
                    BookingModel bookingModel = new Gson().fromJson(jsondata, BookingModel.class);
                    //AlertDialogUtil.showCustomDialognew(this, "SmilePathway", bookingModel.getResult().getMessage());
                    new ApiResultDialog(this, "", bookingModel.getResult().getMessage(), "OK", "", new ConfirmationCallBack() {
                        @Override
                        public void onAccept() {

                        }

                        @Override
                        public void onDecline() {
                            fillter = "unscheduled";
                            smilePlanDetails("unscheduled");
                        }
                    }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ResponseType.ANOTHER_BOOKING_APPOITMENT:
                try {
                    BookingModel bookingModel = new Gson().fromJson(jsondata, BookingModel.class);
                    //AlertDialogUtil.showCustomDialognew(this, "SmilePathway", bookingModel.getResult().getMessage());
                    new ApiResultDialog(this, "", bookingModel.getResult().getMessage(), "OK", "", new ConfirmationCallBack() {
                        @Override
                        public void onAccept() {

                        }

                        @Override
                        public void onDecline() {
                            fillter = "unscheduled";
                            smilePlanDetails("unscheduled");
                        }
                    }).show();
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

    private void setViewBookingSuggestion() {
        suggestionLayoutMain.setVisibility(View.VISIBLE);
        BookingSuggestionAdapter adapter = new BookingSuggestionAdapter(this, this, bookingSuggestionModel.getResult().getSuggestion(), bookingSuggestionModel.getResult().getAppointment_number());
        rvSuggestionList.setHasFixedSize(true);
        rvSuggestionList.setLayoutManager(new LinearLayoutManager(this));
        rvSuggestionList.setAdapter(adapter);
    }


    @Override
    public void onClickItem(int position) {
        showSuggestionDialog();
        bookingSuggestion(position);
    }

    @Override
    public void onClickPrice(int pos) {
        AlertDialogUtil.estimatorDailog(this, pos, treatmentDetailsModel.getResult());
    }

    @Override
    public void bookingAppoitment(int position) {
        dialog.dismiss();
        appoitmentBooking(position);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
