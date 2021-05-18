package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.model.ProceduresBean;
import app.smile.smilepathway.model.TreatmentDetailsModel;


public class AppoitmentItemListAdapter extends RecyclerView.Adapter<AppoitmentItemListAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    List<ProceduresBean> data;

    public AppoitmentItemListAdapter(Activity context, List<ProceduresBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_appoitment, parent, false);
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
            final ProceduresBean entity = data.get(position);

            holder.tvTitle.setText("Tooth #" + entity.getTooth_number());
            if (entity.getSurface() != null) {
                if (!entity.getSurface().equalsIgnoreCase("") && !entity.getSurface().equalsIgnoreCase("0")) {
                    holder.tv_Surface.setText("Surface #" + entity.getSurface());
                    holder.tv_Surface.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_Surface.setVisibility(View.GONE);
                }
            } else {
                holder.tv_Surface.setVisibility(View.GONE);
            }

            if (entity.getPrefix_suffix() != null) {
                holder.tvPrefix_suffix.setVisibility(View.VISIBLE);
                holder.tvPrefix_suffix.setText(entity.getPrefix_suffix());
            } else {
                holder.tvPrefix_suffix.setVisibility(View.GONE);
            }

            holder.tvSubTitle.setText(entity.getShort_description());
            holder.tvCode.setText(entity.getCdt_code());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubTitle, tvTitle, tv_Surface, tvCode,tvPrefix_suffix;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_Surface = itemView.findViewById(R.id.tv_Surface);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvPrefix_suffix = itemView.findViewById(R.id.tvPrefix_suffix);

        }
    }
}  