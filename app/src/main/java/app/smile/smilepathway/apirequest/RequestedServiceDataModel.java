package app.smile.smilepathway.apirequest;


import android.app.Activity;
import app.smile.smilepathway.R;


public class RequestedServiceDataModel extends DataModel {

    private ApiResult dostoomServerRequest;

    public RequestedServiceDataModel(Activity context, ResponseDelegate delegate) {
        super(context, delegate);
    }

    public void execute() {

        if (Common.getConnectivityStatus(context)) {
            dostoomServerRequest = new ApiResult(context, this);
            try {
                dostoomServerRequest.executeRequest();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            if (isShowNetworkTost()) {
                Common.showToast(context, context.getString(R.string.internet_connection_msg));
            } else {
                if (responseDelegate != null) {
                    responseDelegate.onNoNetwork(context.getString(R.string.internet_connection_msg), getBaseRequestData());
                    Common.showToast(context, "Please check your internet connection.");

                }
            }

        }

    }

    public void executeWithoutProgressbar() {

        if (Common.getConnectivityStatus(context)) {
            dostoomServerRequest = new ApiResult(context, this);
            try {
                dostoomServerRequest.executeRequestWithoutProgressbar();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            if (isShowNetworkTost()) {
                Common.showToast(context, context.getString(R.string.internet_connection_msg));
            } else {
                if (responseDelegate != null) {
                    responseDelegate.onNoNetwork(context.getString(R.string.internet_connection_msg), getBaseRequestData());
//                    utils.showToast(context, "Please check your internet connection.");

                }
            }

        }


    }

}
