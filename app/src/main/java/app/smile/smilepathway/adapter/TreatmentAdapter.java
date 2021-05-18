package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smileplan.SmilePlanActivity;
import app.smile.smilepathway.model.SmilePlanModel;


public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<SmilePlanModel.ResultBean> data;


    public TreatmentAdapter(SmilePlanActivity activity, onClickItem listner, List<SmilePlanModel.ResultBean> data) {
        this.context = activity;
        this.data = data;
        this.listner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_treatment, parent, false);
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
            final SmilePlanModel.ResultBean entity = data.get(position);
            holder.tvTitle.setText(entity.getTp_heading());
            /*holder.tvSubTitle.setText(entity.getTp_heading());
            holder.tvDateTime.setText(entity.getTp_date());*/
            /*lightgrey_border_button*/
            if (entity.getIs_selected().equalsIgnoreCase("0")) {
                holder.btn_view.setBackgroundResource(R.drawable.darkgrey_border_button);
                holder.btn_appointer.setBackgroundResource(R.drawable.darkgrey_border_button);
                holder.img_right_tick.setVisibility(View.GONE);
            } else {
                holder.img_right_tick.setVisibility(View.VISIBLE);
                holder.btn_view.setBackgroundResource(R.drawable.border_button);
                holder.btn_appointer.setBackgroundResource(R.drawable.new_black_border_button);
            }

                holder.btn_appointer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entity.getIs_selected().equalsIgnoreCase("1")) {
                            listner.onClickAppointer(position);
                        }
                    }
                });
                holder.btn_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (entity.getIs_selected().equalsIgnoreCase("1")) {
                            listner.onClickView(position);
                        }
                    }
                });
        } else {
            holder.txt_hint_msg.setText("NO PATHWAY FOUND");
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
        void onClickAppointer(int position);
        void onClickView(int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_right_tick;
        TextView tvTitle,tvSubTitle, tvDateTime;
        View view;
        LinearLayout btn_appointer, btn_view;
        TextView txt_hint_msg;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img_right_tick = itemView.findViewById(R.id.img_right_tick);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            btn_appointer = itemView.findViewById(R.id.btn_appointer);
            btn_view = itemView.findViewById(R.id.btn_view);
            txt_hint_msg = itemView.findViewById(R.id.txt_hint_msg);

        }
    }
}  