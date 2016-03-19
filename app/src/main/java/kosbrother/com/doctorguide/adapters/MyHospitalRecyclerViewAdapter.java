package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.HospitalFragment.OnListFragmentInteractionListener;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.hospitaldoctor.HospitalDoctorClickHospitalListEvent;

import static kosbrother.com.doctorguide.Util.SphericalUtil.computeDistanceBetween;

public class MyHospitalRecyclerViewAdapter extends RecyclerView.Adapter<MyHospitalRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Hospital> mHospitals;
    private final OnListFragmentInteractionListener mListener;
    private LatLng mLocation;

    public MyHospitalRecyclerViewAdapter(ArrayList<Hospital> items, OnListFragmentInteractionListener listener, LatLng location) {
        mHospitals = items;
        mListener = listener;
        mLocation = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hospital, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (mHospitals.get(position).grade) {
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
        double distance = computeDistanceBetween(mLocation, new LatLng(mHospitals.get(position).latitude, mHospitals.get(position).longitude));
        holder.mDistance.setText(Util.formatNumber(distance));
        holder.mCommentNum.setText(mHospitals.get(position).comment_num + "");
        holder.mRecommendNum.setText(mHospitals.get(position).recommend_num + "");
        holder.mScore.setText(String.format("%.1f", mHospitals.get(position).avg));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Hospital hospital = mHospitals.get(position);
                    mListener.onListFragmentInteraction(hospital);

                    GAManager.sendEvent(new HospitalDoctorClickHospitalListEvent(hospital.name));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHospitals.size();
    }

    public void addHospitals(ArrayList<Hospital> hospitals) {
        mHospitals.addAll(hospitals);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mName;
        public TextView mAddress;
        public ImageView mImageView;
        public TextView mDistance;
        public TextView mCommentNum;
        public TextView mRecommendNum;
        public TextView mScore;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.grade_image);
            mName = (TextView) view.findViewById(R.id.hospial_name);
            mAddress = (TextView) view.findViewById(R.id.address);
            mDistance = (TextView) view.findViewById(R.id.distance);
            mCommentNum = (TextView) view.findViewById(R.id.comment_num);
            mRecommendNum = (TextView) view.findViewById(R.id.recommend_num);
            mScore = (TextView) view.findViewById(R.id.score);
        }
    }
}
