package app.smile.smilepathway.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ApiResultDialog extends Dialog {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_message)
    TextView txtMessage;
    @BindView(R.id.txt_ok)
    TextView txtOk;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;
    private ConfirmationCallBack mCallBack;

    private Context mContext;

    private String title;
    private String message;
    private String btnTextOK;
    private String btnTextSubmit;


    public ApiResultDialog(Context context, String title, String message, String btnTextOK, String btnTextSubmit, ConfirmationCallBack mCallBack) {
        super(context);
        this.mCallBack = mCallBack;
        this.mContext = context;
        this.message = message;
        this.btnTextOK = btnTextOK;
        this.btnTextSubmit = btnTextSubmit;

        this.title = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_common);

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_layout);
        WindowManager.LayoutParams wlp = window.getAttributes();

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        //wlp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        //wlp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        wlp.windowAnimations = R.style.dialog_animation;

        window.setAttributes(wlp);
        ButterKnife.bind(this);
        if (btnTextSubmit.length() > 0) {
            txtSubmit.setVisibility(View.VISIBLE);
            txtSubmit.setText(btnTextSubmit);
        } else {
            txtSubmit.setVisibility(View.GONE);
        }


        if (btnTextOK.length() > 0) {
            txtOk.setVisibility(View.VISIBLE);
            txtOk.setText(btnTextOK);
        } else {
            txtOk.setVisibility(View.GONE);
        }


        if (title.length() > 0) {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(title);
        } else {
            txtTitle.setVisibility(View.GONE);
        }

        if (message.length() > 0) {
            txtMessage.setVisibility(View.VISIBLE);
            txtMessage.setText(message);
        } else {
            txtMessage.setVisibility(View.GONE);
        }

    }


    @OnClick({R.id.txt_ok, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_ok:
                dismiss();
                mCallBack.onDecline();
                break;
            case R.id.txt_submit:
                dismiss();
                mCallBack.onAccept();
                break;
        }
    }

}
