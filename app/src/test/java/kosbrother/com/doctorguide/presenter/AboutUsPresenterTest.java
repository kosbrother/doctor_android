package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.view.AboutUsView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AboutUsPresenterTest {

    private AboutUsView view;
    private AboutUsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(AboutUsView.class);
        presenter = spy(new AboutUsPresenter(view));
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initContentView();
    }

    @Test
    public void testOnFeedbackButtonClick() throws Exception {
        presenter.onFeedbackButtonClick();

        verify(view).startFeedbackActivity();
    }

}