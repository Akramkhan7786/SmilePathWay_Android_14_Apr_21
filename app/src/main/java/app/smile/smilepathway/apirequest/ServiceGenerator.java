package app.smile.smilepathway.apirequest;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Asus on 13-07-2015.
 */
public class ServiceGenerator {
    public static final String BASE_URL = "http://18.191.232.199/smilepathway/sm-patient-portal/";
    public static String PRIVACY_URL = BASE_URL +  "privacy_policy";
    public static String ABOUTUS_URL =BASE_URL +  "about_us";
    public static String TermsAndCondition_URL =BASE_URL +  "terms_conditions";
    public static String CHAT_URL =BASE_URL +  "mobile_chat/";
    // live comment url for chating
    public static String CHATURL = "http://18.191.232.199:4000/chatroom";

    // No need to instantiate this class.
    private ServiceGenerator() {

    }


    public static <S> Retrofit createService(Class<S> serviceClass, String token) {

// set your desired log level
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.connectTimeout(30, TimeUnit.MINUTES);
        httpClient.readTimeout(30, TimeUnit.MINUTES);
        httpClient.writeTimeout(30, TimeUnit.MINUTES);

        if (token != null && !token.equalsIgnoreCase("") && !token.equalsIgnoreCase("0")) {
            Log.e("token", token.toString());

            httpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header(ApiClass.getmApiClass().AUTHORIZATION, "Bearer " + token)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }


}
