package app.smile.smilepathway.activity.smilecredit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentResult;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.smile.smilepathway.Dialog.ApiResultDialog;
import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.adapter.InvoiceViewAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ClientSecertModel;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.InvoiceViewModel;
import app.smile.smilepathway.model.stripePaymentSuccessModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmileInvoiceViewActivity extends AppCompatActivity implements ResponseDelegate {
    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;
    private String customerId;
    private String ephemeralKeySecret;
    private String invoiceId;
    private String payment_intent_id;
    private boolean isPay = false;
    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tvInvoiceView)
    TextView tvInvoiceView;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txt_practice_name)
    TextView txtPracticeName;
    @BindView(R.id.txt_practice_address)
    TextView txtPracticeAddress;
    @BindView(R.id.txt_practice_state_city_pincode)
    TextView txtPracticeStateCityPincode;
    @BindView(R.id.txt_patient_name)
    TextView txtPatientName;
    @BindView(R.id.txt_patient_address)
    TextView txtPatientAddress;
    @BindView(R.id.txt_patient_state_city_pincode)
    TextView txtPatientStateCityPincode;
    @BindView(R.id.tvInvoiceNumber)
    TextView tvInvoiceNumber;
    @BindView(R.id.tvDueDate)
    TextView tvDueDate;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.payButton)
    Button payButton;
    @BindView(R.id.invoiceHeader)
    CardView invoiceHeader;
    @BindView(R.id.rvInvoiceList)
    RecyclerView rvInvoiceList;
    @BindView(R.id.clMainContent)
    LinearLayout clMainContent;
    @BindView(R.id.tvInvoiceDate)
    TextView tvInvoiceDate;
    @BindView(R.id.tvPaymentDate)
    TextView tvPaymentDate;
    @BindView(R.id.img_right_tick)
    ImageView imgRightTick;
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private InvoiceViewModel invoiceViewModel;
    private int notifiTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        if (getIntent().hasExtra("invoice_id")) {
            invoiceId = getIntent().getStringExtra("invoice_id");
            smilePointsGet(invoiceId);
        }
        stripePaymentMethad();
    }


    private void smilePointsGet(String invoiceId) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.GETINVOICEVIEW);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().ID, invoiceId);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("invoice");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void creationPaymentBackend() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.PAYSTRIPECREATE);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().INVOICE_ID, invoiceId);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().PRACTICE_STRIPE_ID, invoiceViewModel.getResult().getPractice_details().getPractice_stripe_id());
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("pay_invoice_amount");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();

    }


    @OnClick({R.id.ivBack, R.id.ivNotification, R.id.payButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                if (isPay) {
                    paymentCancel();
                } else {
                    finish();
                }
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
            case R.id.payButton:
                if (isPay) {
                    presentPaymentSheet();
                }
                break;
        }
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.GETINVOICEVIEW:
                try {
                    invoiceViewModel = new Gson().fromJson(jsondata, InvoiceViewModel.class);
                    if (invoiceViewModel.getCode() == 200) {
                        setBankDetails();
                        setupAdapter(invoiceViewModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.PAYSTRIPECREATE:
                try {
                    ClientSecertModel secertModel = new Gson().fromJson(jsondata, ClientSecertModel.class);
                    if (secertModel.getCode() == 200) {
                        invoiceId = secertModel.getResult().getInvoice_id();
                        payment_intent_id = secertModel.getResult().getPayment_intent_id();

                        // need for stripe payment gatway this key
                        paymentIntentClientSecret = secertModel.getResult().getClient_secret();
                        customerId = secertModel.getResult().getCustomerid();
                        ephemeralKeySecret = secertModel.getResult().getEphemeralKeySecret();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.PAYMENTSUCCESS:
                try {
                    stripePaymentSuccessModel stripeModel = new Gson().fromJson(jsondata, stripePaymentSuccessModel.class);
                    if (stripeModel.getCode() == 200) {
                        new ApiResultDialog(this, "", stripeModel.getResult().getMessage(), "OK", "", new ConfirmationCallBack() {
                            @Override
                            public void onAccept() {
                            }
                            @Override
                            public void onDecline() {
                                finish();
                                Intent intent = new Intent(SmileInvoiceViewActivity.this, DashboardHomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.PAYMENTCANCEL:
                try {
                    finish();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setBankDetails() {
        invoiceHeader.setVisibility(View.VISIBLE);
        txtPracticeName.setText(invoiceViewModel.getResult().getPractice_details().getPractice_name());
        txtPracticeAddress.setText(invoiceViewModel.getResult().getPractice_details().getPractice_address());
        if (!invoiceViewModel.getResult().getPractice_details().getPractice_state().equalsIgnoreCase("") && invoiceViewModel.getResult().getPractice_details().getPractice_state() != null &&
                !invoiceViewModel.getResult().getPractice_details().getPractice_city().equalsIgnoreCase("") && invoiceViewModel.getResult().getPractice_details().getPractice_city() != null &&
                !invoiceViewModel.getResult().getPractice_details().getPractice_pincode().equalsIgnoreCase("") && invoiceViewModel.getResult().getPractice_details().getPractice_pincode() != null) {
            txtPracticeStateCityPincode.setVisibility(View.VISIBLE);
            txtPracticeStateCityPincode.setText(invoiceViewModel.getResult().getPractice_details().getPractice_state() + ", "
                    + invoiceViewModel.getResult().getPractice_details().getPractice_city() + ", "
                    + invoiceViewModel.getResult().getPractice_details().getPractice_pincode());
        } else {
            txtPracticeStateCityPincode.setVisibility(View.GONE);
        }


        txtPatientName.setText(invoiceViewModel.getResult().getPatient_details().getName());
        txtPatientAddress.setText(invoiceViewModel.getResult().getPatient_details().getAddress());

        if (!invoiceViewModel.getResult().getPatient_details().getState().equalsIgnoreCase("") && invoiceViewModel.getResult().getPatient_details().getState() != null &&
                !invoiceViewModel.getResult().getPatient_details().getCity().equalsIgnoreCase("") && invoiceViewModel.getResult().getPatient_details().getCity() != null &&
                !invoiceViewModel.getResult().getPatient_details().getZipcode().equalsIgnoreCase("") && invoiceViewModel.getResult().getPatient_details().getZipcode() != null) {
            txtPatientStateCityPincode.setVisibility(View.VISIBLE);
            txtPatientStateCityPincode.setText(invoiceViewModel.getResult().getPatient_details().getState() + ", " + invoiceViewModel.getResult().getPatient_details().getCity() + ", " + invoiceViewModel.getResult().getPatient_details().getZipcode());
        } else {
            txtPatientStateCityPincode.setVisibility(View.GONE);
        }

        tvInvoiceNumber.setText(invoiceViewModel.getResult().getInvoice().getInvoice_number());
        tvDueDate.setText(invoiceViewModel.getResult().getInvoice().getDue_date());
        tvPaymentDate.setText(invoiceViewModel.getResult().getInvoice().getPayment_date());
        tvInvoiceDate.setText(invoiceViewModel.getResult().getInvoice().getInvoice_create_date());

        tvAmount.setText("$" + invoiceViewModel.getResult().getInvoice().getGross_amount());
        if (invoiceViewModel.getResult().getInvoice().getStatus().equalsIgnoreCase("Presented")) {
            payButton.setText("PAY");
            imgRightTick.setVisibility(View.GONE);
            isPay = true;
            if (isPay) {
                creationPaymentBackend();
            }
        } else if (invoiceViewModel.getResult().getInvoice().getStatus().equalsIgnoreCase("Paid")) {
            payButton.setText("PAID");
            imgRightTick.setVisibility(View.VISIBLE);
            isPay = false;
        } else {
            payButton.setText("Canceled");
            imgRightTick.setVisibility(View.GONE);
            isPay = false;
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

    private void setupAdapter(InvoiceViewModel invoiceViewModel) {
        InvoiceViewAdapter adapter = new InvoiceViewAdapter(this, invoiceViewModel.getResult().getInvoice_details());
        rvInvoiceList.setHasFixedSize(true);
        rvInvoiceList.setNestedScrollingEnabled(false);
        rvInvoiceList.setLayoutManager(new LinearLayoutManager(this));
        rvInvoiceList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (isPay) {
            paymentCancel();
        } else {
            finish();
        }
    }

    /*-------------------------------------------------Stripe CheckOut Activity-------------------------------------------------------------*/
    public void paymentSuccess(String status) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.PAYMENTSUCCESS);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().INVOICE_ID, invoiceId);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().STRIPE_PAYMENT_INTENT_ID, payment_intent_id);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().STRIPE_STATUS, status);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("invoice_payment");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    public void paymentCancel() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.PAYMENTCANCEL);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().INVOICE_ID, invoiceId);
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("cancel_invoice_payment");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void stripePaymentMethad() {
        PaymentConfiguration.init(this, Constant.STRIP_PAYMENT_KEY);
        paymentSheet = new PaymentSheet(this, result -> SmileInvoiceViewActivity.this.onPaymentSheetResult(result));
    }

    private void presentPaymentSheet() {
        paymentSheet.present$stripe_release(
                paymentIntentClientSecret,
                new PaymentSheet.Configuration(
                        "Example, Inc.",
                        new PaymentSheet.CustomerConfiguration(
                                customerId,
                                ephemeralKeySecret
                        )
                )
        );

    }

    private void onPaymentSheetResult(final PaymentResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentResult.Canceled) {
            Toast.makeText(
                    this,
                    "Payment Canceled",
                    Toast.LENGTH_LONG
            ).show();
        } else if (paymentSheetResult instanceof PaymentResult.Failed) {
            Toast.makeText(
                    this,
                    "Payment Failed. See logcat for details.",
                    Toast.LENGTH_LONG
            ).show();
            Log.e("App", "Got error: ", ((PaymentResult.Failed) paymentSheetResult).getError());
            callbackMethad("Your transaction has been declined!");
        } else if (paymentSheetResult instanceof PaymentResult.Completed) {
            String status = String.valueOf(((PaymentResult.Completed) paymentSheetResult).getPaymentIntent().getStatus());
            paymentSuccess(status);
        }
    }

    public void callbackMethad(String msg) {
        new ApiResultDialog(this, "Got error!", msg, "OK", "", new ConfirmationCallBack() {
            @Override
            public void onAccept() {
            }

            @Override
            public void onDecline() {
                finish();
            }
        }).show();

    }

    /*-------------------------------------------------Stripe CheckOut Activity-------------------------------------------------------------*/
}
