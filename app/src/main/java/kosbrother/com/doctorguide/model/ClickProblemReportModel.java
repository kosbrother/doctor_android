package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.Util.ReportType;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class ClickProblemReportModel {
    private int divisionId = 0;
    private String divisionName = "";
    private int doctorId = 0;
    private String doctorName = "";
    private int hospitalId = 0;
    private String hospitalName = "";
    private String reportType = "";

    public ClickProblemReportModel(HospitalActivityViewModel viewModel) {
        hospitalId = viewModel.getHospitalId();
        hospitalName = viewModel.getHospitalName();
        reportType = ReportType.HOSPITAL_PAGE;
    }

    public ClickProblemReportModel(DivisionActivityViewModel viewModel) {
        hospitalName = viewModel.getHospitalName();
        divisionName = viewModel.getDivisionName();
        divisionId = viewModel.getDivisionId();
        hospitalId = viewModel.getHospitalId();
        reportType = ReportType.DIVISION_PAGE;
    }

    public ClickProblemReportModel(DoctorActivityViewModel viewModel) {
        hospitalName = viewModel.getHospitalName();
        doctorName = viewModel.getDoctorName();
        divisionId = viewModel.getDoctorId();
        hospitalId = viewModel.getHospitalId();
        reportType = ReportType.DOCTOR_PAGE;
    }

    public ProblemReportViewModel getViewModel() {
        ProblemReportViewModel viewModel = new ProblemReportViewModel();
        viewModel.setDivisionId(divisionId);
        viewModel.setDivisionName(divisionName);
        viewModel.setDoctorId(doctorId);
        viewModel.setDoctorName(doctorName);
        viewModel.setHospitalId(hospitalId);
        viewModel.setHospitalName(hospitalName);
        viewModel.setReportType(reportType);
        return viewModel;
    }
}
