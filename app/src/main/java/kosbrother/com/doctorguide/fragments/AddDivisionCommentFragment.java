package kosbrother.com.doctorguide.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kosbrother.com.doctorguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDivisionCommentFragment extends Fragment {


    private EnablePagerSlide mListener;
    private Boolean directSubmit;
    private Button next;

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
        next = (Button)view.findViewById(R.id.next_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFilled())
                    mListener.enablePagerSlide();
                else{
                    Snackbar snackbar = Snackbar.make(next, getNoticeString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    private String getNoticeString() {
        return "環境衛生尚未打分數";
    }

    private boolean isFilled() {
        return false;
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
