package app.smile.smilepathway.apirequest;

import android.view.View;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseRequestData {
    private int apiTag;

    public int getApiTag() {
        return apiTag;
    }

    public void setApiTag(int apiTag) {
        this.apiTag = apiTag;
    }

    private int tag;
    private String id;
    private String file;
    private String webService;
    private int loginApi;
    private int smileApi;

    public int getSmileApi() {
        return smileApi;
    }

    public void setSmileApi(int smileApi) {
        this.smileApi = smileApi;
    }

    public int getLoginApi() {
        return loginApi;
    }

    public void setLoginApi(int loginApi) {
        this.loginApi = loginApi;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    private String apiType;

    public String getApiType2() {
        return apiType2;
    }

    public void setApiType2(String apiType2) {
        this.apiType2 = apiType2;
    }

    private String apiType2;
    public JSONObject getChatData() {
        return chatData;
    }

    public void setChatData(JSONObject chatData) {
        this.chatData = chatData;
    }

    private JSONObject chatData ;
    private int serviceType = 2;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private boolean show;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    private boolean error = false;
    private HashMap<Type, Object> hashMapGson = new HashMap<>();
    private View baseView;
    private ArrayList<View> views;
    private String mailId;

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public View getBaseView() {
        return baseView;
    }

    public void setBaseView(View baseView) {
        this.baseView = baseView;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    public HashMap<Type, Object> getHashMapGson() {
        return hashMapGson;
    }

    public void setHashMapGson(HashMap<Type, Object> hashMapGson) {
        this.hashMapGson = hashMapGson;
    }

    public void addHashMap(Type type, Object o) {
        hashMapGson.put(type, o);
    }


    @Override
    public boolean equals(Object o) {
        return getTag() == ((BaseRequestData) o).getTag();
    }


    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceType() {
        return this.serviceType;
    }
}
