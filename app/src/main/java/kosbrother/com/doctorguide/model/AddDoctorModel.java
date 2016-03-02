package kosbrother.com.doctorguide.model;

import java.util.HashMap;

import kosbrother.com.doctorguide.task.SubmitAddDoctorTask;

public interface AddDoctorModel {
    String getDivisionName();

    String getHospitalName();

    void requestSubmitAddDoctor(HashMap<String, String> submitData, SubmitAddDoctorTask.SubmitAddDoctorListener listener);
}
