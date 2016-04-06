package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.custom.CustomSlider;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDateTextEvent;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDivisionSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDoctorSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentSubmitCommentEvent;
import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.presenter.AddCommentPresenter;
import kosbrother.com.doctorguide.view.AddCommentView;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModelImpl;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;


public class AddCommentActivity extends BaseActivity implements
        DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener,
        AddCommentView {

    private ProgressDialog mProgressDialog;

    private AddCommentPresenter presenter;
    private CustomSlider.OnProgressScoredListener divisionScoredListener = new CustomSlider.OnProgressScoredListener() {
        @Override
        public void onProgressScored() {
            presenter.onDivisionScored();
        }
    };

    private CustomSlider.OnProgressScoredListener doctorScoredListener = new CustomSlider.OnProgressScoredListener() {
        @Override
        public void onProgressScored() {
            presenter.onDoctorScored();
        }
    };
    private TextWatcher divisionCommentEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            presenter.afterStep3CommentTextChanged(s.toString());
        }
    };
    private TextWatcher doctorCommentEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            presenter.afterDoctorCommentTextChanged(s.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddCommentViewModel viewModel = new AddCommentViewModelImpl(getIntent());
        presenter = new AddCommentPresenter(this, new AddCommentModel(viewModel));
        presenter.onCreate();
    }

    @Override
    public void setLoadingView() {
        setContentView(R.layout.loading);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_comment);
    }

    @Override
    public void initActionbar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle(getString(R.string.add_comment_title));
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void setHospitalNameText(String hospitalName) {
        ((TextView) findViewById(R.id.hospital_name_edit_text_view)).setText(hospitalName);
    }

    @Override
    public void setDateText(String date) {
        ((TextView) findViewById(R.id.date)).setText(date);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setDivisionSpinner(ArrayList<String> divs, int position) {
        Spinner div_spinner = (Spinner) findViewById(R.id.division_spinner);
        setSpinner(divs, position, div_spinner);
    }

    @Override
    public void setDoctorSpinner(ArrayList<String> drs, int position) {
        Spinner dr_spinner = (Spinner) findViewById(R.id.doctor_spinner);
        setSpinner(drs, position, dr_spinner);
    }

    @Override
    public void initViewSwitcher() {
        getStep1ViewSwitcher().setVisibility(View.VISIBLE);
        getStep2ViewSwitcher().setVisibility(View.GONE);
        getStep3ViewSwitcher().setVisibility(View.GONE);
        getStep4ViewSwitcher().setVisibility(View.GONE);
        getStep5ViewSwitcher().setVisibility(View.GONE);
    }

    @Override
    public void initButton() {
        getStep2NextButton().setEnabled(false);
        getStep3NextButton().setEnabled(false);
        getStep4NextButton().setEnabled(false);
        getStep5NextButton().setEnabled(false);
        getSubmitButton().setVisibility(View.GONE);
    }

    @Override
    public void initEditIcon() {
        findViewById(R.id.step1_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step2_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step3_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step4_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step5_edit_image_view).setVisibility(View.GONE);
    }

    @Override
    public void initSlider() {
        CustomSlider divEnvSlider = getDivEnvSlider();
        divEnvSlider.setSliderLabel("divisionEnvSlide");
        divEnvSlider.setOnProgressScoredListener(divisionScoredListener);

        CustomSlider divEquSlider = getDivEquSlider();
        divEquSlider.setSliderLabel("divisionEquSlide");
        divEquSlider.setOnProgressScoredListener(divisionScoredListener);

        CustomSlider divSpeSlider = getDivSpeSlider();
        divSpeSlider.setSliderLabel("divisionSpeSlide");
        divSpeSlider.setOnProgressScoredListener(divisionScoredListener);

        CustomSlider divFriSlider = getDivFriSlider();
        divFriSlider.setSliderLabel("divisionFriendSlide");
        divFriSlider.setOnProgressScoredListener(divisionScoredListener);

        CustomSlider docFriSlider = getDocFriSlider();
        docFriSlider.setSliderLabel("doctorFriendSlide");
        docFriSlider.setOnProgressScoredListener(doctorScoredListener);

        CustomSlider docSpeSlider = getDocSpeSlider();
        docSpeSlider.setSliderLabel("doctorSpeSlide");
        docSpeSlider.setOnProgressScoredListener(doctorScoredListener);
    }

    @Override
    public void initEditTextListener() {
        getDivisionCommentEditText().addTextChangedListener(divisionCommentEditTextListener);
        getDoctorCommentEditText().addTextChangedListener(doctorCommentEditTextListener);
    }

    @Override
    public void hideDoctorCommentView() {
        findViewById(R.id.step4_relative_layout).setVisibility(View.GONE);
        findViewById(R.id.step5_relative_layout).setVisibility(View.GONE);
    }

    @Override
    public void showDoctorCommentView() {
        findViewById(R.id.step4_relative_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.step5_relative_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void switchStep1Info(String hospitalName, String divisionDoctorString, String dateString) {
        getStep1ViewSwitcher().showNext();
        ((TextView) findViewById(R.id.hospital_name_info_text_view)).setText(hospitalName);
        ((TextView) findViewById(R.id.division_doctor_text_view)).setText(divisionDoctorString);
        ((TextView) findViewById(R.id.date_text_view)).setText(dateString);
    }

    @Override
    public void showStep2ViewSwitcher() {
        getStep2ViewSwitcher().setVisibility(View.VISIBLE);
    }

    @Override
    public void switchStep2Info(AddCommentModel.DivisionScore score) {
        getStep2ViewSwitcher().showNext();
        ((TextView) findViewById(R.id.div_env_score_text_view)).setText(score.getDivEnvScoreText());
        ((TextView) findViewById(R.id.div_equipment_score_text_view)).setText(score.getDivEquScoreText());
        ((TextView) findViewById(R.id.div_friendly_score_text_view)).setText(score.getDivFriScoreText());
        ((TextView) findViewById(R.id.div_spe_score_text_view)).setText(score.getDivSpeScoreText());
    }

    @Override
    public void showStep3View() {
        getStep3ViewSwitcher().setVisibility(View.VISIBLE);
    }

    @Override
    public void switchStep3Info(String divisionComment) {
        getStep3ViewSwitcher().showNext();
        ((TextView) findViewById(R.id.step3_comment_text_view)).setText(divisionComment);
    }

    @Override
    public void showStep4SwitchView() {
        getStep4ViewSwitcher().setVisibility(View.VISIBLE);
    }

    @Override
    public void switchStep4Info(AddCommentModel.DoctorScore score) {
        getStep4ViewSwitcher().showNext();
        ((TextView) findViewById(R.id.doctor_friend_score_text_view)).setText(score.getDocFriScoreText());
        ((TextView) findViewById(R.id.doctor_spe_score_text_view)).setText(score.getDocSpeScoreText());
    }

    @Override
    public void showStep5SwitchView() {
        getStep5ViewSwitcher().setVisibility(View.VISIBLE);
    }

    @Override
    public void switchStep5Info(String doctorComment) {
        getStep5ViewSwitcher().showNext();
        ((TextView) findViewById(R.id.step5_comment_text_view)).setText(doctorComment);
    }

    @Override
    public void showSubmitLayout() {
        getSubmitButton().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSubmitLayout() {
        getSubmitButton().setVisibility(View.GONE);
    }

    @Override
    public void resetStep2View() {
        ViewSwitcher viewSwitcher = getStep2ViewSwitcher();
        viewSwitcher.setVisibility(View.GONE);
        if (viewSwitcher.getCurrentView().getId() == R.id.step2_content_relative_layout) {
            viewSwitcher.showNext();
        }
        ((ImageView) findViewById(R.id.step2_image_view)).setImageResource(R.mipmap.ic_two);
        findViewById(R.id.step2_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step2_label_relative_layout).setClickable(false);
    }

    @Override
    public void resetStep3View() {
        ViewSwitcher viewSwitcher = getStep3ViewSwitcher();
        viewSwitcher.setVisibility(View.GONE);
        if (viewSwitcher.getCurrentView().getId() == R.id.step3_comment_text_view) {
            viewSwitcher.showNext();
        }
        ((ImageView) findViewById(R.id.step3_image_view)).setImageResource(R.mipmap.ic_three);
        findViewById(R.id.step3_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step3_label_relative_layout).setClickable(false);
    }

    @Override
    public void resetStep4View() {
        ViewSwitcher viewSwitcher = getStep4ViewSwitcher();
        viewSwitcher.setVisibility(View.GONE);
        if (viewSwitcher.getCurrentView().getId() == R.id.step4_content_linear_layout) {
            viewSwitcher.showNext();
        }
        ((ImageView) findViewById(R.id.step4_image_view)).setImageResource(R.mipmap.ic_four);
        findViewById(R.id.step4_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step4_label_relative_layout).setClickable(false);
    }

    @Override
    public void resetStep5View() {
        ViewSwitcher viewSwitcher = getStep5ViewSwitcher();
        viewSwitcher.setVisibility(View.GONE);
        if (viewSwitcher.getCurrentView().getId() == R.id.step5_comment_text_view) {
            viewSwitcher.showNext();
        }
        ((ImageView) findViewById(R.id.step5_image_view)).setImageResource(R.mipmap.ic_five);
        findViewById(R.id.step5_edit_image_view).setVisibility(View.GONE);
        findViewById(R.id.step5_label_relative_layout).setClickable(false);
    }

    private void setSpinner(ArrayList<String> data, int position, final Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_black_text, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(position, false);
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(AddCommentActivity.this);
            }
        });
    }

    @Override
    public void sendAddCommentClickDivisionSpinnerEvent(String division) {
        GAManager.sendEvent(new AddCommentClickDivisionSpinnerEvent(division));
    }

    @Override
    public void sendAddCommentClickDoctorSpinnerEvent(String doctorFromPosition) {
        GAManager.sendEvent(new AddCommentClickDoctorSpinnerEvent(doctorFromPosition));
    }

    @Override
    public void sendAddCommentClickSubmitButtonEvent() {
        GAManager.sendEvent(new AddCommentSubmitCommentEvent());
    }

    @Override
    public void sendAddCommentClickDateTextEvent(String dateSetString) {
        GAManager.sendEvent(new AddCommentClickDateTextEvent(dateSetString));
    }

    @Override
    public void showDatePickerDialog(DatePickerViewModel datePickerViewModel) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                datePickerViewModel.getYear(),
                datePickerViewModel.getMonth(),
                datePickerViewModel.getDay());
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void enableStep2NextButton() {
        getStep2NextButton().setEnabled(true);
    }

    @Override
    public void enableStep4NextButton() {
        getStep4NextButton().setEnabled(true);
    }

    @Override
    public void disableStep3NextButton() {
        getStep3NextButton().setEnabled(false);
    }

    @Override
    public void enableStep3NextButton() {
        getStep3NextButton().setEnabled(true);
    }

    @Override
    public void disableStep5NextButton() {
        getStep5NextButton().setEnabled(false);
    }

    @Override
    public void enableStep5NextButton() {
        getStep5NextButton().setEnabled(true);
    }

    @Override
    public void setStep1CheckedAndClickable() {
        ((ImageView) findViewById(R.id.step1_image_view)).setImageResource(R.mipmap.ic_check);
        findViewById(R.id.step1_edit_image_view).setVisibility(View.VISIBLE);
        final View label = findViewById(R.id.step1_label_relative_layout);
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep1EditLayoutClick();
            }
        });
    }

    @Override
    public void setStep2CheckedAndClickable() {
        ((ImageView) findViewById(R.id.step2_image_view)).setImageResource(R.mipmap.ic_check);
        findViewById(R.id.step2_edit_image_view).setVisibility(View.VISIBLE);
        final View label = findViewById(R.id.step2_label_relative_layout);
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep2EditLayoutClick();
            }
        });
    }

    @Override
    public void setStep3CheckedAndClickable() {
        ((ImageView) findViewById(R.id.step3_image_view)).setImageResource(R.mipmap.ic_check);
        findViewById(R.id.step3_edit_image_view).setVisibility(View.VISIBLE);
        final View label = findViewById(R.id.step3_label_relative_layout);
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep3EditLayoutClick();
            }
        });
    }

    @Override
    public void setStep4CheckedAndClickable() {
        ((ImageView) findViewById(R.id.step4_image_view)).setImageResource(R.mipmap.ic_check);
        findViewById(R.id.step4_edit_image_view).setVisibility(View.VISIBLE);
        final View label = findViewById(R.id.step4_label_relative_layout);
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep4EditLayoutClick();
            }
        });
    }

    @Override
    public void setStep5CheckedAndClickable() {
        ((ImageView) findViewById(R.id.step5_image_view)).setImageResource(R.mipmap.ic_check);
        findViewById(R.id.step5_edit_image_view).setVisibility(View.VISIBLE);
        final View label = findViewById(R.id.step5_label_relative_layout);
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep5EditLayoutClick();
            }
        });
    }

    @Override
    public void switchStep1Edit() {
        getStep1ViewSwitcher().showPrevious();
        findViewById(R.id.step1_edit_image_view).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.step1_image_view)).setImageResource(R.mipmap.ic_one);
        findViewById(R.id.step1_label_relative_layout).setClickable(false);
    }

    @Override
    public void switchStep2() {
        getStep2ViewSwitcher().showPrevious();
        findViewById(R.id.step2_edit_image_view).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.step2_image_view)).setImageResource(R.mipmap.ic_two);
        findViewById(R.id.step2_label_relative_layout).setClickable(false);
    }

    @Override
    public void switchStep3() {
        getStep3ViewSwitcher().showPrevious();
        findViewById(R.id.step3_edit_image_view).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.step3_image_view)).setImageResource(R.mipmap.ic_three);
        findViewById(R.id.step3_label_relative_layout).setClickable(false);
    }

    @Override
    public void switchStep4() {
        getStep4ViewSwitcher().showPrevious();
        findViewById(R.id.step4_edit_image_view).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.step4_image_view)).setImageResource(R.mipmap.ic_four);
        findViewById(R.id.step4_label_relative_layout).setClickable(false);
    }

    @Override
    public void switchStep5() {
        getStep5ViewSwitcher().showPrevious();
        findViewById(R.id.step5_edit_image_view).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.step5_image_view)).setImageResource(R.mipmap.ic_five);
        findViewById(R.id.step5_label_relative_layout).setClickable(false);
    }

    @Override
    public void showPostCommentResultSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("評論發表成功")
                .setMessage("謝謝你發表評論，讓資料更完善！")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.division_spinner:
                presenter.onDivisionItemSelected(position);
                break;
            case R.id.doctor_spinner:
                presenter.onDoctorItemSelected(position);
                break;
            default:
                break;
        }
    }

    public void onDateButtonClick(View v) {
        presenter.onDateButtonClick();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int y, int m, int dayOfMonth) {
        presenter.onDateSet(y, m, dayOfMonth);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onStep1NextButtonClick(View view) {
        presenter.onStep1NextButtonClick();
    }

    public void onStep2NextButtonClick(View view) {
        presenter.onStep2NextButtonClick(getDivisionScore());
    }

    public void onStep3SkipButtonClick(View view) {
        presenter.onStep3SkipButtonClick();
    }

    public void onStep3NextButtonClick(View view) {
        presenter.onStep3NextButtonClick(getDivisionCommentEditText().getText().toString());
    }

    public void onStep4NextButtonClick(View view) {
        presenter.onStep4NextButtonClick(getDoctorScore(), ((RadioButton) findViewById(R.id.radio_recommend_yes)).isChecked());
    }

    public void onStep5NextButtonClick(View view) {
        presenter.onStep5NextButtonClick(getDoctorCommentEditText().getText().toString());
    }

    public void onStep5SkipButtonClick(View view) {
        presenter.onStep5SkipButtonClick();
    }

    public void onSubmitButtonClick(View view) {
        presenter.onSubmitButtonClick();
    }

    private CustomSlider getDocSpeSlider() {
        return (CustomSlider) findViewById(R.id.doctor_spe_slide);
    }

    private CustomSlider getDocFriSlider() {
        return (CustomSlider) findViewById(R.id.doctor_friendly_slide);
    }

    private CustomSlider getDivFriSlider() {
        return (CustomSlider) findViewById(R.id.division_friendly_slide);
    }

    private CustomSlider getDivSpeSlider() {
        return (CustomSlider) findViewById(R.id.division_spe_slide);
    }

    private CustomSlider getDivEquSlider() {
        return (CustomSlider) findViewById(R.id.division_equipment_slide);
    }

    private CustomSlider getDivEnvSlider() {
        return (CustomSlider) findViewById(R.id.division_env_slide);
    }

    private ViewSwitcher getStep1ViewSwitcher() {
        return (ViewSwitcher) findViewById(R.id.step1_view_switcher);
    }

    private ViewSwitcher getStep2ViewSwitcher() {
        return (ViewSwitcher) findViewById(R.id.step2_view_switcher);
    }

    private ViewSwitcher getStep3ViewSwitcher() {
        return (ViewSwitcher) findViewById(R.id.step3_view_switcher);
    }

    private ViewSwitcher getStep4ViewSwitcher() {
        return (ViewSwitcher) findViewById(R.id.step4_view_switcher);
    }

    private ViewSwitcher getStep5ViewSwitcher() {
        return (ViewSwitcher) findViewById(R.id.step5_view_switcher);
    }

    private View getSubmitButton() {
        return findViewById(R.id.submit_frame_layout);
    }

    private View getStep2NextButton() {
        return findViewById(R.id.step2_next_button);
    }

    private View getStep3NextButton() {
        return findViewById(R.id.step3_next_button);
    }

    private View getStep4NextButton() {
        return findViewById(R.id.step4_next_button);
    }

    private View getStep5NextButton() {
        return findViewById(R.id.step5_next_button);
    }

    private EditText getDoctorCommentEditText() {
        return (EditText) findViewById(R.id.doctor_comment_edit_text);
    }

    private EditText getDivisionCommentEditText() {
        return (EditText) findViewById(R.id.step3_comment_edit_text);
    }

    private AddCommentModel.DivisionScore getDivisionScore() {
        AddCommentModel.DivisionScore divisionScore = new AddCommentModel.DivisionScore();
        divisionScore.setDivEnvScore(getDivEnvSlider().getScore());
        divisionScore.setDivEquScore(getDivEquSlider().getScore());
        divisionScore.setDivSpeScore(getDivSpeSlider().getScore());
        divisionScore.setDivFriScore(getDivFriSlider().getScore());
        return divisionScore;
    }

    private AddCommentModel.DoctorScore getDoctorScore() {
        AddCommentModel.DoctorScore doctorScore = new AddCommentModel.DoctorScore();
        doctorScore.setDocFriScore(getDocFriSlider().getScore());
        doctorScore.setDocSpeScore(getDocSpeSlider().getScore());
        return doctorScore;
    }

}
