package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.model.MyCollectionModel;
import kosbrother.com.doctorguide.view.MyCollectionView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MyCollectionPresenterTest {

    private MyCollectionView view;
    private MyCollectionModel model;
    private MyCollectionPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(MyCollectionView.class);
        model = mock(MyCollectionModel.class);
        presenter = new MyCollectionPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).setViewPager();
    }

    @Test
    public void testOnHospitalHeartClick() throws Exception {
        RealmHospital hospital = new RealmHospital();

        presenter.onHospitalHeartClick(hospital);

        verify(model).setHospital(hospital);
        verify(view).showCancelHospitalCollectDialog(model.getCancelCollectHospitalMsg());
    }

    @Test
    public void testOnConfirmCancelCollectHospitalClick() throws Exception {
        presenter.onConfirmCancelCollectHospitalClick();

        verify(model).removeHospitalFromRealm();
        verify(view).updateAdapter();
    }

    @Test
    public void testOnHospitalItemClick() throws Exception {
        RealmHospital hospital = new RealmHospital();

        presenter.onHospitalItemClick(hospital);

        verify(view).startHospitalActivity(hospital);
    }

    @Test
    public void testOnDoctorHeartClick() throws Exception {
        RealmDoctor doctor = new RealmDoctor();

        presenter.onDoctorHeartClick(doctor);

        verify(model).setDoctor(doctor);
        verify(view).showCancelDoctorCollectDialog(model.getCancelCollectDoctorMsg());
    }

    @Test
    public void testOnConfirmCancelCollectDoctorClick() throws Exception {
        presenter.onConfirmCancelCollectDoctorClick();

        verify(model).removeDoctorFromRealm();
        verify(view).updateAdapter();
    }

    @Test
    public void testOnDoctorItemClick() throws Exception {
        RealmDoctor doctor = new RealmDoctor();

        presenter.onDoctorItemClick(doctor);

        verify(view).startDoctorActivity(doctor);
    }
}