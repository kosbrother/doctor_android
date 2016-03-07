package kosbrother.com.doctorguide.model;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.Util.StringUtil;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.factory.DivisionFactory;
import kosbrother.com.doctorguide.task.GetDivisionTask;
import kosbrother.com.doctorguide.task.GetDivisionTask.GetDivisionListener;
import kosbrother.com.doctorguide.task.GetDivisionsTask;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionScoreViewModel;

import static kosbrother.com.doctorguide.task.GetDivisionsTask.GetDivisionsListener;

public class DivisionModel {
    private final DivisionActivityViewModel viewModel;
    private Division division;
    private ArrayList<Division> divisions;
    private int doctorId;

    public DivisionModel(DivisionActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public String getHospitalName() {
        return viewModel.getHospitalName();
    }

    public int getHospitalId() {
        return viewModel.getHospitalId();
    }

    public int getDivisionId() {
        return viewModel.getDivisionId();
    }

    public String getDivisionName() {
        return viewModel.getDivisionName();
    }

    public int getDivisionImageResId() {
        return DivisionFactory.createDivisionImageResId(viewModel.getHospitalGrade());
    }

    public String getHospitalNameWithUnderline() {
        return StringUtil.appendHtmlUnderline(getHospitalName());
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Division getDivision() {
        return division;
    }

    public DivisionScoreViewModel getDivisionScoreViewModel() {
        return new DivisionScoreViewModel(division);
    }

    public String getCancelCollectMessage(String name) {
        return "確定要取消收藏「" + name + " 醫師" + "」嗎？";
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setDivisions(ArrayList<Division> divisions) {
        this.divisions = divisions;
    }

    public List<String> getDivisionNames() {
        List<String> strings = new ArrayList<>();
        for (Division div : divisions)
            strings.add(div.name);
        return strings;
    }

    public String getClickDivisionName(int position) {
        return divisions.get(position).name;
    }

    public int getClickDivisionId(int position) {
        return divisions.get(position).id;
    }

    public void requestGetDivision(GetDivisionListener listener) {
        new GetDivisionTask(getDivisionId(), getHospitalId(), listener).execute();
    }

    public void requestGetDivisions(GetDivisionsListener listener) {
        new GetDivisionsTask(listener).execute(getHospitalId());
    }

    public DivisionActivityViewModel getDivisionInputViewModel() {
        return viewModel;
    }
}
