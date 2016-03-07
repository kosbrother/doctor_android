package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.HospitalActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.SearchableActivity;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.entity.Hospital;

public class HospitalSearchAdapter extends BaseAdapter {

    private final SearchableActivity.LIST_TYPE mType;
    private final View.OnClickListener mMoreClickListener;
    private LayoutInflater inflater;
    private ArrayList<Hospital> mHospitals;
    private Context mContext;

    public HospitalSearchAdapter(Context context, ArrayList<Hospital> hospitals, SearchableActivity.LIST_TYPE type) {
        mContext = context;
        mHospitals = hospitals;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mType = type;
        mMoreClickListener = null;
    }

    public HospitalSearchAdapter(Context context, ArrayList<Hospital> hospitals, SearchableActivity.LIST_TYPE type, View.OnClickListener moreClickListener) {
        mContext = context;
        mHospitals = hospitals;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mType = type;
        mMoreClickListener = moreClickListener;
    }

    @Override
    public int getCount() {
        if (mType == SearchableActivity.LIST_TYPE.MORE)
            return mHospitals.size() + 1;
        else
            return mHospitals.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < mHospitals.size()) {
            return mHospitals.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            if (position < mHospitals.size()) {
                vi = inflater.inflate(R.layout.item_search_hospital, parent, false);
                TextView hospitalNameTextView = (TextView) vi.findViewById(R.id.hospial_name);
                TextView addressTextView = (TextView) vi.findViewById(R.id.address);
                TextView distanceTextView = (TextView) vi.findViewById(R.id.distance);

                hospitalNameTextView.setText(mHospitals.get(position).name);
                addressTextView.setText(mHospitals.get(position).address);
                distanceTextView.setText(mHospitals.get(position).address.substring(0, 3));

                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Hospital hospital = mHospitals.get(position);
                        Intent intent = new Intent(mContext, HospitalActivity.class);
                        intent.putExtra(ExtraKey.HOSPITAL_ID, hospital.id);
                        intent.putExtra(ExtraKey.HOSPITAL_GRADE, hospital.grade);
                        intent.putExtra(ExtraKey.HOSPITAL_NAME, hospital.name);
                        mContext.startActivity(intent);
                    }
                });
            } else if (mType == SearchableActivity.LIST_TYPE.MORE) {
                if (position == mHospitals.size())
                    vi = inflateMore(vi, parent);
            }
        }
        return vi;
    }

    private View inflateMore(View vi, ViewGroup parent) {
        vi = inflater.inflate(R.layout.item_search_more, parent, false);
        vi.setOnClickListener(mMoreClickListener);
        return vi;
    }
}
