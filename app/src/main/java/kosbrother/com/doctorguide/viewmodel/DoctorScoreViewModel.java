package kosbrother.com.doctorguide.viewmodel;

import kosbrother.com.doctorguide.entity.Doctor;

public class DoctorScoreViewModel {
    private final Doctor doctor;

    public DoctorScoreViewModel(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getCommentNum() {
        return doctor.comment_num + "";
    }

    public String getReCommentNum() {
        return doctor.recommend_num + "";
    }

    public String getAvgScore() {
        return String.format("%.1f", doctor.avg);
    }
}
