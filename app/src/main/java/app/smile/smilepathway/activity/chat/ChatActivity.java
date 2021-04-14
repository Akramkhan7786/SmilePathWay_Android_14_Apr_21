package app.smile.smilepathway.activity.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import app.smile.smilepathway.R;
import app.smile.smilepathway.adapter.ChatAdapter;
import app.smile.smilepathway.adapter.NotificationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivUserIcon)
    ImageView ivUserIcon;
    @BindView(R.id.tvClinicName)
    TextView tvClinicName;
    @BindView(R.id.clHeader)
    ConstraintLayout clHeader;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.ivCamera)
    ImageView ivCamera;
    @BindView(R.id.edtEmailId)
    EditText edtEmailId;
    @BindView(R.id.ivSend)
    ImageView ivSend;
    @BindView(R.id.cvBottom)
    CardView cvBottom;
    @BindView(R.id.bottomCard)
    ConstraintLayout bottomCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        ButterKnife.bind(this);
        setupAdapter();
    }


    private void setupAdapter() {
        ChatAdapter adapter = new ChatAdapter(this);
        rvChat.setHasFixedSize(true);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);
    }

    @OnClick({R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
