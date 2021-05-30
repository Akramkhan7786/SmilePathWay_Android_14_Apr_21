package app.smile.smilepathway.apirequest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.utils.AlertDialogUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;


/**
 * Created by user47 on 26/12/17.
 */

public class ApiResult {

    private final Dialog progressdialog;
    private Activity context;
    private RequestedServiceDataModel requestedServiceDataModel;
    private String token = "";


    public ApiResult(Activity context, RequestedServiceDataModel requestedServiceDataModel) {
        this.context = context;
        this.requestedServiceDataModel = requestedServiceDataModel;
        progressdialog = new Dialog(context);
        progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressdialog.setContentView(R.layout.view_progress);
        progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    public void executeRequest() throws UnsupportedEncodingException, JSONException {

        showProgressBar();
        token = Common.getPreferencesToken(context, ApiClass.TOKEN);
        if (token.equalsIgnoreCase("0")) {
            token = "";
        }
        Retrofit userService = ServiceGenerator.createService(RetroDataRequest.class, token);

        if (requestedServiceDataModel.getBaseRequestData().getServiceType() == Constant.SERVICE_TYPE_GET &&
                requestedServiceDataModel.getBaseRequestData().getApiTag() == ResponseType.GET_MULTIPATH) {
            Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                    .flatMap(retroDataRequest -> {

                        Observable<LinkedTreeMap> couponsObservable
                                = retroDataRequest.dataRequestGet2("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), requestedServiceDataModel.getBaseRequestData().getApiType2(), getAction()).subscribeOn(Schedulers.io());

                        return Observable.concatArray(couponsObservable);
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);

        } else if (requestedServiceDataModel.getBaseRequestData().getServiceType() == Constant.SERVICE_TYPE_GET) {
            Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                    .flatMap(retroDataRequest -> {

                        Observable<LinkedTreeMap> couponsObservable
                                = retroDataRequest.dataRequestGet(requestedServiceDataModel.getBaseRequestData().getApiType(),
                                getAction()).subscribeOn(Schedulers.io());

                        return Observable.concatArray(couponsObservable);
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);

        } else if (requestedServiceDataModel.getFile() == null) {
            if (requestedServiceDataModel.getQurry() != null && requestedServiceDataModel.getQurry().size() > 0) {
                if (requestedServiceDataModel.getBaseRequestData().getLoginApi() == Constant.LOGIN_API) {
                    Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                            .flatMap(retroDataRequest -> {
                                Observable<LinkedTreeMap> couponsObservable
                                        = retroDataRequest.dataRequestPostWithQueryLogin("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(),
                                        getAction(), getMultiPartDataText()).subscribeOn(Schedulers.io());
                                return Observable.concatArray(couponsObservable);
                            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
                } else  if (requestedServiceDataModel.getBaseRequestData().getSmileApi() == Constant.SMILE_API)  {
                    Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                            .flatMap(retroDataRequest -> {
                                Observable<LinkedTreeMap> couponsObservable
                                        = retroDataRequest.dataRequest("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), getAction(), requestedServiceDataModel.getQurry()).subscribeOn(Schedulers.io());
                                return Observable.concatArray(couponsObservable);
                            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
                } else {
                    Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                            .flatMap(retroDataRequest -> {
                                Observable<LinkedTreeMap> couponsObservable
                                        = retroDataRequest.dataRequestPostWithQuery("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), requestedServiceDataModel.getBaseRequestData().getApiType2(), getAction(), getMultiPartDataText()).subscribeOn(Schedulers.io());
                                return Observable.concatArray(couponsObservable);
                            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
                }
            } else {
                Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                        .flatMap(retroDataRequest -> {
                            Observable<LinkedTreeMap> couponsObservable
                                    = retroDataRequest.dataRequest("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), getAction(), requestedServiceDataModel.getQurry()).subscribeOn(Schedulers.io());
                            return Observable.concatArray(couponsObservable);
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
            }
        } else {
            Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                    .flatMap(retroDataRequest -> {
                        Observable<LinkedTreeMap> couponsObservable
                                = retroDataRequest.dataRequestMultiPart(requestedServiceDataModel.getBaseRequestData().getApiType(), getAction(), getMultiPartDataText(), getMultiPartDataImage()).subscribeOn(Schedulers.io());
                        return Observable.concatArray(couponsObservable);
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
        }


    }

    public void executeRequestWithoutProgressbar() throws UnsupportedEncodingException, JSONException {
        token = Common.getPreferencesToken(context, ApiClass.TOKEN);
        if (token.equalsIgnoreCase("0")) {
            token = "";
        }
        Retrofit userService = ServiceGenerator.createService(RetroDataRequest.class, token);

        if (requestedServiceDataModel.getBaseRequestData().getServiceType() == Constant.SERVICE_TYPE_GET) {
            if (requestedServiceDataModel.getBaseRequestData().getApiTag() == ResponseType.GET_MULTIPATH) {
                Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                        .flatMap(retroDataRequest -> {

                            Observable<LinkedTreeMap> couponsObservable
                                    = retroDataRequest.dataRequestGet3(requestedServiceDataModel.getBaseRequestData().getApiType(), requestedServiceDataModel.getPageNo()).subscribeOn(Schedulers.io());

                            return Observable.concatArray(couponsObservable);
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);

            } else {
                Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                        .flatMap(retroDataRequest -> {

                            Observable<LinkedTreeMap> couponsObservable
                                    = retroDataRequest.dataRequestGet(requestedServiceDataModel.getBaseRequestData().getApiType(), getAction()).subscribeOn(Schedulers.io());

                            return Observable.concatArray(couponsObservable);
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
            }
        } else if (requestedServiceDataModel.getFile() == null) {
            if (requestedServiceDataModel.getQurry() != null && requestedServiceDataModel.getQurry().size() > 0) {
                Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                        .flatMap(retroDataRequest -> {
                            Observable<LinkedTreeMap> couponsObservable
                                    = retroDataRequest.dataRequestPostWithQuery("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), requestedServiceDataModel.getBaseRequestData().getApiType2(), getAction(), getMultiPartDataText()).subscribeOn(Schedulers.io());


                            return Observable.concatArray(couponsObservable);
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
            } else {
                Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                        .flatMap(retroDataRequest -> {
                            Observable<LinkedTreeMap> couponsObservable
                                    = retroDataRequest.dataRequest("Bearer " + token, requestedServiceDataModel.getBaseRequestData().getApiType(), getAction(), requestedServiceDataModel.getQurry()).subscribeOn(Schedulers.io());


                            return Observable.concatArray(couponsObservable);
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
            }
        } else {
            Observable.just(userService.create(RetroDataRequest.class)).retry(3).subscribeOn(Schedulers.computation())
                    .flatMap(retroDataRequest -> {
                        Observable<LinkedTreeMap> couponsObservable
                                = retroDataRequest.dataRequestMultiPart(requestedServiceDataModel.getBaseRequestData().getApiType(), getAction(), getMultiPartDataText(), getMultiPartDataImage()).subscribeOn(Schedulers.io());

                        return Observable.concatArray(couponsObservable);
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
        }


    }

    private void showProgressBar() {
        ImageView imageView = progressdialog.findViewById(R.id.iv_image);
//        Glide.with(context).load(R.drawable.loading).into(imageView);

        progressdialog.setCancelable(false);
        progressdialog.show();
    }


    public HashMap<String, String> getAction() {

        HashMap<String, String> qurryMap = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, String> stringStringEntry : requestedServiceDataModel.getQurry().entrySet()) {
            strings.add(stringStringEntry.getKey());
            if (requestedServiceDataModel.getBaseRequestData().getServiceType() == Constant.SERVICE_TYPE_GET) {
                try {
                    jsonObject.put(stringStringEntry.getKey(), stringStringEntry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONArray jsonArray = new JSONArray();

//        strings.add(requestedServiceDataModel.getActionType().toLowerCase());
//        qurryMap.put("signature", SignatureCommonMenthods.getSignatureForAPI(strings));
        //  qurryMap.put("action", requestedServiceDataModel.getActionType());
        if (requestedServiceDataModel.getBaseRequestData().getServiceType() == Constant.SERVICE_TYPE_GET) {
            jsonArray.put(jsonObject);
            qurryMap.put("data", jsonArray.toString());
            requestedServiceDataModel.setQurry(new HashMap<String, String>());
        }
        return qurryMap;
    }

    private void handleError(Throwable throwable) {
        progressEnd();
        String s = throwable.getMessage() + " ";
        Log.e("error", "error" + s + " " + throwable.toString());
        throwable.printStackTrace();
        //  e.printStackTrace();
        if (s.toLowerCase().contains("unauthorized")) {
            Common.showToast(context.getApplicationContext(), "Your sesssion has been expired. Please login again.");
            Common.clearPreferences(context);
            Common.clearPreferencesToken(context);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            context.finishAffinity();
        } else {
            AlertDialogUtil.showCustomDialog(context, "SmilePathway", "oops something went wrong, please try again.");
        }
    }

    private void handleResults(LinkedTreeMap string) {
        progressEnd();
        try {
            JSONObject jsonObject = new JSONObject(string);
            Log.e("JsonObject", jsonObject.toString());
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                if (jsonObject.has("result")) {
                    requestedServiceDataModel.getResponseDelegate().onSuccess(jsonObject.toString(), jsonObject.getString("code"), requestedServiceDataModel.getBaseRequestData());
                }
            } else if (jsonObject.getString("status").equalsIgnoreCase("false")) {
                if (jsonObject.getString("code").equals("200.0")) {
                    requestedServiceDataModel.getResponseDelegate().onFailure(jsonObject.toString(), jsonObject.getString("code"), requestedServiceDataModel.getBaseRequestData());
                } else if (jsonObject.getString("code").equals("401.0")) {
                    Common.clearPreferences(context);
                    Common.clearPreferencesToken(context);
                    AlertDialogUtil.showCustomDialognew(context, "", "Your login has expired. Please log on again to continue!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Common.clearPreferences(context);
            Common.clearPreferencesToken(context);
            AlertDialogUtil.showCustomDialognew(context, "SmilePathway", "Your login has expired. Please log on again to continue!");
            /*Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            context.finishAffinity();*/
        }
    }

    private void progressEnd() {

        if (progressdialog.isShowing()) {
            try {
                progressdialog.dismiss();
            } catch (Exception e) {

            }

        }
    }

    public HashMap<String, RequestBody> getMultiPartDataText() {
        HashMap<String, RequestBody> bodyHashMap = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : requestedServiceDataModel.getQurry().entrySet()) {
            bodyHashMap.put(stringStringEntry.getKey(), RequestBodyUtils.getRequestBodyString(stringStringEntry.getValue()));
        }
        return bodyHashMap;
    }

    public List<MultipartBody.Part> getMultiPartDataImage() {
        List<MultipartBody.Part> bodyHashMap = new ArrayList<>();
        for (Map.Entry<String, File> stringStringEntry : requestedServiceDataModel.getFile().entrySet()) {
            bodyHashMap.add(RequestBodyUtils.getRequestBodyImage(stringStringEntry.getValue(), stringStringEntry.getKey()));
        }
        Log.e("bodyHashMap file", bodyHashMap + "");
//        if (bodyHashMap.size() > 0) {
//            return bodyHashMap.get(0);
//        }
//        return null;

        return bodyHashMap;
    }

}
