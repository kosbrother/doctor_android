package kosbrother.com.doctorguide.presenter;

import org.junit.Test;
import org.mockito.Mockito;

import kosbrother.com.doctorguide.view.ClickShareView;

public class ClickSharePresenterTest {

    @Test
    public void testOnShareClick() throws Exception {
        ClickShareView view = Mockito.mock(ClickShareView.class);
        ClickSharePresenter presenter = new ClickSharePresenter(view);

        presenter.onShareClick();

        Mockito.verify(view).startShareActivity();
    }
}