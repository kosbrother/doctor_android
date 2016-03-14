package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosbrother.com.doctorguide.R;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private final String[] areaStrings;
    private View.OnClickListener listener;

    public AreaAdapter(String[] areaStrings, View.OnClickListener listener) {
        this.areaStrings = areaStrings;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        v.setOnClickListener(listener);
        return new ViewHolder(v);
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
}
