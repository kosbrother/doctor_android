package kosbrother.com.doctorguide.presenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.DoctorModel;
import kosbrother.com.doctorguide.view.DoctorView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorPresenterTest {

    private DoctorView view;
    private DoctorModel model;
    private DoctorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(DoctorView.class);
        model = mock(DoctorModel.class);
        presenter = new DoctorPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
        verify(view).initHeartButton();
        verify(view).setHeartButtonBackground(model.getHeartButtonResId());

        verify(view).showProgressDialog();
        verify(model).requestGetDoctor(presenter);
    }

    @Test
    public void testOnHeartButtonClick_DoctorCollected() throws Exception {
        when(model.isDoctorCollected()).thenReturn(true);

        presenter.onHeartButtonClick();

        verify(view).sendDoctorClickCollectEvent(model.getDoctorName());
        verify(model).removeDoctorFromCollect();
        verify(view).showCancelCollectSnackBar();
        verify(view).setHeartButtonBackground(model.getHeartButtonResId());
    }

    @Test
    public void testOnHeartButtonClick_doctorNotCollected() throws Exception {
        when(model.isDoctorCollected()).thenReturn(false);

        presenter.onHeartButtonClick();

        verify(view).sendDoctorClickCollectEvent(model.getDoctorName());
        verify(model).addDoctorToCollect();
        verify(view).showCollectSuccessSnackBar();
        verify(view).setHeartButtonBackground(model.getHeartButtonResId());
    }

    @Test
    public void testOnGetDoctorSuccess() throws Exception {
        Doctor doctor = new Doctor();

        presenter.onGetDoctorSuccess(doctor);

        verify(model).setDoctor(doctor);
        verify(view).setViewPager(model.getDoctorId());
        verify(view).setDoctorName(model.getDoctorName());
        verify(view).setDoctorScore(model.getDoctorScoreViewModel());
        verify(view).hideProgressDialog();
    }

    @Test
    public void testGetDoctor() throws Exception {
        Doctor expect = model.getDoctor();

        Doctor actual = presenter.getDoctor();

        Assert.assertEquals(expect, actual);
    }
}