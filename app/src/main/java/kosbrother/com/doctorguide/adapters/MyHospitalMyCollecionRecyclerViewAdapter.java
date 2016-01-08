package kosbrother.com.doctorguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmResults;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.fragments.HospitalMyCollecionFragment.OnListFragmentInteractionListener;
import kosbrother.com.doctorguide.fragments.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHospitalMyCollecionRecyclerViewAdapter extends RecyclerView.Adapter<MyHospitalMyCollecionRecyclerViewAdapter.ViewHolder> {

    private final RealmResults<RealmHospital> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHospitalMyCollecionRecyclerViewAdapter(RealmResults<RealmHospital> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hospitalmycollecion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mValues.get(position).getName());
        holder.mAddress.setText(mValues.get(position).getAddress());

        switch (mValues.get(position).getGrade()){
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
                if (null != mListener) {
                    mListener.onListFragmentInteraction(v,mValues.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mName;
        public TextView mAddress;
        public ImageView mImageView;
        public Button heart;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView)view.findViewById(R.id.grade_image);
            mName = (TextView)view.findViewById(R.id.hospial_name);
            mAddress = (TextView)view.findViewById(R.id.address);
            heart = (Button)view.findViewById(R.id.heart);
        }

    }
}
