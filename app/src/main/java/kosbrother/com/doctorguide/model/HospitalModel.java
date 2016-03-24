package kosbrother.com.doctorguide.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import io.realm.Realm;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.factory.HospitalFactory;
import kosbrother.com.doctorguide.task.GetHospitalTask;
import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalScoreViewModel;

public class HospitalModel {
    private static final String appUriString =
            "android-app://kosbrother.com.doctorguide/http/doctorguide.tw/hospitals/";
    private static final String webUriString =
            "http://doctorguide.tw/hospitals/";

    private Uri appUri;
    private Uri webUrl;

    private final HospitalActivityViewModel viewModel;
    private final Realm realm;
    private Hospital hospital;

    public HospitalModel(HospitalActivityViewModel viewModel, Realm realm) {
        this.viewModel = viewModel;
        this.realm = realm;
        setAppUri(viewModel);
        setWebUrl(viewModel);
    }

    public Uri getAppUri() {
        return appUri;
    }

    public Uri getWebUrl() {
        return webUrl;
    }

    public int getHospitalImageRedId() {
        return HospitalFactory.createHospitalImageResId(viewModel.getHospitalGrade());
    }

    public String getHospitalName() {
        return viewModel.getHospitalName();
    }

    public int getHospitalId() {
        return viewModel.getHospitalId();
    }

    public int getHeartButtonBackgroundResId() {
        return isHospitalCollected() ? R.drawable.heart_read_to_white_button : R.drawable.heart_white_to_red_button;
    }

    public void requestGetHospital(GetHospitalTask.GetHospitalListener listener) {
        new GetHospitalTask(listener).execute(viewModel.getHospitalId());
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public synchronized void removeHospitalFromCollect() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", viewModel.getHospitalId()).findFirst();
                hosp.removeFromRealm();
            }
        });
    }

    public synchronized void addHospitalToCollect() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmHospital hosp = realm.createObject(RealmHospital.class);
                hosp.setId(viewModel.getHospitalId());
                hosp.setGrade(viewModel.getHospitalGrade());
                hosp.setName(viewModel.getHospitalName());
                hosp.setAddress(hospital.address);
            }
        });
    }

    public synchronized boolean isHospitalCollected() {
        RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", viewModel.getHospitalId()).findFirst();
        return hosp != null;
    }

    public HospitalScoreViewModel getHospitalScoreViewModel() {
        return new HospitalScoreViewModel(hospital);
    }

    public Hospital getHospital() {
        return hospital;
    }

    public String getHospitalLabel() {
        return "醫院: " + viewModel.getHospitalName();
    }

    private void setAppUri(HospitalActivityViewModel viewModel) {
        appUri = Uri.parse(appUriString + getHospitalDataString(viewModel));
    }

    private void setWebUrl(HospitalActivityViewModel viewModel) {
        webUrl = Uri.parse(webUriString + getHospitalDataString(viewModel));
    }

    @NonNull
    private String getHospitalDataString(HospitalActivityViewModel viewModel) {
        return viewModel.getHospitalId() + "-"
                + viewModel.getHospitalName() + "-"
                + viewModel.getHospitalGrade();
    }
}
