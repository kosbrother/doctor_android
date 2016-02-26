package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.AddDoctorActivity;
import kosbrother.com.doctorguide.DoctorActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.SearchableActivity;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickFABEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

/**
 * Created by steven on 1/2/16.
 */
public class DoctorSearchListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Doctor> mDoctors;
    private Context mContext;

    public DoctorSearchListAdapter(Context context, ArrayList<Doctor> doctors) {
        mContext = context;
        mDoctors = doctors;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDoctors.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < mDoctors.size()) {
            return mDoctors.get(position);
        }
        else {
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
            if (position < mDoctors.size()) {
                vi = inflater.inflate(R.layout.item_search_doctor, parent, false);
                TextView doctorNameTextView = (TextView) vi.findViewById(R.id.doctor_name);
                TextView hospialNameTextView = (TextView) vi.findViewById(R.id.hospial_name);
                TextView distanceTextView = (TextView) vi.findViewById(R.id.distance);

                doctorNameTextView.setText(mDoctors.get(position).name);
                hospialNameTextView.setText(mDoctors.get(position).search_hospital);
                distanceTextView.setText("");

                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Doctor doctor = mDoctors.get(position);
                        Intent intent = new Intent(mContext, DoctorActivity.class);
                        intent.putExtra("HOSPITAL_ID", doctor.search_hospital_id);
                        intent.putExtra("DOCTOR_ID", doctor.id);
                        intent.putExtra("DOCTOR_NAME", doctor.name);
                        intent.putExtra("HOSPITAL_NAME", doctor.search_hospital);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                vi = inflater.inflate(R.layout.item_search_doctor_add, parent, false);
                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GAManager.sendEvent(new HospitalClickFABEvent(GALabel.ADD_DOCTOR));

                        Intent intent = new Intent(mContext, AddDoctorActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
        return vi;
    }
}
