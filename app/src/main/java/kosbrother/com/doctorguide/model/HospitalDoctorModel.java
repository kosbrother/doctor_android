package kosbrother.com.doctorguide.model;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.task.GetDivisionsByHospitalAndCategoryTask;
import kosbrother.com.doctorguide.task.GetDivisionsByHospitalAndCategoryTask.GetDivisionsListener;

public class HospitalDoctorModel {
    private final int categoryId;
    private Hospital hospital;
    private ArrayList<Division> divisions;

    public HospitalDoctorModel(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void requestGetDivisions(GetDivisionsListener listener) {
        new GetDivisionsByHospitalAndCategoryTask(listener).execute(hospital.id, categoryId);
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setDivisions(ArrayList<Division> divisions) {
        this.divisions = divisions;
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }

    public String[] getDivisionArray() {
        List<String> divisionNameList = new ArrayList<>();
        for (Division div : divisions)
            divisionNameList.add(div.name);
        return divisionNameList.toArray(new String[divisionNameList.size()]);
    }
}
