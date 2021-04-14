package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smilecredit.SmileCreditActivity;
import app.smile.smilepathway.activity.smilecredit.SmileInvoiceViewActivity;
import app.smile.smilepathway.model.InvoiceModel;
import app.smile.smilepathway.model.InvoiceViewModel;


public class InvoiceViewAdapter extends RecyclerView.Adapter<InvoiceViewAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    List<InvoiceViewModel.ResultBean.InvoiceDetailsBean> data;

    public InvoiceViewAdapter(Activity context, List<InvoiceViewModel.ResultBean.InvoiceDetailsBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_invoice_view, parent, false);
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
            final InvoiceViewModel.ResultBean.InvoiceDetailsBean entity = data.get(position);
            holder.tvDesc.setText(entity.getDescription());
            holder.tv_amount.setText("$" + entity.getTotal_amount());// total amount aayga
            holder.tvAmountRate.setText("$" + entity.getAmount());// only amount
            holder.tvTax.setText(entity.getTax() + "%");
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
        TextView tvDesc, tv_amount, tvTax, tvAmountRate;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tvTax = itemView.findViewById(R.id.tvTax);
            tvAmountRate = itemView.findViewById(R.id.tvAmountRate);
        }
    }
}  