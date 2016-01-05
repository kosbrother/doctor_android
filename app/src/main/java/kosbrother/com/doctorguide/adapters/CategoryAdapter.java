package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.HospitalDoctorActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Category;

/**
 * Created by steven on 12/25/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private static Context mContext;
    private ArrayList<Category> mCategories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        mCategories = categories;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, final int position) {
        holder.name.setText(mCategories.get(position).name);
        holder.icon.setImageResource(mCategories.get(position).resourceId);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(mCategories.get(position).name);
                builder.setMessage(mCategories.get(position).intro);
                builder.setPositiveButton("確定", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button mButton;
        ImageView icon;

        CategoryViewHolder(View view) {
            super(view);

            name = (TextView)view.findViewById(R.id.text_view);
            mButton = (Button)view.findViewById(R.id.detail_button);
            icon = (ImageView)view.findViewById(R.id.category_icon);

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
