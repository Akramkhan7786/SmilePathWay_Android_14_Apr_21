package app.smile.smilepathway.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.adapter.NotificationAdapter;
import app.smile.smilepathway.adapter.NotificationHeaderAdapter;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.HomeResponseModel;
import app.smile.smilepathway.model.NotificationHeaderModel;
import app.smile.smilepathway.model.NotificationListModel;
import app.smile.smilepathway.model.NotificationListNewModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity implements ResponseDelegate, NotificationHeaderAdapter.onClickItem, NotificationAdapter.onClickItem {

    @BindView(R.id.tvNotification)
    TextView tvNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    @BindView(R.id.rvNotificationHeader)
    RecyclerView rvNotificationHeader;
    private RequestedServiceDataModel requestedServiceDataModel;
    private NotificationHeaderModel headerModel;
    private NotificationListNewModel notificationListModel;
    private String notificationId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        notificationAllList();
    }

    private void notificationHeader() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFIHEADER);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiTag(ResponseType.GET_MULTIPATH);
        baseRequestData.setApiType("notification");
        baseRequestData.setApiType2("notification_type");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void notificationList(String id) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFILIST);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().NOTIFICATION_ID, id);
        baseRequestData.setApiType("notification");
        baseRequestData.setApiType2("notification_record");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }
    private void notificationAllList() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFILIST);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_GET);
        baseRequestData.setApiTag(ResponseType.GET_MULTIPATH);
        baseRequestData.setApiType("notification");
        baseRequestData.setApiType2("get_all_notification");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }
    private void readNotificationMessage(String id) {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.NOTIFIUNREAD);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().NOTIFICATION_ID, id);
        baseRequestData.setApiType("notification");
        baseRequestData.setApiType2("notification_deactivate");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.executeWithoutProgressbar();
    }

    private void setupAdapter(List<NotificationHeaderModel.ResultBean> result) {
        NotificationHeaderAdapter adapter = new NotificationHeaderAdapter(this, this, result);
        rvNotificationHeader.setHasFixedSize(true);
        rvNotificationHeader.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvNotificationHeader.setAdapter(adapter);
    }

    private void setupAdapterList(List<NotificationListNewModel.ResultBean.RecordsBean> result) {
        NotificationAdapter adapter = new NotificationAdapter(this, this, result);
        rvNotification.setHasFixedSize(true);
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        rvNotification.setAdapter(adapter);
    }

    @OnClick({R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.NOTIFIHEADER:
                try {
                    headerModel = new Gson().fromJson(jsondata, NotificationHeaderModel.class);
                    setupAdapter(headerModel.getResult());
                    if (headerModel.getResult().size() > 0) {
                        notificationId = headerModel.getResult().get(0).getId();
                        notificationList(notificationId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.NOTIFILIST:
                try {
                    notificationListModel = new Gson().fromJson(jsondata, NotificationListNewModel.class);
                    setupAdapterList(notificationListModel.getResult().getRecords());
                    if (notificationListModel.getResult().getRecords().size() > 0) {
                        //readNotificationMessage(notificationId);
                        Intent intent = new Intent("seen_notification");
                        sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ResponseType.NOTIFIUNREAD:
                try {
                    Intent intent = new Intent("seen_notification");
                    sendBroadcast(intent);
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

    @Override
    public void itemOnClick(String id) {
        notificationId = id;
        notificationList(notificationId);
    }

    @Override
    public void notificationItemClick(String id) {
        // click evnet open type base screen in notification
    }
}
