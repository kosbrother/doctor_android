package kosbrother.com.doctorguide.model;

import android.support.annotation.NonNull;

import java.util.HashMap;

import kosbrother.com.doctorguide.task.PostProblemTask;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class ProblemReportModel {
    private final ProblemReportViewModel viewModel;

    public ProblemReportModel(ProblemReportViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public String getReportTypeText() {
        return viewModel.getReportType();
    }

    public String getReportPageText() {
        StringBuilder stringBuilder = new StringBuilder(viewModel.getHospitalName());
        String doctorName = viewModel.getDoctorName();
        String divisionName = viewModel.getDivisionName();
        if (doctorName != null) {
            stringBuilder.append(" | ").append(doctorName).append(" 醫師");
        } else if (divisionName != null)
            stringBuilder.append(" | ").append(divisionName);
        return stringBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public void requestPostProblem(String content, PostProblemTask.PostProblemListener listener) {
        new PostProblemTask(listener).execute(getSubmitParams(content));
    }

    @NonNull
    private HashMap<String, String> getSubmitParams(String content) {
        HashMap<String, String> submitParams = new HashMap<>();
        submitParams.put("content", content);

        int doctorId = viewModel.getDoctorId();
        int hospitalId = viewModel.getHospitalId();
        int divisionId = viewModel.getDivisionId();

        if (doctorId != 0)
            submitParams.put("doctor_id", doctorId + "");
        if (hospitalId != 0)
            submitParams.put("hospital_id", hospitalId + "");
        if (divisionId != 0)
            submitParams.put("division_id", divisionId + "");
        return submitParams;
    }
}
