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
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickCategoryListEvent;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickDivisionInfoEvent;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

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
                GAManager.sendEvent(new MainClickDivisionInfoEvent(mCategories.get(position).name));

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(mCategories.get(position).name);
                builder.setMessage(mCategories.get(position).intro);
                builder.setPositiveButton("確定", null);
                builder.show();
            }
        });
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAManager.sendEvent(new MainClickCategoryListEvent(mCategories.get(position).name));

                Intent intent = new Intent(mContext, HospitalDoctorActivity.class);
                intent.putExtra(ExtraKey.CATEGORY_NAME, mCategories.get(position).name);
                intent.putExtra(ExtraKey.CATEGORY_ID, mCategories.get(position).id);
                mContext.startActivity(intent);
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
        View listView;

        CategoryViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.text_view);
            mButton = (Button) view.findViewById(R.id.detail_button);
            icon = (ImageView) view.findViewById(R.id.category_icon);
            listView = view;
        }
    }
}
