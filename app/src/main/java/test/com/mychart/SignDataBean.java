package test.com.mychart;

import android.view.View;
import android.widget.TextView;

/**
 * Created by pc-20171125 on 2018/5/24.
 */

public class SignDataBean {

    private TextView textView;
    private String date;
    private String weekDay;
    private boolean isSign;
    private boolean isEnable;

    public SignDataBean(TextView textView, String date, String weekDay, boolean isSign, boolean isEnable) {
        this.textView = textView;
        this.date = date;
        this.weekDay = weekDay;
        this.isSign = isSign;
        this.isEnable = isEnable;

        if (isEnable) {
            textView.setVisibility(View.VISIBLE);
            if (isSign) {
                textView.setClickable(false);
                textView.setText("已签到");
            } else {
                textView.setText("未签到");
                textView.setClickable(true);
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

}
