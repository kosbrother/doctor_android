package kosbrother.com.doctorguide.presenter;

import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.view.HospitalFabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HospitalFabPresenterTest {

    @Test
    public void testOnFabAddCommentClick() throws Exception {
        HospitalFabView view = mock(HospitalFabView.class);
        HospitalFabPresenter presenter = new HospitalFabPresenter(view);

        presenter.onFabAddCommentClick();

        verify(view).sendClickAddCommentFabEvent(GALabel.COMMENT);
    }
}