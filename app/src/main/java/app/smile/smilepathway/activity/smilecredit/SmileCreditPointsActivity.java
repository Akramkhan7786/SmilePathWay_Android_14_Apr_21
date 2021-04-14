package app.smile.smilepathway.activity.smilecredit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.SmilePointsAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.SmilePointsModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmileCreditPointsActivity extends AppCompatActivity implements ResponseDelegate, SmilePointsAdapter.onClickItem, AdapterView.OnItemSelectedListener {

    @BindView(R.id.tvCreditHistory)
    TextView tvCreditHistory;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvCreditHistoryList)
    RecyclerView rvCreditHistory;
    @BindView(R.id.img_fillter)
    ImageView imgFillter;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private SmilePointsModel smilePointsModel;
    private int notifiTotalCount = 0;
    private ArrayList<String> spinnerList;
    private String spinnerItemSelected = "";
    Calendar myCalendar = Calendar.getInstance();
    //private String mServerFromDate = "", mServerToDate = "";
    private String mFromDate = "", mToDate = "";
    private TextView fromDate;
    private TextView toDate;
    private EditText etFromPoints;
    private EditText etEndPoints;
    private String monthWiseRadio;
    private RadioButton rbLastMonth, rbLastThreeMonth;
    private RadioGroup rgMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_points);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        smilePointsGet("1");

    }

    private void smilePointsGet(String pageNo) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.GET_SMILE_POINTS);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PAGE_NO, pageNo);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().START_DATE, mFromDate);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().END_DATE, mToDate);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().MONTH, "");
        if (etEndPoints != null)
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().MIN_POINT, etFromPoints.getText().toString().trim());
        if (etEndPoints != null)
            requestedServiceDataModel.putQurry(ApiClass.getmApiClass().MAX_POINT, etEndPoints.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().TYPE, spinnerItemSelected);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("smilecredit");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }


    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.GET_SMILE_POINTS:
                try {
                    smilePointsModel = new Gson().fromJson(jsondata, SmilePointsModel.class);
                    if (smilePointsModel.getCode() == 200) {
                        setupAdapter(smilePointsModel);
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


    private void setupAdapter(SmilePointsModel smilePointsModel) {
        SmilePointsAdapter adapter = new SmilePointsAdapter(this, this, smilePointsModel.getResult().getCredit_details());
        rvCreditHistory.setHasFixedSize(true);
        rvCreditHistory.setLayoutManager(new LinearLayoutManager(this));
        rvCreditHistory.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {

    }

    @OnClick({R.id.ivBack, R.id.img_fillter, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.img_fillter:
                filterDialog();
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }

    private void filterDialog() {
        // if you need dialog width full parent then you can use bottom sheet dialog but issue radio theme issue and background layout not impliment
        //BottomSheetDialog dialog = new BottomSheetDialog(this);
        spinnerItemSelected = "All";
        mFromDate = "";
        mToDate = "";
        monthWiseRadio = "";
        Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_tab_box_curve));
        dialog.setContentView(R.layout.alert_fillter_smile_credit);
        spinnerList = new ArrayList<>();

        Spinner spinnerCreditAndDebit = dialog.findViewById(R.id.spinner_category);
        etFromPoints = dialog.findViewById(R.id.et_from_points);
        etEndPoints = dialog.findViewById(R.id.et_end_points);
        rbLastMonth = dialog.findViewById(R.id.rb_last_month);
        rbLastThreeMonth = dialog.findViewById(R.id.rb_last_three_month);
        rgMonth = dialog.findViewById(R.id.rg_month);

        Button btnSearch = dialog.findViewById(R.id.btnSearch);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        fromDate = dialog.findViewById(R.id.txt_from_date);
        toDate = dialog.findViewById(R.id.txt_end_date);

        fromDate.setOnClickListener(view -> {
            if (monthWiseRadio.equalsIgnoreCase(""))
                chooseFromDate();
        });
        toDate.setOnClickListener(view -> {
            if (monthWiseRadio.equalsIgnoreCase(""))
                chooseToDate();
        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnSearch.setOnClickListener(view -> {
            SmileCreditPointsActivity.this.smilePointsGet("1");
            dialog.dismiss();
        });

        dialog.show();
        spinnerList.add(0, "All");
        spinnerList.add(1, "Debit");
        spinnerList.add(2, "Credit");
        spinnerCreditAndDebit.setOnItemSelectedListener(SmileCreditPointsActivity.this);
        final ArrayAdapter<String> quantityItem = new ArrayAdapter<String>(SmileCreditPointsActivity.this, android.R.layout.simple_spinner_item, spinnerList);
        quantityItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreditAndDebit.setAdapter(quantityItem);
        dialog.setCanceledOnTouchOutside(true);

        rgMonth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_last_month:
                        // do operations specific to this selection
                        monthWiseRadio = "0";
                        fromDate.setText("From Date");
                        toDate.setText("To Date");
                        mFromDate = "";
                        mToDate = "";
                        break;
                    case R.id.rb_last_three_month:
                        // do operations specific to this selection
                        monthWiseRadio = "3";
                        fromDate.setText("From Date");
                        toDate.setText("To Date");
                        mFromDate = "";
                        mToDate = "";
                        break;

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (parent.getId() == R.id.spinner_category) {
            spinnerItemSelected = spinnerList.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void chooseFromDate() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd MMM, yyyy "; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mFromDate = sdf.format(myCalendar.getTime());
            fromDate.setText(mFromDate);
            Log.e("mFrom Date", mFromDate);
            rbLastMonth.setClickable(false);
            rbLastThreeMonth.setClickable(false);
            rbLastMonth.setFocusable(false);
            rbLastThreeMonth.setFocusable(false);
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void chooseToDate() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd MMM, yyyy "; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mToDate = sdf.format(myCalendar.getTime());
            toDate.setText(mToDate);
            Log.e("mTo Date", mToDate);
            rbLastMonth.setClickable(false);
            rbLastThreeMonth.setClickable(false);
            rbLastMonth.setFocusable(false);
            rbLastThreeMonth.setFocusable(false);
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        // datePickerDialog.getDatePicker().setMinDate(Long.parseLong(mFromDate));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

}
