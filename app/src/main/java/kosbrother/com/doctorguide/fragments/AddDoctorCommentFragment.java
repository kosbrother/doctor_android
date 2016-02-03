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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDoctorCommentFragment extends Fragment {


    private CustomSlider speSlide;
    private CustomSlider friendSlide;
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
        speSlide = (CustomSlider)view.findViewById(R.id.spe_slide);
        friendSlide = (CustomSlider)view.findViewById(R.id.friendly_slide);
        drComment = (EditText)view.findViewById(R.id.dr_comment);
        radioGroup = (RadioGroup)view.findViewById(R.id.rgroup);
        radioRecommendYes = (RadioButton)view.findViewById(R.id.radio_recommend_yes);
        radioRecommendNo = (RadioButton)view.findViewById(R.id.radio_recommend_no);

        next = (Button)view.findViewById(R.id.next_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilled()) {
                    passMethod.passParams(getSubmitParams());
                    passMethod.submitPost();
                }else{
                    Snackbar snackbar = Snackbar.make(next, getNoticeString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    private HashMap<String, String> getSubmitParams() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("dr_speciality",speSlide.getScore()+"");
        hashMap.put("dr_friendly",friendSlide.getScore()+"");
        hashMap.put("dr_comment",drComment.getText().toString());
        if(radioGroup.getCheckedRadioButtonId() == radioRecommendYes.getId())
            hashMap.put("is_recommend","true");
        else
            hashMap.put("is_recommend","false");
        return hashMap;
    }

    private String getNoticeString() {
        String noticeString = "";
        List<String> list = new ArrayList<String>();
        if(speSlide.isScroed() == false)
            list.add("醫師專業");
        if(friendSlide.isScroed() == false)
            list.add("醫師態度");
        if(list.size() > 0)
            noticeString = TextUtils.join(", ", list) + " 尚未打分數！";
        if(radioGroup.getCheckedRadioButtonId() == -1)
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
        if(speSlide.isScroed() == false)
            return false;
        if(friendSlide.isScroed() == false)
            return false;
        if(radioGroup.getCheckedRadioButtonId() == -1)
            return false;
        return true;
    }
}
