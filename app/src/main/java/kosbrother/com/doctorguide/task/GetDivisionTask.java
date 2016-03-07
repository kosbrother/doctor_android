package kosbrother.com.doctorguide.task;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;

public class GetDivisionTask extends AsyncTask<Void, Void, Division> {

    private int divisionId;
    private int hospitalId;
    private GetDivisionListener listener;

    public GetDivisionTask(int divisionId, int hospitalId, GetDivisionListener listener) {
        this.divisionId = divisionId;
        this.hospitalId = hospitalId;
        this.listener = listener;
    }

    @Override
    protected Division doInBackground(Void... voids) {
        return DoctorGuideApi.getDivisionScore(divisionId, hospitalId);
    }

    @Override
    protected void onPostExecute(Division division) {
        super.onPostExecute(division);
        if (division != null) {
            listener.onGetDivisionSuccess(division);
        }
    }

    public interface GetDivisionListener {
        void onGetDivisionSuccess(Division division);
    }
}
