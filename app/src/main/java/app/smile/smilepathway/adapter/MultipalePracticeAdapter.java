package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.setting.LoginActivity;
import app.smile.smilepathway.model.LoginModel;


public class MultipalePracticeAdapter extends RecyclerView.Adapter<MultipalePracticeAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<LoginModel.ResultBean.PracticeBean> data;

    public MultipalePracticeAdapter(LoginActivity activity, onClickItem listner, List<LoginModel.ResultBean.PracticeBean> data) {
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
                        .inflate(R.layout.item_multipal_practice, parent, false);
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
            final LoginModel.ResultBean.PracticeBean entity = data.get(position);
            holder.tv_practice_name.setText(entity.getName());
            Glide.with(context).load(R.mipmap.right_arrow_grey).into(holder.img_arrow);
            holder.layout_multipal_practice.setBackground(context.getResources().getDrawable(R.drawable.lightgrey_border_button));
            holder.tv_practice_name.setTextColor(context.getResources().getColor(R.color.grey_));

            holder.itemView.setOnClickListener((View v) -> {
                holder.layout_multipal_practice.setBackground(context.getResources().getDrawable(R.drawable.light_orange_button));
                holder.tv_practice_name.setTextColor(context.getResources().getColor(R.color.orange));
                Glide.with(context).load(R.mipmap.right_arrow).into(holder.img_arrow);
                listner.itemOnClick(position);
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
        void itemOnClick(int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_multipal_practice;
        TextView tv_practice_name;
        ImageView img_arrow;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            layout_multipal_practice = itemView.findViewById(R.id.layout_multipal_practice);
            tv_practice_name = itemView.findViewById(R.id.tv_practice_name);
            img_arrow = itemView.findViewById(R.id.img_arrow);

        }
    }
}  