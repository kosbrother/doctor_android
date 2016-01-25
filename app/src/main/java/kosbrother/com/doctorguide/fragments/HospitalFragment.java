package kosbrother.com.doctorguide.fragments;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyHospitalRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.custom.LoadMoreRecyclerView;
import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.entity.Hospital;

public class HospitalFragment extends Fragment implements Spinner.OnItemSelectedListener{

    private static final String ARG_CATEGORY_ID = "CATEGORY_ID";
    private static LatLng location;
    private int mCategoryId = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Hospital> hospitals = new ArrayList<>();
    private LoadMoreRecyclerView recyclerView;
    private MyHospitalRecyclerViewAdapter hospitalAdapter;
    private int page = 1;
    private LinearLayout loadmoreLayout;
    private boolean isLoadCompleted = false;
    private String[] values;
    private String orderString;

    public HospitalFragment() {
    }

    public static HospitalFragment newInstance(int category, LatLng latLng) {
        HospitalFragment fragment = new HospitalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, category);
        location = latLng;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_list, container, false);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        loadmoreLayout = (LinearLayout) view.findViewById(R.id.load_more);
        Context context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(!isLoadCompleted){
                    loadmoreLayout.setVisibility(View.VISIBLE);
                    new GetHospitalsTask().execute();
                }
            }
        });
        setAreaSpinner(view);
        setSortSpinner(view);

        return view;
    }

    private void setSortSpinner(View view) {

        Spinner sort = (Spinner) view.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> sort_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, R.layout.spinner_area_item);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(sort_adapter);
        sort.setOnItemSelectedListener(this);
        values = getResources().getStringArray(R.array.sort_values);
    }

    private void setAreaSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.area);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_area_item, Area.getAreaStrings().toArray(new String[Area.getAreas().size()]));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
        int closest = Util.getClosestAreaPosition(location);
        spinner.setSelection(closest);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    int areaId = 0;
    String sortSrting;
    int checktime = 0;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.area){
            areaId = Area.getAreas().get(position).id;
        }else if(spinner.getId() == R.id.sort){
            sortSrting = (String)parent.getItemAtPosition(position);
            orderString = values[position];
        }

        if(areaId != 0 && sortSrting!=null)
            checktime += 1;
        if(checktime >= 1) {
            page = 1;isLoadCompleted = false;
            new GetHospitalsTask().execute();
        }
    }

    private class GetHospitalsTask extends AsyncTask {

        private ArrayList<Hospital> getHospitals;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(page == 1)
                Util.showProgressDialog(getContext());
        }
        @Override
        protected Object doInBackground(Object... params) {
            getHospitals = DoctorGuideApi.getHospitalsByAreaAndCategory(areaId, mCategoryId, page,location.latitude,location.longitude,orderString);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if(page==1)
                Util.hideProgressDialog();
            loadmoreLayout.setVisibility(View.GONE);
            recyclerView.setLoaded();

            if(page == 1) {
                page += 1;
                hospitals = getHospitals;
                hospitalAdapter = new MyHospitalRecyclerViewAdapter(hospitals, mListener,location);
                recyclerView.setAdapter(hospitalAdapter);
                hospitalAdapter.notifyDataSetChanged();
            }else{
                if(getHospitals.size() > 0) {
                    page += 1;
                    hospitals.addAll(getHospitals);
                    hospitalAdapter.notifyDataSetChanged();
                }else{
                    isLoadCompleted = true;
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Hospital item);
    }
}
