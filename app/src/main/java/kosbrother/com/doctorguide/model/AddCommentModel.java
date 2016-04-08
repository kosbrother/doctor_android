package kosbrother.com.doctorguide.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.task.GetDivisionScoreTask;
import kosbrother.com.doctorguide.task.SubmitCommentTask;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;

public class AddCommentModel {
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

    private String divisionComment = "";
    private String doctorComment = "";
    private DivisionScore divisionScore;
    private DoctorScore doctorScore;
    private int divisionScoreCount = 0;
    private int doctorScoreCount = 0;
    private boolean recommend;

    public AddCommentModel(AddCommentViewModel viewModel) {
        this.viewModel = viewModel;
        divisionId = viewModel.getDivisionId();
        doctorId = viewModel.getDoctorId();
        hospitalId = viewModel.getHospitalId();

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DAY_OF_MONTH);
    }

    public String getHospitalName() {
        return viewModel.getHospitalName();
    }

    public void requestGetDivisions(GetDivisionScoreTask.GetDivisionScoreListener listener) {
        new GetDivisionScoreTask(listener).execute(hospitalId);
    }

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
            if (divisionId == 0) {
                divisionId = divsValue.get(0);
            }
        }
    }

    private int getDivisionIdFromDoctors() {
        for (Division d : divisions) {
            if (d.doctors != null) {
                for (Doctor dr : d.doctors) {
                    if (dr.id == doctorId)
                        return d.id;
                }
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

    public void updateDivisionAndDoctors(int position) {
        divisionId = divsValue.get(position);
        initDoctors();
    }

    public void updateDoctor(int position) {
        doctorId = drsValue.get(position);
    }

    public ArrayList<String> getDivisions() {
        return divs;
    }

    public String getDivisionFromPosition(int position) {
        return divs.get(position);
    }

    public int getDivisionSelection() {
        return divsValue.indexOf(divisionId);
    }

    public ArrayList<String> getDoctors() {
        return drs;
    }

    public String getDoctorFromPosition(int position) {
        return drs.get(position);
    }

    public int getDoctorSelection() {
        int index = drsValue.indexOf(doctorId);
        return index == -1 ? 0 : index;
    }

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

    public String getDateString() {
        return "就診日期：" + year + "/" + month + "/" + day + " ";
    }

    public void updateDate(int y, int m, int dayOfMonth) {
        year = y;
        month = m + 1;
        day = dayOfMonth;
    }

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
    public void requestSubmitComment(SubmitCommentTask.SubmitCommentListener listener) {
        setSubmitParams();
        new SubmitCommentTask(listener).execute(submitParams);
    }

    private void setSubmitParams() {
        submitParams.put("hospital_id", hospitalId + "");
        submitParams.put("division_id", divisionId + "");
        submitParams.put("user", viewModel.getUser());

        submitParams.put("div_equipment", divisionScore.getEquScore());
        submitParams.put("div_environment", divisionScore.getEnvScore());
        submitParams.put("div_speciality", divisionScore.getSpeScore());
        submitParams.put("div_friendly", divisionScore.getFriScore());
        submitParams.put("div_comment", divisionComment);

        if (!isDivisionComment()) {
            submitParams.put("doctor_id", doctorId + "");
            submitParams.put("dr_speciality", doctorScore.getSpeScore());
            submitParams.put("dr_friendly", doctorScore.getFriScore());
            submitParams.put("dr_comment", doctorComment);
            submitParams.put("is_recommend", String.valueOf(recommend));
        }
    }

    public boolean isDivisionComment() {
        return doctorId == 0;
    }

    public void setDivisionComment(String divisionComment) {
        this.divisionComment = divisionComment;
    }

    public String getDivisionComment() {
        return divisionComment;
    }

    public void setDivisionScore(DivisionScore divisionScore) {
        this.divisionScore = divisionScore;
    }

    public DivisionScore getDivisionScore() {
        return divisionScore;
    }

    public void setDoctorScore(DoctorScore doctorScore) {
        this.doctorScore = doctorScore;
    }

    public void setDoctorComment(String doctorComment) {
        this.doctorComment = doctorComment;
    }

    public String getDivisionDoctorString() {
        return getDivisionFromPosition(getDivisionSelection()) + "　|　" + getDoctorFromPosition(getDoctorSelection());
    }

    public void plusDivisionScoredCount() {
        divisionScoreCount++;
    }

    public boolean divisionScoreFinish() {
        return divisionScoreCount == 4;
    }

    public void plusDoctorScoredCount() {
        doctorScoreCount++;
    }

    public boolean doctorScoreFinish() {
        return doctorScoreCount == 2;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }


    public static class DivisionScore {

        private String divEnvScore;
        private String divEquScore;
        private String divSpeScore;
        private String divFriScore;

        public void setDivEnvScore(String divEnvScore) {
            this.divEnvScore = divEnvScore;
        }

        public String getEnvScore() {
            return divEnvScore;
        }

        public String getDivEnvScoreText() {
            return "環境衛生：" + divEnvScore;
        }

        public void setDivEquScore(String divEquScore) {
            this.divEquScore = divEquScore;
        }

        public String getEquScore() {
            return divEquScore;
        }

        public String getDivEquScoreText() {
            return "醫療設備：" + divEquScore;
        }

        public void setDivSpeScore(String divSpeScore) {
            this.divSpeScore = divSpeScore;
        }

        public String getSpeScore() {
            return divSpeScore;
        }

        public String getDivSpeScoreText() {
            return "醫護專業：" + divSpeScore;
        }

        public void setDivFriScore(String divFriScore) {
            this.divFriScore = divFriScore;
        }

        public String getFriScore() {
            return divFriScore;
        }

        public String getDivFriScoreText() {
            return "服務態度：" + divFriScore;
        }
    }

    public static class DoctorScore {

        private String docFriScore;
        private String docSpeScore;

        public void setDocFriScore(String docFriScore) {
            this.docFriScore = docFriScore;
        }

        public String getFriScore() {
            return docFriScore;
        }

        public String getDocFriScoreText() {
            return "醫師態度：" + docFriScore;
        }

        public void setDocSpeScore(String docSpeScore) {
            this.docSpeScore = docSpeScore;
        }

        public String getSpeScore() {
            return docSpeScore;
        }

        public String getDocSpeScoreText() {
            return "醫師專業：" + docSpeScore;
        }
    }
}
