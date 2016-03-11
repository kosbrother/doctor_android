package kosbrother.com.doctorguide;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.adapters.HospitalSearchAdapter;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.SearchMoreHospitalModel;
import kosbrother.com.doctorguide.presenter.SearchMoreHospitalPresenter;
import kosbrother.com.doctorguide.view.SearchMoreHospitalView;

public class SearchMoreHospitalActivity extends SearchMoreActivity implements SearchMoreHospitalView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchMoreHospitalModel model = new SearchMoreHospitalModel(query);
        SearchMoreHospitalPresenter presenter = new SearchMoreHospitalPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setHospitalsListView(ArrayList<Hospital> hospitals) {
        ListView listView = (ListView) findViewById(R.id.ListView);
        HospitalSearchAdapter adapter = new HospitalSearchAdapter(
                this, hospitals, SearchableActivity.LIST_TYPE.NOT_MORE);
        listView.setAdapter(adapter);
    }
}
