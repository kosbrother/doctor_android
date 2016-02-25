package kosbrother.com.doctorguide.presenter;

import junit.framework.TestCase;

import kosbrother.com.doctorguide.view.AboutUsView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AboutUsPresenterTest extends TestCase {

    private AboutUsView view;
    private AboutUsPresenter presenter;

    public void setUp() throws Exception {
        super.setUp();
        view = mock(AboutUsView.class);
        presenter = spy(new AboutUsPresenter(view));
    }

    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initContentView();
    }

    public void testOnFeedbackButtonClick() throws Exception {
        presenter.onFeedbackButtonClick();

        verify(view).startFeedbackActivity();
    }

    public void testOnHomeItemSelected() throws Exception {
        presenter.onHomeItemSelected();

        verify(view).finish();
    }
}