package app.smile.smilepathway.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.adapter.EstimatorAdapter;
import app.smile.smilepathway.model.TreatmentDetailsModel;

/**
 * Created by user on 27/7/18.
 */

public class AlertDialogUtil {
    private static Inflater inflater;

    public static AlertDialog showAlertDialog(final Activity activity, String msg) {
        if (activity.isFinishing())
            return null;

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(msg).setCancelable(false).setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        return alertDialog;
    }

    public static void showApiErrorDialog(final Activity activity, String msg) {
        if (activity.isFinishing())
            return;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setMessage(msg).setCancelable(true).setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //  alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
    }

    public static void showDialog(final Activity activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_common);

        TextView tittle = dialog.findViewById(R.id.txt_title);
        TextView message = dialog.findViewById(R.id.txt_message);
        TextView textok = dialog.findViewById(R.id.txt_ok);
        View view = dialog.findViewById(R.id.view);
        TextView submit = dialog.findViewById(R.id.txt_submit);
        if (title.equalsIgnoreCase("")) {
            tittle.setVisibility(View.GONE);
        } else {
            tittle.setText(title);
            tittle.setVisibility(View.VISIBLE);
        }
        message.setText(msg);
        textok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });
        dialog.show();
    }

    public static void showCustomDialog(final Activity activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_common);

        TextView tittle = dialog.findViewById(R.id.txt_title);
        TextView message = dialog.findViewById(R.id.txt_message);
        TextView textok = dialog.findViewById(R.id.txt_ok);
        View view = dialog.findViewById(R.id.view);
        TextView submit = dialog.findViewById(R.id.txt_submit);
        if (title.equalsIgnoreCase("")) {
            tittle.setVisibility(View.GONE);
        } else {
            tittle.setText(title);
            tittle.setVisibility(View.VISIBLE);
        }
        submit.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

        message.setText(msg);
        textok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showCustomDialogApiResult(final Context activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_common);

        TextView tittle = dialog.findViewById(R.id.txt_title);
        TextView message = dialog.findViewById(R.id.txt_message);
        TextView textok = dialog.findViewById(R.id.txt_ok);
        View view = dialog.findViewById(R.id.view);
        TextView submit = dialog.findViewById(R.id.txt_submit);

        if (title.equalsIgnoreCase("")) {
            tittle.setVisibility(View.GONE);
        } else {
            tittle.setText(title);
            tittle.setVisibility(View.GONE);
        }

        submit.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

        message.setText(msg);
        textok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public static void showCustomDialognew(final Activity activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_common);

        TextView tittle = dialog.findViewById(R.id.txt_title);
        TextView message = dialog.findViewById(R.id.txt_message);
        TextView textok = dialog.findViewById(R.id.txt_ok);
        View view = dialog.findViewById(R.id.view);
        TextView submit = dialog.findViewById(R.id.txt_submit);

        if (title.equalsIgnoreCase("")) {
            tittle.setVisibility(View.GONE);
        } else {
            tittle.setText(title);
            tittle.setVisibility(View.GONE);
        }

        submit.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

        message.setText(msg);
        textok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finishAffinity();
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        });
        dialog.show();

    }


    public static void estimatorDailog(final Activity activity, int pos, TreatmentDetailsModel.ResultBean data) {
        // if you need dialog width full parent then you can use bottom sheet dialog but issue radio theme issue and background layout not impliment
        //BottomSheetDialog dialog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(activity);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.alert_dialog_estimator);
        TextView appoitmentNumber = dialog.findViewById(R.id.txt_appoitment_number);
        RecyclerView rvEstimator = dialog.findViewById(R.id.rvEstimator);
        appoitmentNumber.setText("Appointment #" + data.getAppointments_arr().get(pos).getAppointment_number());

        EstimatorAdapter adapter = new EstimatorAdapter(activity, data.getAppointments_arr().get(pos).getProcedures());
        rvEstimator.setHasFixedSize(true);
        rvEstimator.setLayoutManager(new LinearLayoutManager(activity));
        rvEstimator.setAdapter(adapter);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }
}
