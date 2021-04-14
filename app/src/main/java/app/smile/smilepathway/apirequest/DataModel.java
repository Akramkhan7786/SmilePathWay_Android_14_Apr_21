package app.smile.smilepathway.apirequest;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;


public abstract class DataModel {

	protected Activity context;
	protected ResponseDelegate responseDelegate;
	private String url;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	private String actionType;
	private  int REQUEST_TAG;
	private BaseRequestData baseRequestData;

    private HashMap<String, String> qurry = new HashMap<>();

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	private String pageNo;
	private HashMap<String, File> files;
	private Class<?> responseType;
	private Type type;
	private JSONObject requestedParameter;

    public void setRequestedParameter(JSONObject requestedParameter) {
        this.requestedParameter = requestedParameter;
    }
    public HashMap<String, String> getQurry() {
        return qurry;
    }
	public HashMap<String, File> getFile() {
		return files;
	}

    public void setQurry(HashMap<String, String> qurry) {
        this.qurry = qurry;
    }
	public void putQurry(String key, String value) {
		if(qurry==null)
			qurry = new HashMap<>();
		qurry.put(key,value);
	}
	public void setFiles(HashMap<String, File> files) {
		this.files = files;
	}
	public void putFiles(String key, File value) {
		if(files==null)
			files = new HashMap<>();
		files.put(key,value);
	}
    private boolean  isShowNetworkTost		=	false;
	

	public boolean isShowNetworkTost() {
		return isShowNetworkTost;
	}

	public void setShowNetworkTost(boolean isShowNetworkTost) {
		this.isShowNetworkTost = isShowNetworkTost;
	}

	public DataModel(Activity context, ResponseDelegate delegate) {
		this.context = context;
		this.responseDelegate = delegate;

	}

	public Context getContext() {

		return context;
	}

	protected String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*public int getTag(){

		return REQUEST_TAG;
	}

	public void setRequestTag(int requestTag){
		this.REQUEST_TAG = requestTag;
	}*/
	public BaseRequestData getBaseRequestData() {
		return baseRequestData;
	}

	public void setBaseRequestData(BaseRequestData baseRequestData) {
		this.baseRequestData = baseRequestData;
	}

	protected ResponseDelegate getResponseDelegate() {

		return responseDelegate;
	}

	public Type getResponseType() {
		if(responseType!=null)
		return responseType;
		else
			return type;
	}
	public Type getType() {
		return type;
	}

	public void setResponseType(Class<?> responseType) {
		this.responseType = responseType;
	}
	public void setType(Type listType) {
		this.type = listType;
	}


	public JSONObject getParamBody()
	{
		return requestedParameter;
	}
	public String getParamBodyArray()
	{
        if(requestedParameter==null)
            return new JSONObject().toString();
			return requestedParameter.toString();
		
	}
	public void putParamData(String key, Object Value) throws JSONException
	{
		if(requestedParameter==null)
			requestedParameter	=	new JSONObject();
		requestedParameter.put(key, Value);
	}
	public void putParamData(String key, int Value) throws JSONException
	{
		if(requestedParameter==null)
			requestedParameter	=	new JSONObject();
		requestedParameter.put(key, Value);
	}
	public void putParamData(String key, double Value) throws JSONException
	{
		if(requestedParameter==null)
			requestedParameter	=	new JSONObject();
		requestedParameter.put(key, Value);
	}
	public void putParamData(String key, boolean Value) throws JSONException
	{
		if(requestedParameter==null)
			requestedParameter	=	new JSONObject();
		requestedParameter.put(key, Value);
	}
	public void putParamData(String key, long Value) throws JSONException
	{
		if(requestedParameter==null)
			requestedParameter	=	new JSONObject();
		requestedParameter.put(key, Value);
	}


	public void setJsonArray(String tagName, JSONArray jArray)throws JSONException {

		requestedParameter.putOpt(tagName, jArray);
	}

}
