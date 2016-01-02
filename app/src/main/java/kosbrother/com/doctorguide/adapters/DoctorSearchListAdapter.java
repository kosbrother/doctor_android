package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import kosbrother.com.doctorguide.R;

/**
 * Created by steven on 1/2/16.
 */
public class DoctorSearchListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] mDoctors;

    public DoctorSearchListAdapter(Context context, String[] doctors) {
        mDoctors = doctors;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDoctors.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(position < mDoctors.length){
            vi = inflater.inflate(R.layout.item_search_doctor, null);
            vi.setClickable(true);
            vi.setFocusable(true);
        }else{
            vi = inflater.inflate(R.layout.item_search_doctor_add, null);
            vi.setClickable(true);
            vi.setFocusable(true);
        }


        return vi;
    }
}
