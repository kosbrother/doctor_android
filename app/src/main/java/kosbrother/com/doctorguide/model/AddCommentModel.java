package kosbrother.com.doctorguide.model;

import java.util.ArrayList;
import java.util.HashMap;

import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.task.GetDivisionScoreTask;
import kosbrother.com.doctorguide.task.SubmitCommentTask;

public interface AddCommentModel {
    String getHospitalName();

    void requestGetDivisions(GetDivisionScoreTask.GetDivisionScoreListener listener);

    void initResultData(ArrayList<Division> divisions);

    void updateDivisionAndDoctors(int position);

    void updateDoctor(int position);

    ArrayList<String> getDivisions();

    String getDivisionFromPosition(int position);

    int getDivisionSelection();

    ArrayList<String> getDoctors();

    String getDoctorFromPosition(int position);

    int getDoctorSelection();

    void putSubmitParams(HashMap<String, String> map);

    String getDateString();

    void updateDate(int y, int m, int dayOfMonth);

    DatePickerViewModel getDatePickerViewModel();

    void requestSubmitComment(SubmitCommentTask.SubmitCommentListener listener);
}
