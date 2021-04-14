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
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smileplan.SmilePlanDetailsActivity;
import app.smile.smilepathway.model.InvoiceViewModel;
import app.smile.smilepathway.model.ProceduresBean;


public class EstimatorAdapter extends RecyclerView.Adapter<EstimatorAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    List<ProceduresBean> data;
    Boolean flagDeatils = true;

    public EstimatorAdapter(Activity context, List<ProceduresBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_estimator, parent, false);
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
            holder.tv_cdnCode.setText(entity.getCdt_code());
            holder.tv_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (flagDeatils) {
                        holder.layoutDeatils.setVisibility(View.VISIBLE);
                        Glide.with(context).load(R.mipmap.arrow_up).into(holder.img_drop_down);
                        flagDeatils = false;
                    } else {
                        holder.layoutDeatils.setVisibility(View.GONE);
                        Glide.with(context).load(R.mipmap.arrow_down).into(holder.img_drop_down);
                        flagDeatils = true;
                    }
                }
            });

            if (entity.getOffice_fee() != null) {
                holder.tvOfficeFee.setText("$ " + entity.getOffice_fee());
            } else {
                holder.tvOfficeFee.setText("$ " +"0");
            }
            if (entity.getProcedure_insurance_fee() != null) {
                holder.tvInsuFee.setText("$ " + entity.getProcedure_insurance_fee());
            } else {
                holder.tvInsuFee.setText("$ " + "0");
            }
            if (entity.getInsurance_coverage() != null) {
                holder.tvInsuCover.setText("$ " + entity.getInsurance_coverage());
            } else {
                holder.tvInsuCover.setText("$ " + "0");
            }
            if (entity.getInsurance_coverage_amount() != null) {
                holder.tvEstimateInsurPayment.setText("$ " + entity.getInsurance_coverage_amount());
            } else {
                holder.tvEstimateInsurPayment.setText("$ " + "0");
            }
            if (entity.getProcedure_payable_amount() != null) {
                holder.tvEstiPatientPayment.setText("$ " + entity.getProcedure_payable_amount());
            } else {
                holder.tvEstiPatientPayment.setText("$ " + "0");
            }
            if (entity.getInsurance_coverage_amount() != null) {
                holder.tvEstimatedSaving.setText("$ " + entity.getInsurance_coverage_amount());// calc
            } else {
                holder.tvEstimatedSaving.setText("$ " + "0");// calc
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cdnCode, tvOfficeFee, tvInsuFee, tvInsuCover, tvEstimateInsurPayment, tvEstiPatientPayment, tvEstimatedSaving;
        ImageView img_drop_down;
        LinearLayout layoutDeatils,tv_details;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_cdnCode = itemView.findViewById(R.id.tv_cdnCode);
            tvOfficeFee = itemView.findViewById(R.id.tvOfficeFee);
            tvInsuFee = itemView.findViewById(R.id.tvInsuFee);
            tvInsuCover = itemView.findViewById(R.id.tvInsuCover);
            tvEstimateInsurPayment = itemView.findViewById(R.id.tvEstimateInsurPayment);
            tvEstiPatientPayment = itemView.findViewById(R.id.tvEstiPatientPayment);
            tvEstimatedSaving = itemView.findViewById(R.id.tvEstimatedSaving);
            img_drop_down = itemView.findViewById(R.id.img_drop_down);
            layoutDeatils = itemView.findViewById(R.id.layoutDeatils);
            tv_details = itemView.findViewById(R.id.tv_details);
        }
    }
}  