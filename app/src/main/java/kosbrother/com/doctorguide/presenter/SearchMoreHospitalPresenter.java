package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.SearchMoreHospitalModel;
import kosbrother.com.doctorguide.task.SearchHospitalsTask;
import kosbrother.com.doctorguide.view.SearchMoreHospitalView;

public class SearchMoreHospitalPresenter extends SearchMorePresenter implements
        SearchHospitalsTask.SearchHospitalsListener {

    private final SearchMoreHospitalView view;
    private final SearchMoreHospitalModel model;

    public SearchMoreHospitalPresenter(SearchMoreHospitalView view, SearchMoreHospitalModel model) {
        super(view);
        this.view = view;
        this.model = model;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view.showProgressDialog();
        model.requestSearchHospital(this);
    }

    @Override
    public void onSearchHospitalsResult(ArrayList<Hospital> hospitals) {
        view.hideProgressDialog();
        view.setHospitalsListView(hospitals);
    }
}
