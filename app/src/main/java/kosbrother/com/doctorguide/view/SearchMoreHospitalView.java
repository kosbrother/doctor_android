package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;

public interface SearchMoreHospitalView extends SearchMoreView {

    void setHospitalsListView(ArrayList<Hospital> hospitals);
}
