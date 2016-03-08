package kosbrother.com.doctorguide.model;

import io.realm.Realm;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.task.GetDoctorTask;
import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorScoreViewModel;

public class DoctorModel {
    private final DoctorActivityViewModel viewModel;
    private final Realm realm;
    private Doctor doctor;

    public DoctorModel(DoctorActivityViewModel viewModel, Realm realm) {
        this.viewModel = viewModel;
        this.realm = realm;
    }

    public synchronized boolean isDoctorCollected() {
        RealmDoctor doctor = realm.where(RealmDoctor.class).equalTo("id", viewModel.getDoctorId()).findFirst();
        return doctor != null;
    }

    public int getHeartButtonResId() {
        return isDoctorCollected() ? R.drawable.heart_read_to_white_button : R.drawable.heart_white_to_red_button;
    }

    public String getDoctorName() {
        return viewModel.getDoctorName();
    }

    public int getDoctorId() {
        return viewModel.getDoctorId();
    }

    public synchronized void removeDoctorFromCollect() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", viewModel.getDoctorId()).findFirst();
                doc.removeFromRealm();
            }
        });
    }

    public synchronized void addDoctorToCollect() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmDoctor doctor = realm.createObject(RealmDoctor.class);
                doctor.setId(viewModel.getDoctorId());
                doctor.setName(viewModel.getDoctorName());
                doctor.setHospital(viewModel.getHospitalName());
            }
        });
    }

    public void requestGetDoctor(GetDoctorTask.GetDoctorListener listener) {
        new GetDoctorTask(listener).execute(viewModel.getDoctorId());
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorScoreViewModel getDoctorScoreViewModel() {
        return new DoctorScoreViewModel(doctor);
    }
}
