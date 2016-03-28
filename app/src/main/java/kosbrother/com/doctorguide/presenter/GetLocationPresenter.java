package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.GetLocationView;

public class GetLocationPresenter {
    private final GetLocationView view;

    public GetLocationPresenter(GetLocationView view) {
        this.view = view;
    }

    public void onCreate(int sdkInt) {
        view.setGoogleClient();
        if (sdkInt >= 23) {
            view.checkLocationPermission();
        } else {
            view.connectGoogleClient();
        }
    }

    public void onStop() {
        view.disconnectGoogleClient();
    }

    public void onPermissionGranted() {
        view.connectGoogleClient();
    }

    public void onPermissionNotGranted() {
        view.checkShouldShowRequestPermissionRationale();
    }

    public void onShouldShowRequestPermissionRationale() {
        view.showPermissionExplanationSnackBar();
        view.requestLocationPermission();
    }

    public void onShouldNotShowRequestPermissionRationale() {
        view.requestLocationPermission();
    }

    public void onRequestPermissionResultDenied() {
        view.showRequestPermissionSnackBar();
    }

    public void onGetLastLocationNull() {
        view.requestEnableLocationSetting();
    }

    public void onLocationSettingEnable() {
        view.setLoadingView();
        view.requestLocationUpdates();
    }

    public void onLocationSettingDisable() {
        view.showEnableLocationSettingDialog();
    }

    public void onRequestLocationSettingResultCancel() {
        view.showRequestLocationSettingSnackBar();
    }
}
