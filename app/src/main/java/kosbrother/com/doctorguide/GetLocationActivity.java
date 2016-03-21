package kosbrother.com.doctorguide;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import kosbrother.com.doctorguide.presenter.GetLocationPresenter;
import kosbrother.com.doctorguide.view.GetLocationView;

public abstract class GetLocationActivity extends BaseActivity implements
        GetLocationView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int PERMISSION_REQUEST_LOCATION = 0;

    protected Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private GetLocationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GetLocationPresenter(this);
        presenter.onCreate(Build.VERSION.SDK_INT);
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void setGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void checkShouldShowRequestPermissionRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            presenter.onShouldShowRequestPermissionRationale();
        } else {
            presenter.onShouldNotShowRequestPermissionRationale();
        }
    }

    @Override
    public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            presenter.onPermissionGranted();
        } else {
            presenter.onPermissionNotGranted();
        }
    }

    public void requestLocationPermission() {
        // Request the permission. The result will be received in onRequestPermissionResult().
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.onRequestPermissionResultSuccess();
            } else {
                // Permission request was denied.
                presenter.onRequestPermissionResultDenied();
            }
        }
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            presenter.onGetLastLocationNull();
        } else {
            onGetLocationSuccess(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
    }

    @SuppressWarnings("ResourceType")
    public void requestLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        onGetLocationSuccess(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
    }

    protected abstract void onGetLocationSuccess(LatLng latLng);

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void disconnectGoogleClient() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    @Override
    public void showRequestPermissionSnackBar() {
        Snackbar.make(findViewById(android.R.id.content),
                "就醫指南需要位置的權限，才能幫你找到附近的醫院，醫生",
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRequestPermissionDeniedSnackBar() {
        Snackbar.make(findViewById(android.R.id.content),
                "位置存取權限被拒絕！無法讀取資料", Snackbar.LENGTH_INDEFINITE)
                .setAction("確定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestLocationPermission();
                    }
                }).show();
    }
}
