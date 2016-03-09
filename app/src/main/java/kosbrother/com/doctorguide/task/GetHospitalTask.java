package kosbrother.com.doctorguide.task;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Hospital;

public class GetHospitalTask extends AsyncTask<Integer, Void, Hospital> {

    private GetHospitalListener listener;

    public GetHospitalTask(GetHospitalListener listener) {
        this.listener = listener;
    }

    @Override
    protected Hospital doInBackground(Integer... integers) {
        return DoctorGuideApi.getHospitalInfo(integers[0]);
    }

    @Override
    protected void onPostExecute(Hospital hospital) {
        super.onPostExecute(hospital);
        if (hospital != null) {
            listener.onGetHospitalSuccess(hospital);
        }
    }

    public interface GetHospitalListener {
        void onGetHospitalSuccess(Hospital hospital);
    }
}
