package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.DoctorFragment;


public class MyDoctorRecyclerViewAdapter extends RecyclerView.Adapter<MyDoctorRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Doctor> mDoctors;
    private final DoctorFragment.OnListFragmentInteractionListener mListener;
    private int mFragmentViewType;
    public static final int DISTANCETYPE = 0;
    public static final int HEARTTYPE = 1;


    public MyDoctorRecyclerViewAdapter(ArrayList<Doctor> items, DoctorFragment.OnListFragmentInteractionListener listener, int fragmentViewType) {
        mDoctors = items;
        mListener = listener;
        mFragmentViewType = fragmentViewType;
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.doctor_name);
            mhospialName = (TextView) view.findViewById(R.id.hospial_name);
            heart = (Button)view.findViewById(R.id.heart);
        }

    }
}
