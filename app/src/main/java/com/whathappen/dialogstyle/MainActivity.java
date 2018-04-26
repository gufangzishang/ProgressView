package com.whathappen.dialogstyle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.whathappen.progresslibrary.control.CircleProgressStyle;
import com.whathappen.progresslibrary.utils.DensityUtils;
import com.whathappen.progresslibrary.view.CircleProgress;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    float progress = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            circleProgress.setProgress(progress).builder();
            if (progress < 100) {
                progress = progress + 1.5f;
                handler.sendEmptyMessageDelayed(0, 200);
            }
        }
    };
    private CircleProgress circleProgress;
    private SeekBar sb_stroke_width;
    private RadioButton rb_has_gradient;
    private RadioButton rb_no_gradient;
    private RadioButton rb_has_dial;
    private RadioButton rb_no_dial;
    private SeekBar sb_dial_stroke_width;
    private SeekBar sb_progress;
    private RadioGroup rg1;
    private RadioGroup rg2;
    private RadioButton rb_value_center;
    private RadioButton rb_value_center_dial;
    private RadioButton rb_value_outer;
    private RadioButton rb_value_outer_dial;
    private RadioButton rb_value_inner;
    private RadioGroup rg3;
    private int minDialWidth;
    private int maxDialWidth;
    private int minStrokeWidth;
    private int maxStrokeWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //handler.sendEmptyMessageDelayed(0, 100);
        initListener();
    }

    private void initView() {
        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);
        sb_stroke_width = (SeekBar) findViewById(R.id.sb_stroke_width);
        rb_has_gradient = (RadioButton) findViewById(R.id.rb_has_gradient);
        rb_no_gradient = (RadioButton) findViewById(R.id.rb_no_gradient);
        rb_has_dial = (RadioButton) findViewById(R.id.rb_has_dial);
        rb_no_dial = (RadioButton) findViewById(R.id.rb_no_dial);
        sb_dial_stroke_width = (SeekBar) findViewById(R.id.sb_dial_stroke_width);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        rg3 = (RadioGroup) findViewById(R.id.rg3);
        rb_value_center = (RadioButton) findViewById(R.id.rb_value_center);
        rb_value_center_dial = (RadioButton) findViewById(R.id.rb_value_center_dial);
        rb_value_outer = (RadioButton) findViewById(R.id.rb_value_outer);
        rb_value_outer_dial = (RadioButton) findViewById(R.id.rb_value_outer_dial);
        rb_value_inner = (RadioButton) findViewById(R.id.rb_value_inner);
    }

    private void initData() {
        //进度
        float progress = circleProgress.getProgress();//当前进度
        sb_progress.setProgress((int) progress);
        //设置宽度为1dp - 10dp
        minStrokeWidth = DensityUtils.dp2px(this, 1);
        maxStrokeWidth = DensityUtils.dp2px(this, 8);
        float strokeWidth = circleProgress.getStrokeWidth();
        sb_stroke_width.setProgress((int) ((strokeWidth - minStrokeWidth) * 100 / (maxStrokeWidth - minStrokeWidth)));
        //样式
        int progressStyle = circleProgress.getProgressStyle();
        switch (progressStyle) {
            case CircleProgressStyle.DEFAULT_TYPE:
                rb_no_gradient.setChecked(true);
                rb_no_dial.setChecked(true);
                break;
            case CircleProgressStyle.GRADIENT_TYPE:
                rb_has_gradient.setChecked(true);
                rb_no_dial.setChecked(true);
                break;
            case CircleProgressStyle.DEFAULT_TYPE_DIAL:
                rb_no_gradient.setChecked(true);
                rb_has_dial.setChecked(true);
                break;
            case CircleProgressStyle.GRADIENT_TYPE_DIAL:
                rb_has_gradient.setChecked(true);
                rb_has_dial.setChecked(true);
                break;
        }
        //刻度宽度
        float[] dialWidth = circleProgress.getDialWidth();
        float singleDialWidth = dialWidth[0];
        minDialWidth = DensityUtils.dp2px(this, 0.5f);
        maxDialWidth = DensityUtils.dp2px(this, 10f);
        sb_dial_stroke_width.setProgress((int) ((singleDialWidth - minDialWidth) * 100 / (maxDialWidth - minDialWidth)));

        //进度值样式
        int progressValueStyle = circleProgress.getProgressValueStyle();
        switch (progressValueStyle) {
            case CircleProgressStyle.DEFAULT_VALUE_TYPE:
                rb_value_center.setChecked(true);
                break;
            case CircleProgressStyle.DEFAULT_VALUE_TYPE_SHOW_DIAL:
                rb_value_center_dial.setChecked(true);
                break;
            case CircleProgressStyle.INNER_VALUE_TYPE:
                rb_value_inner.setChecked(true);
                break;
            case CircleProgressStyle.OUTER_VALUE_TYPE:
                rb_value_outer.setChecked(true);
                break;
            case CircleProgressStyle.OUTER_VALUE_TYPE_SHOW_DIAL:
                rb_value_outer_dial.setChecked(true);
                break;
        }
    }

    private void initListener() {
        sb_progress.setOnSeekBarChangeListener(this);
        sb_stroke_width.setOnSeekBarChangeListener(this);
        sb_dial_stroke_width.setOnSeekBarChangeListener(this);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_no_gradient:
                        rb_no_gradient.setChecked(true);
                        boolean checked = rb_has_dial.isChecked();
                        if (!checked)
                            circleProgress.setProgressStyle(CircleProgressStyle.DEFAULT_TYPE).builder();
                        else
                            circleProgress.setProgressStyle(CircleProgressStyle.DEFAULT_TYPE_DIAL).builder();
                        break;
                    case R.id.rb_has_gradient:
                        rb_has_gradient.setChecked(true);
                        checked = rb_has_dial.isChecked();
                        if (!checked)
                            circleProgress.setProgressStyle(CircleProgressStyle.GRADIENT_TYPE).builder();
                        else
                            circleProgress.setProgressStyle(CircleProgressStyle.GRADIENT_TYPE_DIAL).builder();
                        break;
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_has_dial:
                        rb_has_dial.setChecked(true);
                        boolean checked = rb_has_gradient.isChecked();
                        if (checked)
                            circleProgress.setProgressStyle(CircleProgressStyle.GRADIENT_TYPE_DIAL).builder();
                        else
                            circleProgress.setProgressStyle(CircleProgressStyle.DEFAULT_TYPE_DIAL).builder();
                        break;
                    case R.id.rb_no_dial:
                        rb_no_dial.setChecked(true);
                        checked = rb_has_gradient.isChecked();
                        if (checked)
                            circleProgress.setProgressStyle(CircleProgressStyle.GRADIENT_TYPE).builder();
                        else
                            circleProgress.setProgressStyle(CircleProgressStyle.DEFAULT_TYPE).builder();
                        break;
                }
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_value_center:
                        rb_value_center.setChecked(true);
                        circleProgress.setProgressValueStyle(CircleProgressStyle.DEFAULT_VALUE_TYPE).builder();
                        break;
                    case R.id.rb_value_center_dial:
                        rb_value_center_dial.setChecked(true);
                        circleProgress.setProgressValueStyle(CircleProgressStyle.DEFAULT_VALUE_TYPE_SHOW_DIAL).builder();
                        break;
                    case R.id.rb_value_inner:
                        rb_value_inner.setChecked(true);
                        circleProgress.setProgressValueStyle(CircleProgressStyle.INNER_VALUE_TYPE).builder();
                        break;
                    case R.id.rb_value_outer:
                        rb_value_outer.setChecked(true);
                        circleProgress.setProgressValueStyle(CircleProgressStyle.OUTER_VALUE_TYPE).builder();
                        break;
                    case R.id.rb_value_outer_dial:
                        rb_value_outer_dial.setChecked(true);
                        circleProgress.setProgressValueStyle(CircleProgressStyle.OUTER_VALUE_TYPE_SHOW_DIAL).builder();
                        break;
                }
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sb_progress) {
            circleProgress.setProgress(progress).builder();
        } else if (seekBar == sb_stroke_width) {
            int strokeWidth = progress * (maxStrokeWidth - minStrokeWidth) / 100+minStrokeWidth;
            circleProgress.setStrokeWidth(DensityUtils.dp2px(this,strokeWidth)).builder();//传px
        } else if (seekBar == sb_dial_stroke_width) {
            int singleDialWidth = progress * (maxDialWidth - minDialWidth) / 100+minDialWidth;
            float[] dialWidth = circleProgress.getDialWidth();
            int i = DensityUtils.px2dp(this, singleDialWidth);
            int i1 = DensityUtils.px2dp(this, dialWidth[1]);
            System.out.println("-----i ="+i+",------i1 ="+i1);
            circleProgress.setDialWidth(i,i1).builder();//传px
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
