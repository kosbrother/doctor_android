package kosbrother.com.doctorguide.model;

import java.util.HashMap;

import kosbrother.com.doctorguide.viewmodel.AddDoctorViewModel;
import kosbrother.com.doctorguide.task.SubmitAddDoctorTask;

public class AddDoctorModelImpl implements AddDoctorModel {
    private final AddDoctorViewModel viewModel;

    public AddDoctorModelImpl(AddDoctorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public String getDivisionName() {
        return viewModel.getDivisionName();
    }

    @Override
    public String getHospitalName() {
        return viewModel.getHospitalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestSubmitAddDoctor(HashMap<String, String> submitData, SubmitAddDoctorTask.SubmitAddDoctorListener listener) {
        putDivisionAndHospitalId(submitData);
        new SubmitAddDoctorTask(listener).execute(submitData);
    }

    private void putDivisionAndHospitalId(HashMap<String, String> submitData) {
        int divisionId = viewModel.getDivisionId();
        if (divisionId != 0) {
            submitData.put("division_id", divisionId + "");
        }
        int hospitalId = viewModel.getHospitalId();
        if (hospitalId != 0) {
            submitData.put("hospital_id", hospitalId + "");
        }
    }
}
