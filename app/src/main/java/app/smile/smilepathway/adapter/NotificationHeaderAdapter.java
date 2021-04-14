package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.notification.NotificationActivity;
import app.smile.smilepathway.model.NotificationHeaderModel;


public class NotificationHeaderAdapter extends RecyclerView.Adapter<NotificationHeaderAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<NotificationHeaderModel.ResultBean> data;
    List<View> itemViewList = new ArrayList<>();
    private int selected_position = 0;

    public NotificationHeaderAdapter(Activity activity, onClickItem listner, List<NotificationHeaderModel.ResultBean> data) {
        this.context = activity;
        this.listner = listner;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_notification_header, parent, false);
                break;
            case VIEW_TYPE_EMPTY:
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_general_layout, parent, false);
                break;
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            final NotificationHeaderModel.ResultBean entity = data.get(position);
            if (selected_position == position) {
                holder.layout_header.setBackground(context.getResources().getDrawable(R.drawable.light_orange_button));
                holder.tvNotificationTitle.setTextColor(context.getResources().getColor(R.color.orange));
            } else {
                holder.layout_header.setBackground(context.getResources().getDrawable(R.color.white));
                holder.tvNotificationTitle.setTextColor(context.getResources().getColor(R.color.text_grey));
            }

            holder.tvNotificationTitle.setText(entity.getNotification_type());
            holder.tvNotificationCount.setText(entity.getCounter());

            holder.itemView.setOnClickListener(view -> {
                if (listner != null) {
                    int position1 = holder.getAdapterPosition();
                    if (position1 != RecyclerView.NO_POSITION) {
                        listner.itemOnClick(entity.getId());
                        selected_position = position1;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (data != null && data.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else {
            return 1;
        }
    }

    public interface onClickItem {
        void itemOnClick(String id);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotificationTitle, tvNotificationCount;
        LinearLayout layout_header;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle);
            tvNotificationCount = itemView.findViewById(R.id.tvNotificationCount);
            layout_header = itemView.findViewById(R.id.layout_header);
        }
    }
}  