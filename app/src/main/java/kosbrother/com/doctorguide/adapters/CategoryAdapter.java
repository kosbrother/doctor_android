package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosbrother.com.doctorguide.HospitalDoctorActivity;
import kosbrother.com.doctorguide.R;

/**
 * Created by steven on 12/25/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private static Context mContext;
    private String[] mTitles;

    public CategoryAdapter(Context context,String[] titles) {
        mTitles = titles;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.mTextView.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CategoryViewHolder(View view) {
            super(view);
            mTextView = (TextView)view.findViewById(R.id.text_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, HospitalDoctorActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
