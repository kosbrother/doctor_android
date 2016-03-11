package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.BlankViewHolder;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickDoctorListEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickCollectEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospitaldoctor.HospitalDoctorClickDoctorListEvent;

import static kosbrother.com.doctorguide.Util.SphericalUtil.computeDistanceBetween;


public class MyDoctorRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Doctor> mDoctors;
    private final DoctorFragment.OnListFragmentInteractionListener mListener;
    private int mFragmentViewType;
    public static final int DISTANCE_TYPE = 0;
    public static final int HEART_TYPE = 1;
    private LatLng mLocation;

    public enum ITEM_TYPE {
        ITEM,
        ITEM_BLANK
    }

    public MyDoctorRecyclerViewAdapter(ArrayList<Doctor> items, DoctorFragment.OnListFragmentInteractionListener listener, int fragmentViewType, LatLng location) {
        mDoctors = items;
        mListener = listener;
        mFragmentViewType = fragmentViewType;
        mLocation = location;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_doctor, parent, false);
            if (mFragmentViewType == DISTANCE_TYPE) {
                view.findViewById(R.id.heart).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.hospial_name).setVisibility(View.GONE);
                view.findViewById(R.id.distance).setVisibility(View.GONE);
            }
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
            if (mFragmentViewType == DISTANCE_TYPE) {
                double distance = computeDistanceBetween(mLocation,
                        new LatLng(mDoctors.get(position).latitude, mDoctors.get(position).longitude));
                holder.mDistance.setText(Util.formatNumber(distance));
                holder.mhospialName.setText(mDoctors.get(position).hospital);
            } else {
                if (mDoctors.get(position).isCollected) {
                    holder.heart.setBackgroundResource(R.drawable.heart_read_to_white_button);
                } else {
                    holder.heart.setBackgroundResource(R.drawable.heart_red_line_to_red_button);
                }
                holder.heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            Doctor doctor = mDoctors.get(position);
                            GAManager.sendEvent(new DoctorClickCollectEvent(doctor.name));

                            mListener.onListFragmentInteraction(v, doctor);
                        }
                    }
                });
            }
            holder.mName.setText(mDoctors.get(position).name);
            holder.mCommentNum.setText(mDoctors.get(position).comment_num + "");
            holder.mRecommendNum.setText(mDoctors.get(position).recommend_num + "");
            holder.mScore.setText(String.format("%.1f", mDoctors.get(position).avg));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // TODO: 2016/3/3 move logic to activity
                        Doctor doctor = mDoctors.get(position);

                        if (mFragmentViewType == HEART_TYPE) {
                            GAManager.sendEvent(new DivisionClickDoctorListEvent(doctor.name));
                        } else {
                            GAManager.sendEvent(new HospitalDoctorClickDoctorListEvent(doctor.name));
                        }
                        mListener.onListFragmentInteraction(v, doctor);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDoctors.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mDoctors.size() ? ITEM_TYPE.ITEM_BLANK.ordinal() : ITEM_TYPE.ITEM.ordinal();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mName;
        public TextView mhospialName;
        public Button heart;
        public TextView mDistance;
        public TextView mCommentNum;
        public TextView mRecommendNum;
        public TextView mScore;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.doctor_name);
            mhospialName = (TextView) view.findViewById(R.id.hospial_name);
            heart = (Button) view.findViewById(R.id.heart);
            mDistance = (TextView) view.findViewById(R.id.distance);
            mCommentNum = (TextView) view.findViewById(R.id.comment_num);
            mRecommendNum = (TextView) view.findViewById(R.id.recommend_num);
            mScore = (TextView) view.findViewById(R.id.score);
        }

    }
}
