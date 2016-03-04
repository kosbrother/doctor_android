package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.view.BaseView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BasePresenterTest {

    private BaseView view;
    private BasePresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(BaseView.class);
        presenter = new BasePresenter(view);
    }

    @Test
    public void testOnHomeItemSelected() throws Exception {
        presenter.onHomeItemSelected();

        verify(view).finish();
    }
}