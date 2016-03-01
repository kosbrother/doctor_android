package kosbrother.com.doctorguide.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
public class AddDivisionCommentFragment extends Fragment {

    private EnablePagerSlide mListener;
    private Boolean directSubmit;
    private Button next;
    private CustomSlider divisionEnvSlide;
    private CustomSlider divisionEquSlide;
    private CustomSlider divisionSpeSlide;
    private CustomSlider divisionFriendSlide;
    private PassParamsToActivity passMethod;
    private EditText divComment;

    FragmentReceiver receiver = new FragmentReceiver();

    public AddDivisionCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(receiver, new IntentFilter("fragmentupdater"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_division_comment, container, false);
        divisionEnvSlide = (CustomSlider) view.findViewById(R.id.division_env_slide);
        divisionEnvSlide.setSliderLabel("divisionEnvSlide");
        divisionEquSlide = (CustomSlider) view.findViewById(R.id.division_equipment_slide);
        divisionEquSlide.setSliderLabel("divisionEquSlide");
        divisionSpeSlide = (CustomSlider) view.findViewById(R.id.division_spe_slide);
        divisionSpeSlide.setSliderLabel("divisionSpeSlide");
        divisionFriendSlide = (CustomSlider) view.findViewById(R.id.division_friendly_slide);
        divisionFriendSlide.setSliderLabel("divisionFriendSlide");
        divComment = (EditText) view.findViewById(R.id.div_comment);


        next = (Button) view.findViewById(R.id.next_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFilled() && directSubmit) {
                    GAManager.sendEvent(new AddCommentSubmitCommentEvent(GALabel.FINISH));

                    passMethod.passParams(getSubmitParams());
                    passMethod.onSubmitClick();
                } else if (isFilled()) {
                    GAManager.sendEvent(new AddCommentSubmitCommentEvent(GALabel.NEXT_STEP));

                    passMethod.passParams(getSubmitParams());
                    mListener.onDivisionNextClick();
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
        hashMap.put("div_equipment", divisionEquSlide.getScore() + "");
        hashMap.put("div_environment", divisionEnvSlide.getScore() + "");
        hashMap.put("div_speciality", divisionSpeSlide.getScore() + "");
        hashMap.put("div_friendly", divisionFriendSlide.getScore() + "");
        hashMap.put("div_comment", divComment.getText().toString());
        return hashMap;
    }

    private String getNoticeString() {
        List<String> list = new ArrayList<>();
        if (!divisionEnvSlide.isScroed())
            list.add("環境衛生");
        if (!divisionEquSlide.isScroed())
            list.add("醫療設備");
        if (!divisionSpeSlide.isScroed())
            list.add("醫護專業");
        if (!divisionFriendSlide.isScroed())
            list.add("服務態度");
        String joined = TextUtils.join(", ", list);
        return joined + " 尚未打分數！";
    }

    private boolean isFilled() {
        if (!divisionEnvSlide.isScroed())
            return false;
        if (!divisionEquSlide.isScroed())
            return false;
        if (!divisionSpeSlide.isScroed())
            return false;
        if (!divisionFriendSlide.isScroed())
            return false;

        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EnablePagerSlide) {
            mListener = (EnablePagerSlide) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        if (context instanceof PassParamsToActivity) {
            passMethod = (PassParamsToActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    public interface EnablePagerSlide {
        void onDivisionNextClick();
    }

    public class FragmentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                directSubmit = extras.getBoolean("directSubmit");
                if (directSubmit)
                    next.setText("完成");
                else
                    next.setText("下一步");
            }
        }
    }

}
