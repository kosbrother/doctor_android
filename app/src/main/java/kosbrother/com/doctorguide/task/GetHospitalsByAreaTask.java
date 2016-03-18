package kosbrother.com.doctorguide.task;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Hospital;

public class GetHospitalsByAreaTask extends AsyncTask<GetHospitalsByAreaTask.GetHospitalsByAreaInput, Void, ArrayList<Hospital>> {
    private GetHospitalsByAreaListener listener;

    public GetHospitalsByAreaTask(GetHospitalsByAreaListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Hospital> doInBackground(GetHospitalsByAreaInput... getHospitalsByAreaInputs) {
        return DoctorGuideApi.getHospitalsByArea(getHospitalsByAreaInputs[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Hospital> hospitals) {
        super.onPostExecute(hospitals);
        if (hospitals != null) {
            listener.onGetHospitalsSuccess(hospitals);
        }
    }

    public interface GetHospitalsByAreaListener {
        void onGetHospitalsSuccess(ArrayList<Hospital> hospitals);
    }

    public class GetHospitalsByAreaInput {
        private int areaId;
        private int page;
        private double latitude;
        private double longitude;
        private String orderString;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getOrderString() {
            return orderString;
        }

        public void setOrderString(String orderString) {
            this.orderString = orderString;
        }
    }
}
