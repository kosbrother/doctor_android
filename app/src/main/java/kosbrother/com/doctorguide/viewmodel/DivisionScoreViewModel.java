package kosbrother.com.doctorguide.viewmodel;

import kosbrother.com.doctorguide.entity.Division;

public class DivisionScoreViewModel {
    private final Division division;

    public DivisionScoreViewModel(Division division) {
        this.division = division;
    }

    public String getCommentNumText() {
        return division.comment_num + "";
    }

    public String getRecommendNumText() {
        return division.recommend_num + "";
    }

    public String getScoreAvgText() {
        return String.format("%.1f", division.avg);
    }
}
