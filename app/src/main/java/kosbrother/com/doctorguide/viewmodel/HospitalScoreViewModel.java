package kosbrother.com.doctorguide.viewmodel;

import kosbrother.com.doctorguide.entity.Hospital;

public class HospitalScoreViewModel {
    private final Hospital hospital;

    public HospitalScoreViewModel(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getCommentNum() {
        return hospital.comment_num + "";
    }

    public String getRecommendNum() {
        return hospital.recommend_num + "";
    }

    public String getAvgScore() {
        return String.format("%.1f", hospital.avg);
    }
}
