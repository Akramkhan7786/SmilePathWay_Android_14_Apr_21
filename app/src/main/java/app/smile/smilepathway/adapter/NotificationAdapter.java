package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.notification.NotificationActivity;
import app.smile.smilepathway.model.NotificationListModel;
import app.smile.smilepathway.model.NotificationListNewModel;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<NotificationListNewModel.ResultBean.RecordsBean> data;

    public NotificationAdapter(Activity activity, onClickItem listner, List<NotificationListNewModel.ResultBean.RecordsBean> data) {
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
                        .inflate(R.layout.item_notification, parent, false);
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
            final NotificationListNewModel.ResultBean.RecordsBean entity = data.get(position);
            holder.tv_schdule_time.setText(getTime(data.get(position).getCreated_at(), context));
            if (!entity.getModule().equalsIgnoreCase("") && entity.getModule() != null) {
                holder.tvNotificationTitle.setVisibility(View.VISIBLE);
                holder.tvNotificationTitle.setText(entity.getModule());
            } else {
                holder.tvNotificationTitle.setVisibility(View.GONE);
            }
            holder.tvDiscription.setText(entity.getMessage());

            Glide.with(context).load(entity.getNotification_icon()).into(holder.img_read_unread);
        } else {
            holder.txt_hint_msg.setText("No Notification Right Now!");
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
        void notificationItemClick(String id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_read_unread;
        TextView tvNotificationTitle, tvDiscription,txt_hint_msg,tv_schdule_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle);
            tvDiscription = itemView.findViewById(R.id.tvDiscription);
            img_read_unread = itemView.findViewById(R.id.img_read_unread);
            txt_hint_msg = itemView.findViewById(R.id.txt_hint_msg);
            tv_schdule_time = itemView.findViewById(R.id.tv_schdule_time);
        }
    }

    public static String getTime(String dateStr, Activity activity) {
        try {
            String returnStr = "";
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
//
            Date chatDate = df2.parse(dateStr);
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
            String time = timeFormat.format(chatDate);
            SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = new Date();
            Date nowDate = outputFmt.parse(outputFmt.format(date1));
            long diff = nowDate.getTime() - chatDate.getTime();
            long sec = diff / 1000;
            long min = sec / 60;
            long hour = min / 60;
            long days = hour / 24;
            if (days != 0) {
                Log.e("days", "" + days);
                if (days == 1) {
                    returnStr = "Yesterday" + " at " + time;
                } else if (days <= 7) {
                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                    String goal = outFormat.format(chatDate);
                    returnStr = goal + " at " + time;
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
                    String dateStr1 = dateFormat.format(chatDate);
                    returnStr = dateStr1 + " at " + time;
                }

            } else if (hour != 0) {
                Log.e("hour", "" + hour);
                returnStr = hour + " hours ago";
            } else if (min != 0) {
                Log.e("min", "" + min);
                returnStr = min + " min ago";
            } else {
                Log.e("sec", "" + sec);
                if (sec < 1) {
                    returnStr = "just now";
                } else {
                    returnStr = sec + " sec ago";
                }
            }
            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}  