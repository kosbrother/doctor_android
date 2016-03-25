package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.DivisionFabModel;
import kosbrother.com.doctorguide.view.DivisionFabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DivisionFabPresenterTest {

    private DivisionFabView view;
    private DivisionFabModel model;
    private DivisionFabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(DivisionFabView.class);
        model = mock(DivisionFabModel.class);
        presenter = new DivisionFabPresenter(view, model);
    }

    @Test
    public void testOnFabCommentClick() throws Exception {
        presenter.onFabCommentClick();

        verify(view).sendClickFabEvent(GALabel.COMMENT);
    }

    @Test
    public void testOnFabAddDoctorClick() throws Exception {
        presenter.onFabAddDoctorClick();

        verify(view).sendClickFabEvent(GALabel.ADD_DOCTOR);
    }

    @Test
    public void testOnPageChanged_switchToDoctor() throws Exception {
        int position = 0;
        when(model.showAddComment(position)).thenReturn(true);

        presenter.onPageChanged(position);

        verify(view).hideAddDoctorFab();
        verify(view).showAddCommentFab();
        verify(model).setLastPagePosition(position);
    }

    @Test
    public void testOnPageChanged_switchToComment() throws Exception {
        int position = 1;
        when(model.showAddDoctor(position)).thenReturn(true);

        presenter.onPageChanged(position);

        verify(view).hideAddCommentFab();
        verify(view).showAddDoctorFab();
        verify(model).setLastPagePosition(position);
    }
}