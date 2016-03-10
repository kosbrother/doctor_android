package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.model.MyCollectionModel;
import kosbrother.com.doctorguide.view.MyCollectionView;

public class MyCollectionPresenter {
    private final MyCollectionView view;
    private final MyCollectionModel model;

    public MyCollectionPresenter(MyCollectionView view, MyCollectionModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
        view.setViewPager();
    }

    public void onHospitalHeartClick(RealmHospital hospital) {
        model.setHospital(hospital);
        view.showCancelHospitalCollectDialog(model.getCancelCollectHospitalMsg());
    }

    public void onConfirmCancelCollectHospitalClick() {
        model.removeHospitalFromRealm();
        view.updateAdapter();
    }

    public void onHospitalItemClick(RealmHospital hospital) {
        view.startHospitalActivity(hospital);
    }

    public void onDoctorHeartClick(RealmDoctor doctor) {
        model.setDoctor(doctor);
        view.showCancelDoctorCollectDialog(model.getCancelCollectDoctorMsg());
    }

    public void onConfirmCancelCollectDoctorClick() {
        model.removeDoctorFromRealm();
        view.updateAdapter();
    }

    public void onDoctorItemClick(RealmDoctor doctor) {
        view.startDoctorActivity(doctor);
    }
}
