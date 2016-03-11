package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.task.SearchHospitalsTask;

public class SearchMoreHospitalModel {

    private final String query;

    public SearchMoreHospitalModel(String query) {
        this.query = query;
    }

    public void requestSearchHospital(SearchHospitalsTask.SearchHospitalsListener listener) {
        new SearchHospitalsTask(30, listener).execute(query);
    }
}
