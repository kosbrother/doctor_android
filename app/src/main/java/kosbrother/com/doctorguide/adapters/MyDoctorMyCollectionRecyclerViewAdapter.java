package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.RealmResults;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.fragments.DoctorMyCollectionFragment.OnListFragmentInteractionListener;

public class MyDoctorMyCollectionRecyclerViewAdapter extends RecyclerView.Adapter<MyDoctorMyCollectionRecyclerViewAdapter.ViewHolder> {

    private final RealmResults<RealmDoctor> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDoctorMyCollectionRecyclerViewAdapter(RealmResults<RealmDoctor> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_doctormycollection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mName.setText(mValues.get(position).getName());
        holder.mHospital.setText(mValues.get(position).getHospital());

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(v,mValues.get(position));
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                    mListener.onListFragmentInteraction(v, mValues.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mName;
        public TextView mHospital;
        public Button heart;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView)view.findViewById(R.id.doctor_name);
            mHospital = (TextView)view.findViewById(R.id.hospial_name);
            heart = (Button)view.findViewById(R.id.heart);
        }
    }
}
