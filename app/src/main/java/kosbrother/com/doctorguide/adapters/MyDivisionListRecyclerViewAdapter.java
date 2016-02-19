package kosbrother.com.doctorguide.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.BlankViewHolder;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.fragments.DivisionListFragment.OnListFragmentInteractionListener;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickDivisionListEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickDivisionInfoEvent;

public class MyDivisionListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ITEM_TYPE {
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
        if (viewType == ITEM_TYPE.ITEM.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_division_list_item, parent, false);
            RatingBar rating = (RatingBar) view.findViewById(R.id.score_rating_bar);
            DrawableCompat.setTint(rating.getProgressDrawable(), ContextCompat.getColor(parent.getContext(), R.color.rating_bar_color));
            return new ViewHolder(view);
        } else {
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
                        Division division = mValues.get(position);

                        GAManager.sendEvent(new HospitalClickDivisionInfoEvent(division.name));
                        mListener.onListFragmentInteraction(v, division);
                    }
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        Division division = mValues.get(position);

                        GAManager.sendEvent(new HospitalClickDivisionListEvent(division.name));
                        mListener.onListFragmentInteraction(v, division);
                    }
                }
            });
            holder.mScore.setText(String.format("%.1f", mValues.get(position).avg));
            holder.mRatingBar.setRating(mValues.get(position).avg);
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
        public TextView mScore;
        public RatingBar mRatingBar;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRatingBar = (RatingBar) view.findViewById(R.id.score_rating_bar);
            mScore = (TextView) view.findViewById(R.id.score1);
            mCategoryImage = (ImageView) view.findViewById(R.id.category_icon);
            mDivision = (TextView) view.findViewById(R.id.division);
            mButton = (Button) view.findViewById(R.id.detail_button);
        }

    }
}
