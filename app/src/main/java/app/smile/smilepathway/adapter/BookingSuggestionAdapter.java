package app.smile.smilepathway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.smile.smilepathway.R;
import app.smile.smilepathway.activity.smileplan.SmilePlanDetailsActivity;


public class BookingSuggestionAdapter extends RecyclerView.Adapter<BookingSuggestionAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public Activity context;
    onClickItem listner;
    List<String> data;
    private String bookingNumber = "";
    int appoNum =1;

    public BookingSuggestionAdapter(SmilePlanDetailsActivity activity, onClickItem listner, List<String> data, String appointment_number) {
        this.context = activity;
        this.listner = listner;
        this.data = data;
        this.bookingNumber = appointment_number;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_appoitment_suggestion, parent, false);
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
            String number = String.valueOf(appoNum ++);
            holder.tvAppoitment.setText("Option "+number);
            holder.tvDate.setText(data.get(position));
            holder.tvBooking.setOnClickListener(v -> listner.bookingAppoitment(position));
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
        void bookingAppoitment(int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppoitment, tvDate, tvBooking;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvAppoitment = itemView.findViewById(R.id.tv_appoitment);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvBooking = itemView.findViewById(R.id.tv_booking);

        }
    }
}  