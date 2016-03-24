package kosbrother.com.doctorguide.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DivisionFabModelTest {

    public static final int DIVISION_DOCTOR_PAGE = 0;
    public static final int DIVISION_SCORE_PAGE = 1;
    public static final int DIVISION_COMMENT_PAGE = 2;

    private DivisionFabModel model;

    @Before
    public void setUp() throws Exception {
        model = new DivisionFabModel();
    }

    @Test
    public void testIsSwitchToDoctorPage() throws Exception {
        model.setLastPagePosition(DIVISION_SCORE_PAGE);
        int changedPosition = DIVISION_DOCTOR_PAGE;

        boolean showAddDoctor = model.showAddDoctor(changedPosition);

        Assert.assertTrue(showAddDoctor);
    }

    @Test
    public void testIsSwitchToCommentPage_switchFromDoctorPage() throws Exception {
        model.setLastPagePosition(DIVISION_DOCTOR_PAGE);
        int changedPosition = DIVISION_SCORE_PAGE;

        boolean showAddComment = model.showAddComment(changedPosition);

        Assert.assertTrue(showAddComment);
    }

    @Test
    public void testIsSwitchToCommentPage_switchFromCommentPage() throws Exception {
        model.setLastPagePosition(DIVISION_COMMENT_PAGE);
        int changedPosition = DIVISION_SCORE_PAGE;

        boolean showAddComment = model.showAddComment(changedPosition);

        Assert.assertFalse(showAddComment);
    }
}