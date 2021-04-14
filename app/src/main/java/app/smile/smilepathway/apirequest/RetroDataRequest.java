package app.smile.smilepathway.apirequest;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by Asus on 13-07-2015.
 */
public interface RetroDataRequest {

    @FormUrlEncoded
    @POST("api/{path1}")
    public Observable<LinkedTreeMap> dataRequest(@Header("Authorization") String header, @Path("path1") String path1, @QueryMap(encoded = true) Map<String, String> action, @FieldMap(encoded = true) Map<String, String> fieldValue);

    @Multipart
    @POST("api/{path1}/{path2}")
    public Observable<LinkedTreeMap> dataRequestPostWithQuery(@Header("Authorization") String header, @Path("path1") String path1,  @Path("path2") String path2, @QueryMap(encoded = true) Map<String, String> action, @PartMap Map<String, RequestBody> fieldValue);

    @Multipart
    @POST("api/login/{path1}")
    public Observable<LinkedTreeMap> dataRequestPostWithQueryLogin(@Header("Authorization") String header, @Path("path1") String path1, @QueryMap(encoded = true) Map<String, String> action, @PartMap Map<String, RequestBody> fieldValue);

    @Multipart
    @POST("api/{path1}")
    public Observable<LinkedTreeMap> dataRequestMultiPart(@Path("path1") String path1, @QueryMap(encoded = true) Map<String, String> action, @PartMap Map<String, RequestBody> fieldValue, @Part List<MultipartBody.Part> files);

    /*....................................Get API.........................................*/

    @GET("api/{path1}")
    public Observable<LinkedTreeMap> dataRequestGet(@Path("path1") String path1, @QueryMap(encoded = true) Map<String, String> action);

    @GET("api/{path1}/{path2}")
    public Observable<LinkedTreeMap> dataRequestGet2(@Header("Authorization") String header,@Path("path1") String path1, @Path("path2") String path2, @QueryMap(encoded = true) Map<String, String> action);

    @GET("api/{path1}")
    public Observable<LinkedTreeMap> dataRequestGet3(@Path("path1") String path1, @Query("page") String pageNo);

}
