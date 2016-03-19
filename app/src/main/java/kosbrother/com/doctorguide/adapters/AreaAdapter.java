package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosbrother.com.doctorguide.R;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private final String[] areaStrings;
    private final RecyclerViewClickListener listener;

    public AreaAdapter(String[] areaStrings, RecyclerViewClickListener listener) {
        this.areaStrings = areaStrings;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.areaTextView.setText(areaStrings[position]);
    }

    @Override
    public int getItemCount() {
        return areaStrings.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView areaTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            areaTextView = (TextView) itemView;
        }

    }

    public interface RecyclerViewClickListener {
        void onItemClick(int position);
    }
}
