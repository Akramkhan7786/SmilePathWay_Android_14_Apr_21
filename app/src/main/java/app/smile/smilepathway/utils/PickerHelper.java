package app.smile.smilepathway.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by deepak on 8/5/18.
 */

public class PickerHelper {


    public static PickerHelper with(Context context, Object screen) {
        return new PickerHelper(context, screen);
    }

    public int ACTION_REQUEST_CAMERA = 101, ACTION_REQUEST_GALLERY = 102;
    Uri imageUri;
    Context contextd;
    Object screen;
    Activity activity;

    PickerHelper(Context context, Object screen) {
        this.contextd = context;
        this.screen = screen;
        setUpUri();
        setUpActivity();
    }

    private void setUpUri() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Collabalist");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        try {
            imageUri = contextd.getApplicationContext().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpActivity() {
        if (screen instanceof Activity) {
            activity = ((Activity) screen);
        } else if (screen instanceof AppCompatActivity) {
            activity = ((AppCompatActivity) screen);
        } else if (screen instanceof Fragment) {
            activity = ((Fragment) screen).getActivity();
        } else if (screen instanceof android.app.Fragment) {
            activity = ((android.app.Fragment) screen).getActivity();
        } else if (screen instanceof FragmentActivity) {
            activity = ((FragmentActivity) screen);
        } else {
            Log.e("Picker", "Unexpected Error..!\nContact Developer.");
        }
    }

    public Bitmap getBitmap(Intent data, int requestType) {
        Bitmap bit = null;
        Uri temp_uri = null;


        if (requestType == ACTION_REQUEST_CAMERA) {
            try {
                if (imageUri == null && data.getData() != null) {
                    bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), data.getData());
                } else if (data == null) {
                    bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imageUri);
                    if (bit == null) {
                        imageUri = Uri.fromFile(getOutputMediaFile(1));
                        bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imageUri);
                    }
                } else if (imageUri != null && data.getData() == null) {
                    try {
                        bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imageUri);
                    } catch (Exception e) {
                        imageUri = Uri.fromFile(getOutputMediaFile(1));
                        bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imageUri);
                    }
                } else
                    bit = (Bitmap) data.getExtras().get("data");
                if (bit == null) {
                    imageUri = Uri.fromFile(getOutputMediaFile(1));
                    bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imageUri);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp_uri = imageUri;
        } else if (requestType == ACTION_REQUEST_GALLERY) {
            Uri imgUri = data.getData();
            try {
                bit = MediaStore.Images.Media.getBitmap(contextd.getApplicationContext().getContentResolver(), imgUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp_uri = imgUri;
        }

        if (temp_uri != null) {
            try {
                bit = rotateImageIfRequired(contextd, bit, temp_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bit;
    }

    Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public File saveBitmapToFile(Bitmap bitmap, String name) {
        File filesDir = contextd.getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");
        if (imageFile.exists()) {
            imageFile.delete();
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }

    private File getOutputMediaFile(int type) {
        File filesDir = contextd.getApplicationContext().getFilesDir();

        /**Create the storage directory if it does not exist*/
        if (!filesDir.exists()) {
            if (!filesDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = System.currentTimeMillis() + "";
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(filesDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    String getGalleryPath(final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(contextd, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(contextd, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(contextd, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(contextd, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    String getDataColumn(Context context, Uri uri, String selection,
                         String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}

