package app.smile.smilepathway.apirequest;

/**
 * Created by user4 on 27/5/16.
 */
public interface ResponseDelegate {

    public void onNoNetwork(String message, BaseRequestData baseRequestData);

    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData);

    public void onFailure(String jsondata, String message, BaseRequestData baseRequestData);



}
