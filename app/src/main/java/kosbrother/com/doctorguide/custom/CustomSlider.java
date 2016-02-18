package kosbrother.com.doctorguide.custom;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentSlideEvent;

/**
 * Created by steven on 2/2/16.
 */
public class CustomSlider extends LinearLayout {

    private DiscreteSeekBar seekbar;
    private ImageView checkImage;
    private TextView scoreText;
    private Context mContext;
    private boolean isScroed;
    private int score;
    private String sliderLabel;

    public CustomSlider(Context context) {
        super(context);
        initControl(context);
    }

    public CustomSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public CustomSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initControl(context);
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_custom_slider, this);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        seekbar = (DiscreteSeekBar)this.findViewById(R.id.seekbar);
        checkImage = (ImageView)this.findViewById(R.id.check_image);
        scoreText = (TextView)this.findViewById(R.id.score);

        seekbar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                GAManager.sendEvent(new AddCommentSlideEvent(sliderLabel, value));

                isScroed = true;
                score = value;
                scoreText.setText(value + "");
                scoreText.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                checkImage.setVisibility(View.VISIBLE);
                seekbar.setScrubberColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                seekbar.setThumbColor(ContextCompat.getColor(mContext, R.color.colorAccent),ContextCompat.getColor(mContext, R.color.colorAccent));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

    }

    public boolean isScroed(){
        return isScroed;
    }

    public int getScore(){
        return score;
    }

    public void setSliderLabel(String sliderLabel) {
        this.sliderLabel = sliderLabel;
    }
}
