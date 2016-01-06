package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.HospitalFragment.OnListFragmentInteractionListener;
import kosbrother.com.doctorguide.fragments.dummy.DummyHospitalContent.DummyHospital;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyHospital} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHospitalRecyclerViewAdapter extends RecyclerView.Adapter<MyHospitalRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Hospital> mHospitals;
    private final OnListFragmentInteractionListener mListener;

    public MyHospitalRecyclerViewAdapter(ArrayList<Hospital> items, OnListFragmentInteractionListener listener) {
        mHospitals = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hospital, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (mHospitals.get(position).grade){
            case "醫學中心":
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_biggest);
                break;
            case "區域醫院":
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_medium);
                break;
            case "地區醫院":
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_small);
                break;
            case "診所":
                holder.mImageView.setImageResource(R.mipmap.ic_hospital_smallest);
                break;
        }
        holder.mName.setText(mHospitals.get(position).name);
        holder.mAddress.setText(mHospitals.get(position).address);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(mHospitals.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHospitals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mName;
        public TextView mAddress;
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView)view.findViewById(R.id.grade_image);
            mName = (TextView)view.findViewById(R.id.hospial_name);
            mAddress = (TextView)view.findViewById(R.id.address);
        }
    }
}
