package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smileestimator.EstimatorActivity;
import app.smile.smilepathway.model.AppointmentsArrBean;
import app.smile.smilepathway.model.ProceduresBean;


public class HomeEstimatorAdapter extends RecyclerView.Adapter<HomeEstimatorAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    List<AppointmentsArrBean> data;
    private EstimatorAdapter adapter;

    public HomeEstimatorAdapter(Activity context, List<AppointmentsArrBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_home_estimator, parent, false);
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
            holder.txt_appoitment_number.setText("Appointment #" + entity.getAppointment_number());

            // estimator adapter call
            if (data.get(position).getProcedures().size() != 0) {
                adapter = new EstimatorAdapter(context, data.get(position).getProcedures());
                holder.rvEstimator.setHasFixedSize(true);
                holder.rvEstimator.setLayoutManager(new LinearLayoutManager(context));
                holder.rvEstimator.setAdapter(adapter);
            } else {
                holder.rvEstimator.setVisibility(View.GONE);
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
        TextView txt_appoitment_number;
        RecyclerView rvEstimator;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txt_appoitment_number = itemView.findViewById(R.id.txt_appoitment_number);
            rvEstimator = itemView.findViewById(R.id.rvEstimator);
        }
    }
}  