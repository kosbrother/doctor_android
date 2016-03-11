package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.SearchMoreDoctorModel;
import kosbrother.com.doctorguide.task.SearchDoctorsTask;
import kosbrother.com.doctorguide.view.SearchMoreDoctorView;

public class SearchMoreDoctorPresenter extends SearchMorePresenter implements
        SearchDoctorsTask.SearchDoctorsListener {

    private final SearchMoreDoctorView view;
    private final SearchMoreDoctorModel model;

    public SearchMoreDoctorPresenter(SearchMoreDoctorView view, SearchMoreDoctorModel model) {
        super(view);
        this.view = view;
        this.model = model;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view.showProgressDialog();
        model.requestSearchDoctors(this);
    }

    @Override
    public void onSearchDoctorsResult(ArrayList<Doctor> doctors) {
        view.hideProgressDialog();
        view.setDoctorsListView(doctors);
    }
}
