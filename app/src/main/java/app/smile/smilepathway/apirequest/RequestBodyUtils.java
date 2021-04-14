package app.smile.smilepathway.apirequest;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by user4 on 2/6/16.
 */
public class RequestBodyUtils {


    public static  MultipartBody.Part getRequestBodyImage(File file, String key)
    {
       RequestBody requestFile= RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        return body;
    }
    public static RequestBody getRequestBodyString(String text)
    {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

}
