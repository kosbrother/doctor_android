package kosbrother.com.doctorguide.model;

import io.realm.Realm;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;

public class MyCollectionModel {
    private final Realm realm;
    private RealmHospital hospital;
    private RealmDoctor doctor;

    public MyCollectionModel(Realm realm) {
        this.realm = realm;
    }

    public String getCancelCollectHospitalMsg() {
        return "確定要取消收藏「" + hospital.getName() + "」嗎？";
    }

    public void setHospital(RealmHospital hospital) {
        this.hospital = hospital;
    }

    public void removeHospitalFromRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", hospital.getId()).findFirst();
                hosp.removeFromRealm();
            }
        });
    }

    public void setDoctor(RealmDoctor doctor) {
        this.doctor = doctor;
    }

    public String getCancelCollectDoctorMsg() {
        return "確定要取消收藏「" + doctor.getName() + " 醫師" + "」嗎？";
    }

    public void removeDoctorFromRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", doctor.getId()).findFirst();
                doc.removeFromRealm();
            }
        });
    }
}
