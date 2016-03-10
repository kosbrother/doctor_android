package kosbrother.com.doctorguide.task;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;

public class GetDivisionsByHospitalAndCategoryTask extends AsyncTask<Integer, Void, ArrayList<Division>> {

    private GetDivisionsListener listener;

    public GetDivisionsByHospitalAndCategoryTask(GetDivisionsListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Division> doInBackground(Integer... integers) {
        return DoctorGuideApi.getDivisionByHospitalAndCategory(integers[0], integers[1]);
    }

    @Override
    protected void onPostExecute(ArrayList<Division> divisions) {
        super.onPostExecute(divisions);
        if (divisions != null && divisions.size() > 0) {
            listener.onGetDivisionsSuccess(divisions);
        }
    }

    public interface GetDivisionsListener {
        void onGetDivisionsSuccess(ArrayList<Division> divisions);
    }
}
