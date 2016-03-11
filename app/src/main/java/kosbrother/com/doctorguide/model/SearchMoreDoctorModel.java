package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.task.SearchDoctorsTask;

public class SearchMoreDoctorModel {
    private final String query;

    public SearchMoreDoctorModel(String query) {
        this.query = query;
    }

    public void requestSearchDoctors(SearchDoctorsTask.SearchDoctorsListener listener) {
        new SearchDoctorsTask(30, listener).execute(query);
    }
}
