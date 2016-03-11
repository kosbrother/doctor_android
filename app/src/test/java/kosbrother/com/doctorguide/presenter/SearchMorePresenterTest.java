package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.view.SearchMoreView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchMorePresenterTest {

    private SearchMoreView view;
    private SearchMorePresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(SearchMoreView.class);
        presenter = new SearchMorePresenter(view);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
    }
}