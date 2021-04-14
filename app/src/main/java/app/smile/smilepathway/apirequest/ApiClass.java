package app.smile.smilepathway.apirequest;


/**
 * Created by user on 31/10/17.
 */

public class ApiClass {
    public static final String BASE_URL = "http://192.168.0.228/ppd/api/";
    public static final String FULLNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String MOBILE = "mobile_number";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";
    public static final String PASSWORD = "password";
    public static final String PRACTICE_ID = "practice_id";
    public static final String OLDPASSWORD = "oldpassword";
    public static final String ProfilePicture = "profile_image";
    public static final String DOB = "dob";
    public static final String DEVICE_TYPE = "device_type";
    public static final String DEVICE_ID = "device_id";
    public static final String USER_ID = "userid";
    public static final String TOKEN = "token";
    public static final String ACTION = "action";
    public static final String ANDROID = "ANDROID";
    public static final String FILLTER = "filter";
    public static final String PMS_PTNT_ID = "pms_patient_id";
    public static final String APPOITMENT_NUMBER = "appointment_number";
    public static final String APPOITMENT_TIME = "appointment_time";
    public static final String PMS_ID = "pms_treatment_id";
    public static final String VALUE = "value";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String MESSAGE = "message";
    public static final String PAGE_NO = "page";
    public static final String INVOICE_ID = "invoice_id";
    public static final String PRACTICE_STRIPE_ID = "practice_stripe_id";
    public static final String STRIPE_PAYMENT_INTENT_ID = "stripe_payment_intent_id";
    public static final String STRIPE_STATUS = "stripe_status";
    public static final String NOTIFICATION_TOTAL_COUNT = "notification_total_count";
    public static final String CHAT_DATA = "chat_data";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String MONTH = "month";
    public static final String MIN_POINT = "min_point";
    public static final String MAX_POINT = "max_point";
    public static final String TYPE = "type";
    public static String AUTHORIZATION = "Authorization";
    public static ApiClass mApiClass;

    public static ApiClass getmApiClass() {
        if (mApiClass == null) {
            mApiClass = new ApiClass();
        }
        return mApiClass;
    }


}
