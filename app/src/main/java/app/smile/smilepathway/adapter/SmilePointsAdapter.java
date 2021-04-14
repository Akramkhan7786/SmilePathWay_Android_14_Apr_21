package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.model.SmilePointsModel;


public class SmilePointsAdapter extends RecyclerView.Adapter<SmilePointsAdapter.ViewHolder> {


    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<SmilePointsModel.ResultBean.CreditDetailsBean> data;

    public SmilePointsAdapter(Activity mContext, onClickItem listner, List<SmilePointsModel.ResultBean.CreditDetailsBean> data) {
        this.context = mContext;
        this.listner = listner;
        this.data = data;
    }


    @Override
    public SmilePointsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_credit_history, parent, false);
                break;
            case VIEW_TYPE_EMPTY:
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_general_layout, parent, false);
                break;
        }
        return new SmilePointsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmilePointsAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            final SmilePointsModel.ResultBean.CreditDetailsBean entity = data.get(position);
            holder.tvTitle.setText(entity.getType());
            holder.tvSubTitle.setText(entity.getDescription());
            holder.tvDateTime.setText(entity.getCreated_at());
            if (entity.getTransaction_type().equalsIgnoreCase("credit")) {
                holder.tvPoints.setText("+" + entity.getCredit() + "pts");
                holder.tvPoints.setTextColor(context.getResources().getColor(R.color.new_green));
            } else {
                holder.tvPoints.setText("-" + entity.getDebit() + "pts");
                holder.tvPoints.setTextColor(context.getResources().getColor(R.color.red));
            }
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
        void onClickItem(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPoints, tvSubTitle, tvDateTime;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}  