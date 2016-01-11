package kosbrother.com.doctorguide.adapters;

import com.google.android.gms.maps.model.LatLng;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.DoctorFragment;

import static kosbrother.com.doctorguide.Util.SphericalUtil.computeDistanceBetween;


public class MyDoctorRecyclerViewAdapter extends RecyclerView.Adapter<MyDoctorRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Doctor> mDoctors;
    private final DoctorFragment.OnListFragmentInteractionListener mListener;
    private int mFragmentViewType;
    public static final int DISTANCETYPE = 0;
    public static final int HEARTTYPE = 1;
    private LatLng mLocation;


    public MyDoctorRecyclerViewAdapter(ArrayList<Doctor> items, DoctorFragment.OnListFragmentInteractionListener listener, int fragmentViewType, LatLng location) {
        mDoctors = items;
        mListener = listener;
        mFragmentViewType = fragmentViewType;
        mLocation = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_doctor, parent, false);
        if(mFragmentViewType == DISTANCETYPE) {
            view.findViewById(R.id.heart).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.hospial_name).setVisibility(View.GONE);
            view.findViewById(R.id.distance).setVisibility(View.GONE);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mDoctors.get(position).name);
        holder.mhospialName.setText(mDoctors.get(position).hospital);
        double distance = computeDistanceBetween(mLocation, new LatLng(mDoctors.get(position).latitude, mDoctors.get(position).longitude));
        holder.mDistance.setText(Util.formatNumber(distance));

        if(mDoctors.get(position).isCollected) {
            holder.heart.setBackgroundResource(R.drawable.heart_read_to_white_button);
        }else{
            holder.heart.setBackgroundResource(R.drawable.heart_red_line_to_red_button);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(v, mDoctors.get(position));
                }
            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(v,mDoctors.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mName;
        public  TextView mhospialName;
        public Button heart;
        public TextView mDistance;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.doctor_name);
            mhospialName = (TextView) view.findViewById(R.id.hospial_name);
            heart = (Button)view.findViewById(R.id.heart);
            mDistance = (TextView)view.findViewById(R.id.distance);
        }

    }
}
