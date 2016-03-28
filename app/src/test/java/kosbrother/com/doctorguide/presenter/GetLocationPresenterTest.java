package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.view.GetLocationView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetLocationPresenterTest {

    private GetLocationView view;
    private GetLocationPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(GetLocationView.class);
        presenter = new GetLocationPresenter(view);
    }

    @Test
    public void testOnCreate_sdkInkAbove23() throws Exception {
        presenter.onCreate(23);

        verify(view).setGoogleClient();
        verify(view).checkLocationPermission();
    }

    @Test
    public void testOnCreate_sdkInkBelow23() throws Exception {
        presenter.onCreate(22);

        verify(view).setGoogleClient();
        verify(view).connectGoogleClient();
    }

    @Test
    public void testOnPermissionGranted() throws Exception {
        presenter.onPermissionGranted();

        verify(view).connectGoogleClient();
    }

    @Test
    public void testOnPermissionNotGranted() throws Exception {
        presenter.onPermissionNotGranted();

        verify(view).checkShouldShowRequestPermissionRationale();
    }

    @Test
    public void testOnShouldShowRequestPermissionRationale() throws Exception {
        presenter.onShouldShowRequestPermissionRationale();

        verify(view).showPermissionExplanationSnackBar();
        verify(view).requestLocationPermission();
    }

    @Test
    public void testOnShouldNotShowRequestPermissionRationale() throws Exception {
        presenter.onShouldNotShowRequestPermissionRationale();

        verify(view).requestLocationPermission();
    }

    @Test
    public void testOnRequestPermissionResultDenied() throws Exception {
        presenter.onRequestPermissionResultDenied();

        verify(view).showRequestPermissionSnackBar();
    }

    @Test
    public void testOnGetLastLocationNull() throws Exception {
        presenter.onGetLastLocationNull();

        verify(view).requestEnableLocationSetting();
    }

    @Test
    public void testOnStop() throws Exception {
        presenter.onStop();

        verify(view).disconnectGoogleClient();
    }

    @Test
    public void testOnLocationSettingEnable() throws Exception {
        presenter.onLocationSettingEnable();

        verify(view).requestLocationUpdates();
    }

    @Test
    public void testOnLocationSettingDisable() {
        presenter.onLocationSettingDisable();

        verify(view).showEnableLocationSettingDialog();
    }

    @Test
    public void onRequestLocationSettingResultCancel() {
        presenter.onRequestLocationSettingResultCancel();

        verify(view).showRequestLocationSettingSnackBar();
    }

}