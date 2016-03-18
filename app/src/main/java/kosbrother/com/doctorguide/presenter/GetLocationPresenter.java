package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.GetLocationView;

public class GetLocationPresenter {
    private final GetLocationView view;

    public GetLocationPresenter(GetLocationView view) {
        this.view = view;
    }

    public void onCreate(int sdkInt) {
        if (sdkInt >= 23) {
            view.checkLocationPermission();
        } else {
            view.setGoogleClient();
        }
    }

    public void onStop() {
        view.disconnectGoogleClient();
    }

    public void onPermissionGranted() {
        view.setGoogleClient();
    }

    public void onPermissionNotGranted() {
        view.checkShouldShowRequestPermissionRationale();
    }

    public void onShouldShowRequestPermissionRationale() {
        view.requestLocationPermission();
    }

    public void onShouldNotShowRequestPermissionRationale() {
        view.showRequestPermissionSnackBar();
        view.requestLocationPermission();
    }

    public void onRequestPermissionResultSuccess() {
        view.setGoogleClient();
    }

    public void onRequestPermissionResultDenied() {
        view.showRequestPermissionDeniedSnackBar();
    }

    public void onGetLastLocationNull() {
        view.requestLocationUpdates();
    }
}
