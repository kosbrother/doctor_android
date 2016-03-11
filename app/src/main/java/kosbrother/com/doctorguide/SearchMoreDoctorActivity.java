package kosbrother.com.doctorguide;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.adapters.DoctorSearchListAdapter;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.SearchMoreDoctorModel;
import kosbrother.com.doctorguide.presenter.SearchMoreDoctorPresenter;
import kosbrother.com.doctorguide.view.SearchMoreDoctorView;

public class SearchMoreDoctorActivity extends SearchMoreActivity implements SearchMoreDoctorView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchMoreDoctorModel model = new SearchMoreDoctorModel(query);
        SearchMoreDoctorPresenter presenter = new SearchMoreDoctorPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setDoctorsListView(ArrayList<Doctor> doctors) {
        DoctorSearchListAdapter adapter = new DoctorSearchListAdapter(
                this, doctors, SearchableActivity.LIST_TYPE.NOT_MORE);
        ((ListView) findViewById(R.id.ListView)).setAdapter(adapter);
    }
}
