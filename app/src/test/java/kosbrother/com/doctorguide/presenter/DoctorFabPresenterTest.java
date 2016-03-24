package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.view.DoctorFabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DoctorFabPresenterTest {

    private DoctorFabView view;
    private DoctorFabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(DoctorFabView.class);
        presenter = new DoctorFabPresenter(view);
    }

    @Test
    public void testOnFabCommentClick() throws Exception {
        presenter.onFabAddCommentClick();

        verify(view).sendClickFabEvent(GALabel.COMMENT);
    }

}