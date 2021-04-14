package app.smile.smilepathway.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.smile.smilepathway.Interface.ConfirmationCallBack;
import app.smile.smilepathway.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ConfirmationDailoge extends Dialog {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_content)
    TextView txtContent;
    @BindView(R.id.btn_decline)
    Button btnDecline;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    private ConfirmationCallBack mCallBack;

    private Context mContext;

    private String content;
    private String btnTextDecline;
    private String btnTextAccept;


    public ConfirmationDailoge(Context context, String content, String btnTextDecline, String btnTextAccept, ConfirmationCallBack mCallBack) {
        super(context);
        this.mCallBack = mCallBack;
        this.mContext = context;
        this.content = content;
        this.btnTextDecline = btnTextDecline;
        this.btnTextAccept = btnTextAccept;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialauge);

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
        if (btnTextAccept.length() > 0) {
            btnAccept.setVisibility(View.VISIBLE);
            btnAccept.setText(btnTextAccept);
        } else {
            btnAccept.setVisibility(View.GONE);
        }


        if (btnTextDecline.length() > 0) {
            btnDecline.setVisibility(View.VISIBLE);
            btnDecline.setText(btnTextDecline);
        } else {
            btnDecline.setVisibility(View.GONE);
        }


        if (content.length() > 0) {
            txtContent.setVisibility(View.VISIBLE);
            txtContent.setText(content);
        } else {
            txtContent.setVisibility(View.GONE);
        }

    }


    @OnClick({R.id.btn_decline, R.id.btn_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_decline:
                dismiss();
                mCallBack.onDecline();
                break;
            case R.id.btn_accept:
                dismiss();
                mCallBack.onAccept();
                break;
        }
    }
}
