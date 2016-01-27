package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.BlankViewHolder;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.fragments.DivisionListFragment.OnListFragmentInteractionListener;

public class MyDivisionListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static enum ITEM_TYPE {
        ITEM,
        ITEM_BLANK
    }

    private final List<Division> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDivisionListRecyclerViewAdapter(List<Division> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.ITEM.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_division_list_item, parent, false);
            return new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_blank, parent, false);
            return new BlankViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder oHolder, final int position) {
        if (oHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) oHolder;
            holder.mDivision.setText(mValues.get(position).name);
            if (Category.getCategoryById(mValues.get(position).category_id) != null)
                holder.mCategoryImage.setImageResource(Category.getCategoryById(mValues.get(position).category_id).resourceId);
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(v, mValues.get(position));
                    }
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(v, mValues.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mValues.size() ? ITEM_TYPE.ITEM_BLANK.ordinal() : ITEM_TYPE.ITEM.ordinal();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ImageView mCategoryImage;
        public TextView mDivision;
        public Button mButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategoryImage = (ImageView)view.findViewById(R.id.category_icon);
            mDivision = (TextView)view.findViewById(R.id.division);
            mButton = (Button)view.findViewById(R.id.detail_button);
        }

    }
}
