package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.HospitalModel;
import kosbrother.com.doctorguide.view.HospitalView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HospitalPresenterTest {

    private HospitalView view;
    private HospitalModel model;
    private HospitalPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(HospitalView.class);
        model = mock(HospitalModel.class);
        presenter = new HospitalPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).setHeartButtonBackground(model.getHeartButtonBackgroundResId());
        verify(view).setHospitalName(model.getHospitalName());
        verify(view).setHospitalImage(model.getHospitalImageRedId());

        verify(view).showProgressDialog();
        verify(model).requestGetHospital(presenter);
    }

    @Test
    public void testOnStart() throws Exception {
        presenter.onStart();

        verify(view).connectAppIndexClient();
    }

    @Test
    public void testOnStop() throws Exception {
        presenter.onStop();

        verify(view).endAppIndexApi(model.getHospitalName(), model.getWebUrl(), model.getAppUri());
        verify(view).disConnectAppIndexClient();
    }

    @Test
    public void testOnGetHospitalSuccess() throws Exception {
        Hospital hospital = new Hospital();

        presenter.onGetHospitalSuccess(hospital);

        verify(model).setHospital(hospital);

        verify(view).setHeartButton();
        verify(view).setHospitalScore(model.getHospitalScoreViewModel());
        verify(view).setViewPager(model.getHospitalId(), model.getHospital());
        verify(view).hideProgressDialog();
        verify(view).startAppIndexApi(model.getHospitalName(), model.getWebUrl(), model.getAppUri());
    }

    @Test
    public void testOnHeartButtonClick_hospitalCollected() throws Exception {
        when(model.isHospitalCollected()).thenReturn(true);

        presenter.onHeartButtonClick();

        verify(view).sendHospitalClickCollectEvent(model.getHospitalName());
        verify(model).removeHospitalFromCollect();
        verify(view).showRemoveFromCollectSuccessSnackBar();
        verify(view).setHeartButtonBackground(model.getHeartButtonBackgroundResId());
    }

    @Test
    public void testOnHeartButtonClick_hospitalNotCollected() throws Exception {
        when(model.isHospitalCollected()).thenReturn(false);

        presenter.onHeartButtonClick();

        verify(view).sendHospitalClickCollectEvent(model.getHospitalName());
        verify(model).addHospitalToCollect();
        verify(view).showAddToCollectSuccessSnackBar();
        verify(view).setHeartButtonBackground(model.getHeartButtonBackgroundResId());
    }

    @Test
    public void testOnDivisionInfoClick() throws Exception {
        Division division = new Division();

        presenter.onDivisionInfoClick(division);

        verify(view).showDivisionInfoDialog(division);
    }

    @Test
    public void testOnDivisionClick() throws Exception {
        Division division = new Division();

        presenter.onDivisionClick(division);

        verify(view).startDivisionActivity(division);
    }

    @Test
    public void testOnAddCommentClick() throws Exception {
        presenter.onAddCommentClick();

        verify(view).sendHospitalClickAddCommentEvent(model.getHospitalLabel());
    }
}