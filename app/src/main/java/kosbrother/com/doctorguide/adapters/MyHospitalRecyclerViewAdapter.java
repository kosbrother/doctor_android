package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.fragments.HospitalFragment.OnListFragmentInteractionListener;
import kosbrother.com.doctorguide.fragments.dummy.DummyHospitalContent;
import kosbrother.com.doctorguide.fragments.dummy.DummyHospitalContent.DummyHospital;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyHospital} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHospitalRecyclerViewAdapter extends RecyclerView.Adapter<MyHospitalRecyclerViewAdapter.ViewHolder> {

    private final List<DummyHospital> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHospitalRecyclerViewAdapter(List<DummyHospitalContent.DummyHospital> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hospital, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        switch (position % 4){
            case 0:
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_biggest);
                break;
            case 1:
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_medium);
                break;
            case 2:
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_small);
                break;
            case 3:
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_smallest);
                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
        public DummyHospital mItem;
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView)view.findViewById(R.id.grade_image);
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
