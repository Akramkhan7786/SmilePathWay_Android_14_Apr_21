package app.smile.smilepathway.activity.smilecredit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeIntent;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import app.smile.smilepathway.Dialog.ApiResultDialog;
import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.activity.setting.LoginActivity;
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
import okhttp3.OkHttpClient;

public class StripeActivity extends AppCompatActivity implements ResponseDelegate {
    @BindView(R.id.header_logo)
    ImageView headerLogo;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.card_multiline_widget)
    CardMultilineWidget cardMultilineWidget;
    @BindView(R.id.cardInputWidget)
    CardInputWidget cardInputWidget;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.clMainContent)
    LinearLayout clMainContent;
    // ...
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private String client_secret = "";
    private String stripeAccountId = "";
    private OkHttpClient httpClient = new OkHttpClient();
    private int notifiTotalCount = 0;
    private static RequestedServiceDataModel requestedServiceDataModel;
    public static String intentId = "";
    public static String paymentStatus = "";
    public static String invoiceId = "";
    private ProgressDialog pageProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }

        if (getIntent().getStringExtra("stripe_accountid") != null) {
            stripeAccountId = getIntent().getStringExtra("stripe_accountid");
            invoiceId = getIntent().getStringExtra("invoice_id");
            client_secret = getIntent().getStringExtra("client_secret");
        }
        startCheckout();
        initializeProgressDialog();
    }
    private void initializeProgressDialog() {
        if (pageProgressDialog == null) {
            pageProgressDialog = new ProgressDialog(StripeActivity.this);
            pageProgressDialog.setMessage(getResources().getString(R.string.service_progrees_bar_title));
            pageProgressDialog.setCanceledOnTouchOutside(false);

            pageProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    //RESTClientAPI.stopRequest(currentRunningRequest);
                }
            });
        }
    }

    public void showProgressDialog() {
        if (!isFinishing() && !pageProgressDialog.isShowing()) {
            pageProgressDialog.show();
        }
    }

    /**
     * Dismiss Progress Dialog
     */
    public void dismissProgressDialog() {
        try {
            if (pageProgressDialog.isShowing()) {
                pageProgressDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            //Do nothing because when user rapidly change
            //orientation suddenly not attach to any windows will lead to crash
            //Log.e(this.toString(), e + " Do nothing because when user rapidly change");
        }
    }
    private void startCheckout() {
        btnSubmit.setOnClickListener((View view) -> {
            showProgressDialog();
            CardInputWidget cardInputWidget = (CardInputWidget) findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, client_secret);
                final Context context = getApplicationContext();
                stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey(), stripeAccountId);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                paymentCancel();
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripeActivity> activityRef;


        PaymentResultCallback(@NonNull StripeActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Log.e("status", paymentIntent.toString());
                Log.e("status", paymentIntent.getStatus().toString());
                intentId = paymentIntent.getId().toString();
                paymentStatus = paymentIntent.getStatus().toString();
                dismissProgressDialog();
                paymentSuccess(intentId, paymentStatus);
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                Log.e("status", "failed");
                callbackMethad("Your transaction has been declined!");
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            Log.e("OnError", e.getLocalizedMessage());
            //AlertDialogUtil.showCustomDialogApiResult(activity, "SmilePathway", e.getLocalizedMessage());
            dismissProgressDialog();
            callbackMethad(e.getLocalizedMessage());
        }
    }

    public void callbackMethad(String msg) {
        new ApiResultDialog(this, "", msg, "OK", "", new ConfirmationCallBack() {
            @Override
            public void onAccept() {
            }

            @Override
            public void onDecline() {
                finish();
            }
        }).show();

    }

    public void paymentSuccess(String id, String paymentStatus) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.PAYMENTSUCCESS);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().INVOICE_ID, invoiceId);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().STRIPE_PAYMENT_INTENT_ID, id);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().STRIPE_STATUS, paymentStatus);
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

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
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
                                Intent intent = new Intent(StripeActivity.this, DashboardHomeActivity.class);
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

    @Override
    public void onBackPressed() {
     paymentCancel();
    }

}

