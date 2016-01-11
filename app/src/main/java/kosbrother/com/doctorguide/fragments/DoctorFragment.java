package kosbrother.com.doctorguide.fragments;

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

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.custom.LoadMoreRecyclerView;
import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;


public class DoctorFragment extends Fragment implements Spinner.OnItemSelectedListener{

    private static final String ARG_TYPE = "ARG_TYPE";
    private static final String ARG_CATEGORY_ID = "CATEGORY_ID";
    private static final String ARG_HOSPITAL_ID = "HOSPITAL_ID";
    private static final String ARG_DIVISION_ID = "DIVISION_ID";
    private int fragmentType;
    private int mCategoryId = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Doctor> doctors = new ArrayList();
    private LoadMoreRecyclerView recyclerView;
    private int mHospitalId;
    private int mDivisionId;
    private int page = 1;
    private LinearLayout loadmoreLayout;
    private boolean isLoadCompleted = false;
    private MyDoctorRecyclerViewAdapter adatper;


    public DoctorFragment() {
    }

    public static DoctorFragment newInstance(int fragmentType,int categoryId) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, fragmentType);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public static DoctorFragment newInstance(int fragmentType,int hospitalId,int divisionId) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, fragmentType);
        args.putInt(ARG_HOSPITAL_ID, hospitalId);
        args.putInt(ARG_DIVISION_ID, divisionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fragmentType = getArguments().getInt(ARG_TYPE);
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
            mHospitalId = getArguments().getInt(ARG_HOSPITAL_ID);
            mDivisionId = getArguments().getInt(ARG_DIVISION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        loadmoreLayout = (LinearLayout) view.findViewById(R.id.load_more);
        Context context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isLoadCompleted) {
                    loadmoreLayout.setVisibility(View.VISIBLE);
                    new GetDoctorsTask().execute();
                }
            }
        });

        if(fragmentType == MyDoctorRecyclerViewAdapter.HEARTTYPE){
            view.findViewById(R.id.selector).setVisibility(View.GONE);
            setHeartTypeRecyclerAdapter();
        }else {
            setAreaSpinner(view);
            setSortSpinner(view);
        }
        return view;
    }

    private void setHeartTypeRecyclerAdapter() {
        new GetDoctorsTask().execute();
    }

    private void setSortSpinner(View view) {

        Spinner sort = (Spinner) view.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> sort_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(sort_adapter);
        sort.setOnItemSelectedListener(this);
    }

    private void setAreaSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.area);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Area.getAreaStrings().toArray(new String[Area.getAreas().size()]));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
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
        }

        if(areaId != 0 && sortSrting!=null)
            checktime += 1;
        if(checktime >= 1) {
            page = 1;isLoadCompleted = false;
            new GetDoctorsTask().execute();
        }
    }

    private class GetDoctorsTask extends AsyncTask {

        private ArrayList<Doctor> getDoctors;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(page==1)
                Util.showProgressDialog(getContext());
        }
        @Override
        protected Object doInBackground(Object... params) {
            if(fragmentType == MyDoctorRecyclerViewAdapter.HEARTTYPE ) {
                doctors = DoctorGuideApi.getDoctorsByHospitalAndDivision(mHospitalId, mDivisionId);
                Realm realm = Realm.getInstance(getContext());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmQuery<RealmDoctor> query = realm.where(RealmDoctor.class);
                        RealmResults<RealmDoctor> results = query.findAll();
                        for (Doctor doc : doctors) {
                            for (RealmDoctor realmDoctor : results) {
                                if (doc.id == realmDoctor.getId()) {
                                    doc.isCollected = true;
                                    break;
                                }
                            }
                        }
                    }
                });
            }else
                getDoctors = DoctorGuideApi.getDoctorsByAreaAndCategory(areaId, mCategoryId,page);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if(page==1)
                Util.hideProgressDialog();
            if(fragmentType == MyDoctorRecyclerViewAdapter.HEARTTYPE ) {
                adatper = new MyDoctorRecyclerViewAdapter(doctors, mListener, fragmentType);
                recyclerView.setAdapter(adatper);
                adatper.notifyDataSetChanged();
                isLoadCompleted = true;
            }else{
                loadmoreLayout.setVisibility(View.GONE);
                recyclerView.setLoaded();

                if(page == 1) {
                    page += 1;
                    doctors = getDoctors;
                    adatper = new MyDoctorRecyclerViewAdapter(doctors, mListener,fragmentType);
                    recyclerView.setAdapter(adatper);
                    adatper.notifyDataSetChanged();
                }else{
                    if(getDoctors.size() > 0) {
                        page += 1;
                        doctors.addAll(getDoctors);
                        adatper.notifyDataSetChanged();
                    }else{
                        isLoadCompleted = true;
                    }
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(View v, Doctor item);
    }
}
