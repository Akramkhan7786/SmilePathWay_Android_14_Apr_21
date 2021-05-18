package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smileplan.SmilePlanListActivity;
import app.smile.smilepathway.model.AppointmentsArrBean;
import app.smile.smilepathway.model.TreatmentDetailsModel;


public class SmilePlanListAdapter extends RecyclerView.Adapter<SmilePlanListAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<AppointmentsArrBean> data;
    private AppoitmentItemListAdapter adapter;


    public SmilePlanListAdapter(SmilePlanListActivity activity, onClickItem listner, List<AppointmentsArrBean> data) {
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
                        .inflate(R.layout.item_treatment_appoitment_details, parent, false);
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
            final AppointmentsArrBean entity = data.get(position);

            holder.tvAppoitment.setText("Appointment #" + entity.getAppointment_number());
            holder.tvTimeDuration.setText(entity.getAppointment_time() + " min");
            holder.tvSchduleTime.setText(entity.getSchedule_date_time());
        /*    if (entity.getAppointment_status().equalsIgnoreCase("1")) {
                holder.tvSchduleTime.setText("Requested " + entity.getSchedule_date());
            } else if (entity.getAppointment_status().equalsIgnoreCase("0")) {
                holder.tvSchduleTime.setText("unschedule ");
            } else {
                holder.tvSchduleTime.setText("schedule " + entity.getSchedule_date());
            }*/
            holder.tvAmount.setText("$" + entity.getAppointment_amount());
            holder.tvAmount.setOnClickListener(view -> listner.onClickPrice(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClickItem(position);
                }
            });


            // amenities adapter call
            if (data.get(position).getProcedures().size() != 0) {
                adapter = new AppoitmentItemListAdapter(context, data.get(position).getProcedures());
                holder.rv_appoitment.setHasFixedSize(true);
                holder.rv_appoitment.setLayoutManager(new LinearLayoutManager(context));
                holder.rv_appoitment.setAdapter(adapter);
            } else {
                holder.rv_appoitment.setVisibility(View.GONE);
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
        void onClickPrice(int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppoitment, tvTimeDuration, tvSchduleTime, tvAmount;
        RecyclerView rv_appoitment;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvAppoitment = itemView.findViewById(R.id.tv_appoitment);
            tvTimeDuration = itemView.findViewById(R.id.tv_time_duration);
            tvSchduleTime = itemView.findViewById(R.id.tv_schdule_time);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            rv_appoitment = itemView.findViewById(R.id.rvAppointmentList);
        }
    }
}  