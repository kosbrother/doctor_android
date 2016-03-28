package kosbrother.com.doctorguide.view;

public interface GetLocationView {
    void checkLocationPermission();

    void checkShouldShowRequestPermissionRationale();

    void requestLocationPermission();

    void showPermissionExplanationSnackBar();

    void showRequestPermissionSnackBar();

    void requestEnableLocationSetting();

    void showRequestLocationSettingSnackBar();

    void showEnableLocationSettingDialog();

    void setGoogleClient();

    void connectGoogleClient();

    void disconnectGoogleClient();

    void requestLocationUpdates();

    void setLoadingView();
}
