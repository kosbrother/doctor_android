package kosbrother.com.doctorguide.factory;

import kosbrother.com.doctorguide.R;

public class HospitalFactory {

    public static int createHospitalImageResId(String hospitalGrade) {
        int resId = 0;
        switch (hospitalGrade) {
            case "醫學中心":
                resId = R.mipmap.ic_hospital_biggest;
                break;
            case "區域醫院":
                resId = R.mipmap.ic_hospital_medium;
                break;
            case "地區醫院":
                resId = R.mipmap.ic_hospital_small;
                break;
            case "診所":
                resId = R.mipmap.ic_hospital_smallest;
                break;
            default:
                break;
        }
        return resId;
    }

}
