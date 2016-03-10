package kosbrother.com.doctorguide.viewmodel;

public class ProblemReportViewModel {
    private String divisionName = "";
    private String hospitalName = "";
    private String doctorName = "";
    private String reportType = "";
    private int doctorId = 0;
    private int hospitalId = 0;
    private int divisionId = 0;

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getDivisionId() {
        return divisionId;
    }
}
