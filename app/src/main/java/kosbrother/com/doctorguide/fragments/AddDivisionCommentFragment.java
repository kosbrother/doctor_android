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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDivisionCommentFragment extends Fragment {


    private EnablePagerSlide mListener;
    private Boolean directSubmit;
    private Button next;
    private CustomSlider envSlide;
    private CustomSlider equSlide;
    private CustomSlider speSlide;
    private CustomSlider friendSlide;
    private PassParamsToActivity passMethod;
    private EditText divComment;

    public AddDivisionCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(new FragmentReceiver(), new IntentFilter("fragmentupdater"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_division_comment, container, false);
        envSlide = (CustomSlider)view.findViewById(R.id.env_slide);
        equSlide = (CustomSlider)view.findViewById(R.id.equipment_slide);
        speSlide = (CustomSlider)view.findViewById(R.id.spe_slide);
        friendSlide = (CustomSlider)view.findViewById(R.id.friendly_slide);
        divComment = (EditText)view.findViewById(R.id.div_comment);


        next = (Button)view.findViewById(R.id.next_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilled() && directSubmit) {
                    passMethod.passParams(getSubmitParams());
                    passMethod.submitPost();
                } else if (isFilled()) {
                    passMethod.passParams(getSubmitParams());
                    mListener.enablePagerSlide();
                } else {
                    Snackbar snackbar = Snackbar.make(next, getNoticeString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    private HashMap<String, String> getSubmitParams() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("div_equipment",equSlide.getScore()+"");
        hashMap.put("div_environment",envSlide.getScore()+"");
        hashMap.put("div_speciality",speSlide.getScore()+"");
        hashMap.put("div_friendly",friendSlide.getScore()+"");
        hashMap.put("div_comment",divComment.getText().toString());
        return hashMap;
    }

    private String getNoticeString() {
        List<String> list = new ArrayList<String>();
        if(envSlide.isScroed() == false)
            list.add("環境衛生");
        if(equSlide.isScroed() == false)
            list.add("醫療設備");
        if(speSlide.isScroed() == false)
            list.add("醫護專業");
        if(friendSlide.isScroed() == false)
            list.add("服務態度");
        String joined = TextUtils.join(", ", list);
        return joined + " 尚未打分數！";
    }

    private boolean isFilled() {
        if(envSlide.isScroed() == false)
            return false;
        if(equSlide.isScroed() == false)
            return false;
        if(speSlide.isScroed() == false)
            return false;
        if(friendSlide.isScroed() == false)
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

    public interface EnablePagerSlide {
        void enablePagerSlide();
    }

    public class FragmentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                directSubmit = extras.getBoolean("directSubmit");
                if(directSubmit)
                    next.setText("完成");
                else
                    next.setText("下一步");
            }
        }
    }

}
