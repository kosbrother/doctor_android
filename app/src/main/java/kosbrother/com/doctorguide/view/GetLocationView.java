package kosbrother.com.doctorguide.view;

public interface GetLocationView {
    void checkLocationPermission();

    void setGoogleClient();

    void checkShouldShowRequestPermissionRationale();

    void requestLocationPermission();

    void requestLocationUpdates();

    void showRequestPermissionSnackBar();

    void showRequestPermissionDeniedSnackBar();

    void disconnectGoogleClient();
}
