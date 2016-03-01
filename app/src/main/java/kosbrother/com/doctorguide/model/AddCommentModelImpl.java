package kosbrother.com.doctorguide.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.task.GetDivisionScoreTask;
import kosbrother.com.doctorguide.task.SubmitCommentTask;

public class AddCommentModelImpl implements AddCommentModel {
    private final int hospitalId;
    private int doctorId;
    private int divisionId;

    private AddCommentViewModel viewModel;
    private ArrayList<String> divs = new ArrayList<>();
    private ArrayList<Integer> divsValue = new ArrayList<>();
    private ArrayList<String> drs = new ArrayList<>();
    private ArrayList<Division> divisions;
    private ArrayList<Integer> drsValue = new ArrayList<>();
    private HashMap<String, String> submitParams = new HashMap<>();
    private int year;
    private int month;
    private int day;

    public AddCommentModelImpl(AddCommentViewModel viewModel) {
        this.viewModel = viewModel;
        divisionId = viewModel.getDivisionId();
        doctorId = viewModel.getDoctorId();
        hospitalId = viewModel.getHospitalId();

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public String getHospitalName() {
        return viewModel.getHospitalName();
    }

    @Override
    public void requestGetDivisions(GetDivisionScoreTask.GetDivisionScoreListener listener) {
        new GetDivisionScoreTask(listener).execute(hospitalId);
    }

    @Override
    public void initResultData(ArrayList<Division> divisions) {
        this.divisions = divisions;
        initDivision();
        initDoctors();
    }

    private void initDivision() {
        for (Division d : divisions) {
            divs.add(d.name);
            divsValue.add(d.id);
        }
        if (divisionId == 0) {
            divisionId = getDivisionIdFromDoctors();
        }
    }

    private int getDivisionIdFromDoctors() {
        for (Division d : divisions) {
            for (Doctor dr : d.doctors) {
                if (dr.id == doctorId)
                    return d.id;
            }
        }
        return 0;
    }

    private void initDoctors() {
        drs.clear();
        drs.add("未指定醫師");

        drsValue.clear();
        drsValue.add(0);

        Division division = getDivisionFromId();
        if (division != null && division.doctors != null) {
            for (Doctor dr : division.doctors) {
                drs.add(dr.name);
                drsValue.add(dr.id);
            }
        }
    }

    private Division getDivisionFromId() {
        for (int i = 0; i < divisions.size(); i++) {
            Division d = divisions.get(i);
            if (divisionId == d.id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public void updateDivisionAndDoctors(int position) {
        divisionId = divsValue.get(position);
        initDoctors();
    }

    @Override
    public void updateDoctor(int position) {
        doctorId = drsValue.get(position);
    }

    @Override
    public ArrayList<String> getDivisions() {
        return divs;
    }

    @Override
    public String getDivisionFromPosition(int position) {
        return divs.get(position);
    }

    @Override
    public int getDivisionSelection() {
        return divsValue.indexOf(divisionId);
    }

    @Override
    public ArrayList<String> getDoctors() {
        return drs;
    }

    @Override
    public String getDoctorFromPosition(int position) {
        return drs.get(position);
    }

    @Override
    public int getDoctorSelection() {
        int index = drsValue.indexOf(doctorId);
        return index == -1 ? 0 : index;
    }

    @Override
    public void putSubmitParams(HashMap<String, String> map) {
        if (doctorId != 0)
            submitParams.put("doctor_id", doctorId + "");
        if (hospitalId != 0)
            submitParams.put("hospital_id", hospitalId + "");
        if (divisionId != 0)
            submitParams.put("division_id", divisionId + "");
        submitParams.put("user", viewModel.getUser());
        for (String key : map.keySet()) {
            submitParams.put(key, map.get(key));
        }
    }

    @Override
    public String getDateString() {
        return year + "年" + month + "月" + day + "日";
    }

    @Override
    public void updateDate(int y, int m, int dayOfMonth) {
        year = y;
        month = m + 1;
        day = dayOfMonth;
    }

    @Override
    public DatePickerViewModel getDatePickerViewModel() {
        return new DatePickerViewModel() {
            @Override
            public int getYear() {
                return year;
            }

            @Override
            public int getMonth() {
                return month - 1;
            }

            @Override
            public int getDay() {
                return day;
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestSubmitComment(SubmitCommentTask.SubmitCommentListener listener) {
        new SubmitCommentTask(listener).execute(submitParams);
    }
}
