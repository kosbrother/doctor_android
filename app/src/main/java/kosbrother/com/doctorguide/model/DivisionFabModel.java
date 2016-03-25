package kosbrother.com.doctorguide.model;

public class DivisionFabModel {

    private static final int DIVISION_DOCTOR_PAGE = 0;
    private static final int DIVISION_SCORE_PAGE = 1;
    private static final int DIVISION_COMMENT_PAGE = 2;

    public boolean showAddDoctor(int changedPosition) {
        return changedPosition == DIVISION_DOCTOR_PAGE;
    }

    public boolean showAddComment(int changedPosition) {
        return changedPosition == DIVISION_SCORE_PAGE ||
                changedPosition == DIVISION_COMMENT_PAGE;
    }
}
