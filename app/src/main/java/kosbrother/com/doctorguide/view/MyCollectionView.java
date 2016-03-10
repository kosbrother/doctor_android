package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;

public interface MyCollectionView {
    void setContentView();

    void setActionBar();

    void setViewPager();

    void showCancelHospitalCollectDialog(String message);

    void showCancelDoctorCollectDialog(String message);

    void updateAdapter();

    void startHospitalActivity(RealmHospital hospital);

    void startDoctorActivity(RealmDoctor doctor);
}
