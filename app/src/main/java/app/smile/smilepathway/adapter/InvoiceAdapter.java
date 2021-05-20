package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smilecredit.SmileCreditActivity;
import app.smile.smilepathway.model.InvoiceModel;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<InvoiceModel.ResultBean> data;

    public InvoiceAdapter(SmileCreditActivity context, onClickItem listner, List<InvoiceModel.ResultBean> data) {
        this.context = context;
        this.listner = listner;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_invoice, parent, false);
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
            final InvoiceModel.ResultBean entity = data.get(position);
            if (entity.getInvoice_number() != null)
                holder.tvInvoiceNumber.setText("Invoice #" + entity.getInvoice_number());
            if (entity.getGross_amount() != null)
                holder.tv_amount.setText("$" + entity.getGross_amount());
            if (entity.getStatus().equalsIgnoreCase("Paid")) {
                holder.tv_paid_status.setText(entity.getStatus());
                holder.tv_paid_status.setTextColor(context.getResources().getColor(R.color.new_green));
                holder.tv_paid_status.setVisibility(View.VISIBLE);
            } else if (entity.getStatus().equalsIgnoreCase("Presented")){
                holder.tv_paid_status.setVisibility(View.GONE);
            } else {
                holder.tv_paid_status.setVisibility(View.VISIBLE);
                holder.tv_paid_status.setText(entity.getStatus());
                holder.tv_paid_status.setTextColor(context.getResources().getColor(R.color.red));
            }

            if (entity.getInvoice_create_date() != null)
                holder.tvInvoiceDate.setText(entity.getInvoice_create_date());

                if (entity.getStatus().equalsIgnoreCase("Paid")) {
                   // if (entity.getPayment_date() != null)
                    holder.tv_dueDate_payment_date.setText("Payment Date");
                    holder.tvPaymentDate.setText(entity.getPayment_date());
                } else {
                  //  if (entity.getDue_date() != null)
                    holder.tv_dueDate_payment_date.setText("Due Date");
                    holder.tvPaymentDate.setText(entity.getDue_date());
                }
            holder.tv_view.setOnClickListener(view -> listner.itemOnClick(entity.getId()));
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
        TextView tvInvoiceNumber, tv_amount, tv_view, tv_paid_status, tvInvoiceDate, tvPaymentDate,tv_dueDate_payment_date;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvInvoiceNumber = itemView.findViewById(R.id.tvInvoiceNumber);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_view = itemView.findViewById(R.id.tv_view);
            tv_paid_status = itemView.findViewById(R.id.tv_paid_status);
            tvInvoiceDate = itemView.findViewById(R.id.tvInvoiceDate);
            tvPaymentDate = itemView.findViewById(R.id.tvPaymentDate);
            tv_dueDate_payment_date = itemView.findViewById(R.id.tv_dueDate_payment_date);
        }
    }
}  