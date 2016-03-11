package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.SearchableModel;
import kosbrother.com.doctorguide.view.SearchableView;

public class SearchablePresenter implements SearchableModel.GetSearchResultListener {
    private final SearchableView view;
    private final SearchableModel model;

    public SearchablePresenter(SearchableView view, SearchableModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
    }

    public void onHandleSearchIntent(String query) {
        model.setQueryString(query);

        view.setActionBarTitle(model.getActionBarTitle());
        view.showProgressDialog();
        model.requestGetSearchResult(this);
    }

    @Override
    public void onGetSearchResultDone() {
        view.hideProgressDialog();
        view.setDoctorListView(model.getDoctors());
        view.setHospitalListView(model.getHospitals());
    }

    public void onSearchMoreHospitalClick() {
        view.startSearchMoreActivity("HOSPITAL", model.getQueryString());
    }

    public void onSearchMoreDoctorClick() {
        view.startSearchMoreActivity("DOCTOR", model.getQueryString());
    }
}
