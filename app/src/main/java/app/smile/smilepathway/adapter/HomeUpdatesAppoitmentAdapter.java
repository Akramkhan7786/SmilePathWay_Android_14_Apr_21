package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.DashboardHomeActivity;
import app.smile.smilepathway.model.AppointmentsArrBean;
import app.smile.smilepathway.model.HomeResponseModel;


public class HomeUpdatesAppoitmentAdapter extends RecyclerView.Adapter<HomeUpdatesAppoitmentAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    List<AppointmentsArrBean> data;
    onClickItem listner;

    public HomeUpdatesAppoitmentAdapter(DashboardHomeActivity context, onClickItem listner, List<AppointmentsArrBean> data) {
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
                        .inflate(R.layout.item_home_appoitment, parent, false);
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
            //holder.txt_plan_title.setText(entity);
            holder.txt_dateTIme.setText(entity.getSchedule_date());
            holder.txt_appoitment.setText("Appointment #"+entity.getPms_treatment_id());
            holder.tv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.viewOnClickAppointer(position);
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
        void viewOnClickAppointer(int pos);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_plan_title, txt_dateTIme, txt_appoitment, tv_view;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txt_plan_title = itemView.findViewById(R.id.txt_appoitment_title);
            txt_dateTIme = itemView.findViewById(R.id.txt_dateTIme);
            txt_appoitment = itemView.findViewById(R.id.txt_appoitment);
            tv_view = itemView.findViewById(R.id.tv_view);
        }
    }
}  