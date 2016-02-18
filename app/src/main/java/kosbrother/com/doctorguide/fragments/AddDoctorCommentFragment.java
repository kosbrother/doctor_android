package kosbrother.com.doctorguide.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.PassParamsToActivity;
import kosbrother.com.doctorguide.custom.CustomSlider;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentSubmitCommentEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDoctorCommentFragment extends Fragment {


    private CustomSlider doctorSpeSlide;
    private CustomSlider doctorFriendSlide;
    private Button next;
    private PassParamsToActivity passMethod;
    private EditText drComment;
    private RadioGroup radioGroup;
    private RadioButton radioRecommendYes;
    private RadioButton radioRecommendNo;

    public AddDoctorCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_doctor_comment, container, false);
        doctorFriendSlide = (CustomSlider) view.findViewById(R.id.doctor_friendly_slide);
        doctorFriendSlide.setSliderLabel("doctorFriendSlide");
        doctorSpeSlide = (CustomSlider) view.findViewById(R.id.doctor_spe_slide);
        doctorSpeSlide.setSliderLabel("doctorSpeSlide");
        drComment = (EditText) view.findViewById(R.id.dr_comment);
        radioGroup = (RadioGroup) view.findViewById(R.id.rgroup);
        radioRecommendYes = (RadioButton) view.findViewById(R.id.radio_recommend_yes);
        radioRecommendNo = (RadioButton) view.findViewById(R.id.radio_recommend_no);

        next = (Button) view.findViewById(R.id.next_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilled()) {
                    GAManager.sendEvent(new AddCommentSubmitCommentEvent(GALabel.FINISH));

                    passMethod.passParams(getSubmitParams());
                    passMethod.submitPost();
                } else {
                    GAManager.sendEvent(new AddCommentSubmitCommentEvent(GALabel.DATA_NOT_FILLED));

                    Snackbar snackbar = Snackbar.make(next, getNoticeString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    private HashMap<String, String> getSubmitParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dr_speciality", doctorSpeSlide.getScore() + "");
        hashMap.put("dr_friendly", doctorFriendSlide.getScore() + "");
        hashMap.put("dr_comment", drComment.getText().toString());
        if (radioGroup.getCheckedRadioButtonId() == radioRecommendYes.getId())
            hashMap.put("is_recommend", "true");
        else
            hashMap.put("is_recommend", "false");
        return hashMap;
    }

    private String getNoticeString() {
        String noticeString = "";
        List<String> list = new ArrayList<>();
        if (!doctorSpeSlide.isScroed())
            list.add("醫師專業");
        if (!doctorFriendSlide.isScroed())
            list.add("醫師態度");
        if (list.size() > 0)
            noticeString = TextUtils.join(", ", list) + " 尚未打分數！";
        if (radioGroup.getCheckedRadioButtonId() == -1)
            noticeString = noticeString + "是否推薦醫師未選擇！";
        return noticeString;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PassParamsToActivity) {
            passMethod = (PassParamsToActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public boolean isFilled() {
        if (!doctorSpeSlide.isScroed())
            return false;
        if (!doctorFriendSlide.isScroed())
            return false;
        if (radioGroup.getCheckedRadioButtonId() == -1)
            return false;
        return true;
    }
}
