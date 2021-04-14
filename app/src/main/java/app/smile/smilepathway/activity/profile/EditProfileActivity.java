package app.smile.smilepathway.activity.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import app.smile.smilepathway.Dialog.ApiResultDialog;
import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import app.smile.smilepathway.apirequest.ApiClass;
import app.smile.smilepathway.apirequest.BaseRequestData;
import app.smile.smilepathway.apirequest.Common;
import app.smile.smilepathway.apirequest.Constant;
import app.smile.smilepathway.apirequest.RequestedServiceDataModel;
import app.smile.smilepathway.apirequest.ResponseDelegate;
import app.smile.smilepathway.apirequest.ResponseType;
import app.smile.smilepathway.model.ErrorModel;
import app.smile.smilepathway.model.LoginModel;
import app.smile.smilepathway.model.UserDataModel;
import app.smile.smilepathway.utils.AlertDialogUtil;
import app.smile.smilepathway.utils.Permissions;
import app.smile.smilepathway.utils.PickerHelper;
import app.smile.smilepathway.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static app.smile.smilepathway.utils.Permissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class EditProfileActivity extends AppCompatActivity implements ResponseDelegate {

    static final int CAM_REQUEST = 1, RESULT_LOAD_IMAGE = 2;
    final static int Image_Capture = 100;
    final static int Image_SELECT = 200;
    private static final String IMAGE_DIRECTORY = "/SmilePathWay";
    @BindView(R.id.tvEditProfile)
    TextView tvEditProfile;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivUser)
    CircleImageView ivUser;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.ivEditProfile)
    ImageView ivEditProfile;
    @BindView(R.id.guideCenter)
    Guideline guideCenter;
    @BindView(R.id.tvFirstName)
    TextView tvFirstName;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.tvLastName)
    TextView tvLastName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.viewName)
    View viewName;
    @BindView(R.id.tvMobileNumber)
    TextView tvMobileNumber;
    @BindView(R.id.edtMobileNumber)
    EditText edtMobileNumber;
    @BindView(R.id.viewMobile)
    View viewMobile;
    @BindView(R.id.tvEmailId)
    TextView tvEmailId;
    @BindView(R.id.edtEmailId)
    EditText edtEmailId;
    @BindView(R.id.viewEmail)
    View viewEmail;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.edtAge)
    EditText edtAge;
    @BindView(R.id.viewAge)
    View viewAge;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFeMale)
    RadioButton rbFeMale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.cvDashboard)
    CardView cvDashboard;
    @BindView(R.id.rbAther)
    RadioButton rbAther;

    @BindView(R.id.notify_count_venue)
    TextView notifyCountVenue;
    @BindView(R.id.fl_bell)
    FrameLayout flBell;
    private UserDataModel userResponse;
    private String isGender = "";
    private RequestedServiceDataModel requestedServiceDataModel;
    private BaseRequestData baseRequestData;
    private int notifiTotalCount = 0;
    private String userChoosenTask;
    private File photofromCamera;
    private Uri mImageUri;
    private File file;
    private PickerHelper pickerHelper;

    //File img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        pickerHelper = PickerHelper.with(EditProfileActivity.this, EditProfileActivity.this);
        Permissions.checkGalleryPermision(this);
        userResponse = new Gson().fromJson(Common.getPreferences(this, "userData"), UserDataModel.class);
        notifiTotalCount = Integer.parseInt(Common.getPreferencesNotiCount(this, ApiClass.NOTIFICATION_TOTAL_COUNT));
        if (notifiTotalCount > 0) {
            notifyCountVenue.setText(notifiTotalCount + "");
            notifyCountVenue.setVisibility(View.VISIBLE);
        } else {
            notifyCountVenue.setVisibility(View.GONE);
        }
        setView();
    }

    private void setView() {
        // countryCode.set
        if (userResponse.getProfile_image() != null && !userResponse.getProfile_image().equalsIgnoreCase("")) {
            Glide.with(this).load(userResponse.getProfile_image())
                    .apply(new RequestOptions().placeholder(R.drawable.usertopbar).error(R.drawable.usertopbar))
                    .into(ivUser);
        }

        edtFirstName.setText(userResponse.getFirstname());
        edtLastName.setText(userResponse.getLastname());
        edtMobileNumber.setText(userResponse.getMobile_number());
        edtEmailId.setText(userResponse.getEmail());
        edtAge.setText(userResponse.getDob());

        if (userResponse.getGender().equalsIgnoreCase("M")) {
            rbMale.setChecked(true);
            rbFeMale.setChecked(false);
            rbAther.setChecked(false);
            isGender = userResponse.getGender();
        } else if (userResponse.getGender().equalsIgnoreCase("F")) {
            rbMale.setChecked(false);
            rbFeMale.setChecked(true);
            rbAther.setChecked(false);
            isGender = userResponse.getGender();
        } else {
            rbMale.setChecked(false);
            rbFeMale.setChecked(false);
            rbAther.setChecked(true);
            isGender = userResponse.getGender();
        }

        /*rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMale:
                        // do operations specific to this selection
                        isGender = "M";
                        break;
                    case R.id.rbFeMale:
                        // do operations specific to this selection
                        isGender = "F";
                        break;
                    case R.id.rbAther:
                        // do operations specific to this selection
                        isGender = "O";
                        break;
                }
            }
        });*/
    }

    @OnClick({R.id.ivBack, R.id.btnSubmit, R.id.ivUser, R.id.ivNotification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSubmit:
                serverRequestForUpdateProfile();
                break;
            case R.id.ivUser:
                boolean result = Permissions.checkGalleryPermision(this);
                if (result) {
                    selectImage();
                }
                break;
            case R.id.ivNotification:
                Utils.notificationRedirect(this);
                break;
        }
    }

    private void checkValidation() {
        if (!Common.getConnectivityStatus(EditProfileActivity.this))
            Utils.showInfoMsg(this, getString(R.string.internet_connection_msg));
        else if (edtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_name_msg));
        } else if (edtLastName.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_name_msg));
        } else if (edtMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_mobilenumber_msg));
        } else if (edtEmailId.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_emailaddress_msg));
        } else if (!Common.isValidEmail(edtEmailId.getText().toString().trim())) {
            Utils.showInfoMsg(this, getString(R.string.enter_valid_email_msg));
        } else if (edtAge.getText().toString().trim().equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_dob_msg));
        } else if (isGender.equalsIgnoreCase("")) {
            Utils.showInfoMsg(this, getString(R.string.enter_gender_msg));
        } else {
            serverRequestForUpdateProfile();
        }
    }

    private void serverRequestForUpdateProfile() {
        requestedServiceDataModel = new RequestedServiceDataModel(this, this);
        baseRequestData = new BaseRequestData();
        baseRequestData.setTag(ResponseType.UPDATE_PROFILE);
        baseRequestData.setServiceType(Constant.SERVICE_TYPE_POST);
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().FULLNAME, edtFirstName.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().LASTNAME, edtLastName.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().MOBILE, edtMobileNumber.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().DOB, edtAge.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().EMAIL, edtEmailId.getText().toString().trim());
        requestedServiceDataModel.putQurry(ApiClass.getmApiClass().GENDER, isGender);
        if (file != null) {
            requestedServiceDataModel.putFiles(ApiClass.getmApiClass().ProfilePicture, file);
        }
        baseRequestData.setSmileApi(Constant.SMILE_API);
        baseRequestData.setApiType("profile");
        requestedServiceDataModel.setBaseRequestData(baseRequestData);
        requestedServiceDataModel.execute();
    }

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Photo");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Permissions.checkGalleryPermision(EditProfileActivity.this);
            if (items[item].equals("Camera")) {
                userChoosenTask = "Camera";
                if (result)
                    cameraIntent();
            } else if (items[item].equals("Gallery")) {
                userChoosenTask = "Gallery";
                if (result)
                    galleryIntent();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Image_Capture);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "select file"), Image_SELECT);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == Image_Capture) {
                    onCaptureImageResultProfile(data);
                }
                if (requestCode == Image_SELECT) {
                    onSelectFromGalleryResultProfile(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResultProfile(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = pickerHelper.saveBitmapToFile(bitmap, "profile" + System.currentTimeMillis());
        ivUser.setImageBitmap(bitmap);
    }

    private void onSelectFromGalleryResultProfile(Intent data) {
        if (data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    file.createNewFile();
                    fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //ivUserpc.setRotation(getCameraPhotoOrientation(profileFile);
                file = pickerHelper.saveBitmapToFile(bitmap, "profile" + System.currentTimeMillis());
                ivUser.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
        }
    }

    @Override
    public void onNoNetwork(String message, BaseRequestData baseRequestData) {

    }

    @Override
    public void onSuccess(String jsondata, String message, BaseRequestData baseRequestData) {
        switch (baseRequestData.getTag()) {
            case ResponseType.UPDATE_PROFILE:
                try {
                    LoginModel response = new Gson().fromJson(jsondata, LoginModel.class);
                    //Utils.showInfoMsg(this, "Profile Successfully Updated");
                    Common.SetPreferences(this, "userData", new Gson().toJson(response.getResult().getUserdata()));
                    Intent intent = new Intent();
                    setResult(101, intent);
                    new ApiResultDialog(this, "","Profile Successfully Updated", "OK", "", new ConfirmationCallBack() {
                        @Override
                        public void onAccept() {

                        }

                        @Override
                        public void onDecline() {
                            finish();
                        }
                    }).show();
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
}

/*


    //Upload image
    private void uploadImg() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 200);
            }
        } else {
            OpenCameraPopUp();
        }

    }

    private void OpenCameraPopUp() {
        final Dialog dialog = new Dialog(this);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.imageupload_layout);
        dialog.show();

        TextView tv_takeimg = dialog.findViewById(R.id.tv_takeimg);
        TextView tv_takecameraroll = dialog.findViewById(R.id.tv_takecameraroll);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);

        tv_takeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activeCamera();
                dialog.dismiss();
            }
        });
        tv_takecameraroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeGallery();
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void activeGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private void activeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAM_REQUEST);
    }

      @Override
      public void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if (resultCode == RESULT_OK) {
              if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                  if (data != null) {
                      Uri contentURI = data.getData();
                      try {
                          Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                          // Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                          ivUser.setImageBitmap(null);
                          ivUser.setImageBitmap(bitmap);
                        //  img = saveImage(bitmap);
                      } catch (IOException e) {
                          e.printStackTrace();
                          // Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                      }
                  }
              } else if (requestCode == CAM_REQUEST) {
                  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                  ivUser.setImageBitmap(null);
                  ivUser.setImageBitmap(thumbnail);
                  //img = saveImage(thumbnail);

              }
          }
      }

    public File saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
*/
