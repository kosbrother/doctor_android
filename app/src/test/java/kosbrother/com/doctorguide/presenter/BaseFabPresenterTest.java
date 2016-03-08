package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.BaseFabModel;
import kosbrother.com.doctorguide.view.BaseFabView;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaseFabPresenterTest {

    private BaseFabView view;
    private BaseFabModel model;
    private BaseFabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(BaseFabView.class);
        model = mock(BaseFabModel.class);
        presenter = new BaseFabPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).initFab();
    }

    @Test
    public void testOnFabMenuToggle() throws Exception {
        boolean opened = anyBoolean();

        presenter.onFabMenuToggle(opened);

        verify(view).sendClickFabEvent(GALabel.FAB_MENU);
        verify(view).setFabImageDrawable(model.getFabDrawableId(opened));
    }

    @Test
    public void testOnFabShareClick() throws Exception {
        presenter.onFabShareClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.SHARE);
        verify(view).startShareActivity();
    }
}