package app.smile.smilepathway.activity.smilecredit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.InvoiceAdapter;
import app.smile.smilepathway.adapter.SmileCreditAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.InvoiceModel;
import app.smile.smilepathway.model.SmilePointsModel;
import app.smile.smilepathway.model.UserDataModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SmileCreditActivity extends AppCompatActivity implements ResponseDelegate, SmileCreditAdapter.onClickItem, InvoiceAdapter.onClickItem {


    @BindView(R.id.tvCreditHistory)
    TextView tvCreditHistory;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvCreditHistoryList)
    RecyclerView rvCreditHistory;
    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tv_smile_points)
    TextView tvSmilePoints;
    @BindView(R.id.tv_invoice)
    TextView tvInvoice;
    @BindView(R.id.lvTabs)
    LinearLayout lvTabs;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.img_user)
    CircleImageView imgUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_view_all)
    TextView tvViewAll;
    @BindView(R.id.clMainContent)
    LinearLayout clMainContent;
    @BindView(R.id.cardView_smile_points)
    CardView cardViewSmilePoints;
    @BindView(R.id.rvInvoiceList)
    RecyclerView rvInvoiceList;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private SmilePointsModel smilePointsModel;
    private UserDataModel userResponse;
    private InvoiceModel invoiceModel;
    private String tabStatus = "";
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_history);
        ButterKnife.bind(this);
        userResponse = new Gson().fromJson(Common.getPreferences(this, "userData"), UserDataModel.class);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        setView();
        tvSmilePoints.setTextColor(getResources().getColor(R.color.grey_));
        tvInvoice.setTextColor(getResources().getColor(R.color.selector));
        tvSmilePoints.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
        tvInvoice.setBackground(getDrawable(R.drawable.rounded_btn_selected));
        getInvoiceDetils();
        cardViewSmilePoints.setVisibility(View.GONE);
        tvViewAll.setVisibility(View.GONE);
        rvInvoiceList.setVisibility(View.VISIBLE);
        // client feedback invocie tab selected always no need tab status
     /*   if (getIntent().hasExtra("tab_status")) {
            tvSmilePoints.setTextColor(getResources().getColor(R.color.grey_));
            tvInvoice.setTextColor(getResources().getColor(R.color.selector));
            tvSmilePoints.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
            tvInvoice.setBackground(getDrawable(R.drawable.rounded_btn_selected));
            getInvoiceDetils();
            cardViewSmilePoints.setVisibility(View.GONE);
            tvViewAll.setVisibility(View.GONE);
            rvInvoiceList.setVisibility(View.VISIBLE);
        } else {
            tvSmilePoints.setTextColor(getResources().getColor(R.color.selector));
            tvInvoice.setTextColor(getResources().getColor(R.color.grey_));
            tvSmilePoints.setBackground(getDrawable(R.drawable.rounded_btn_selected));
            tvInvoice.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
            smilePointsGet("1");
            cardViewSmilePoints.setVisibility(View.VISIBLE);
            tvViewAll.setVisibility(View.VISIBLE);
            rvInvoiceList.setVisibility(View.GONE);
        }*/

    }

    private void setView() {
        if (userResponse.getProfile_image() != null && !userResponse.getProfile_image().equalsIgnoreCase("")) {
            Glide.with(this).load(userResponse.getProfile_image())
                    .apply(new RequestOptions().placeholder(R.drawable.usertopbar).error(R.drawable.usertopbar))
                    .into(imgUser);
        }
        tvUser.setText(userResponse.getFirstname() + " " + userResponse.getLastname());
    }

    private void smilePointsGet(String pageNo) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.GET_SMILE_POINTS);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PAGE_NO, pageNo);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("smilecredit");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void getInvoiceDetils() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.GETINVOICE);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiType("invoice");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    @OnClick({R.id.ivBack, R.id.tv_view_all, R.id.tv_smile_points, R.id.tv_invoice, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_view_all:
                Intent intent15 = new Intent(this, SmileCreditPointsActivity.class);
                startActivity(intent15);
                break;
            case R.id.tv_smile_points:
                tvSmilePoints.setTextColor(getResources().getColor(R.color.selector));
                tvInvoice.setTextColor(getResources().getColor(R.color.grey_));
                tvSmilePoints.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                tvInvoice.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                smilePointsGet("1");
                cardViewSmilePoints.setVisibility(View.VISIBLE);
                tvViewAll.setVisibility(View.VISIBLE);
                rvInvoiceList.setVisibility(View.GONE);
                break;
            case R.id.tv_invoice:
                tvSmilePoints.setTextColor(getResources().getColor(R.color.grey_));
                tvInvoice.setTextColor(getResources().getColor(R.color.selector));
                tvSmilePoints.setBackground(getDrawable(R.drawable.rounded_btn_not_selected));
                tvInvoice.setBackground(getDrawable(R.drawable.rounded_btn_selected));
                getInvoiceDetils();
                cardViewSmilePoints.setVisibility(View.GONE);
                tvViewAll.setVisibility(View.GONE);
                rvInvoiceList.setVisibility(View.VISIBLE);
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
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
                        tvPoints.setText(smilePointsModel.getResult().getOverall_balance() + " PTS");
                        setupAdapter(smilePointsModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ResponseType.GETINVOICE:
                try {
                    invoiceModel = new Gson().fromJson(jsondata, InvoiceModel.class);
                    if (invoiceModel.getCode() == 200) {
                        invoiceSetupAdapter(invoiceModel.getResult());
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
        SmileCreditAdapter adapter = new SmileCreditAdapter(this, this, smilePointsModel.getResult().getCredit_details());
        rvCreditHistory.setHasFixedSize(true);
        rvCreditHistory.setLayoutManager(new LinearLayoutManager(this));
        rvCreditHistory.setAdapter(adapter);
    }

    private void invoiceSetupAdapter(List<InvoiceModel.ResultBean> result) {
        InvoiceAdapter adapter = new InvoiceAdapter(this, this, result);
        rvInvoiceList.setHasFixedSize(true);
        rvInvoiceList.setLayoutManager(new LinearLayoutManager(this));
        rvInvoiceList.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void itemOnClick(String id) {
        Intent intent = new Intent(this, SmileInvoiceViewActivity.class);
        intent.putExtra("invoice_id", id);
        startActivity(intent);
    }
}
