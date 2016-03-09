package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;

public interface HospitalDoctorView extends ProgressDialogView {
    void setContentView();

    void setActionBar();

    void setGoogleClient();

    void setViewPager();

    void checkLocationPermission();

    void checkShouldShowRequestPermissionRationale();

    void requestLocationPermission();

    void requestLocationUpdates();

    void showRequestPermissionSnackBar();

    void showRequestPermissionDeniedSnackBar();

    void showDivisionsDialog(String[] divisionNameArray, Hospital hospital);

    void disconnectGoogleClient();

    void removeLocationUpdatesListener();

    void startDivisionActivity(ArrayList<Division> divisions, Hospital hospital, int position);

    void startDoctorActivity(Doctor doctor);
}
