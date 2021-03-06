package test.com.mychart;

import android.animation.AnimatorSet;
import android.widget.TextView;

/**
 * Created by pc-20171125 on 2018/5/23.
 */

public class MyDataBean {

    private TextView tv;
    //移动的X,Y坐标
    private Integer moveX;
    private Integer moveY;
    //随机生成的x,y
    private Integer randomX;
    private Integer randomY;
    //动画
    private AnimatorSet randomSet;
    //当前的图片是否可点击，默认为false
    private Boolean isClick = false;
    //对应的颜色
    private Integer colorID;

    public MyDataBean(TextView tv) {
        this.tv = tv;
    }

    public MyDataBean(TextView tv, Integer colorID) {
        this.tv = tv;
        this.colorID = colorID;
    }

    public Integer getMoveX() {
        return moveX;
    }

    public void setMoveX(Integer moveX) {
        this.moveX = moveX;
    }

    public Integer getMoveY() {
        return moveY;
    }

    public void setMoveY(Integer moveY) {
        this.moveY = moveY;
    }

    public Integer getRandomX() {
        return randomX;
    }

    public void setRandomX(Integer randomX) {
        this.randomX = randomX;
    }

    public Integer getRandomY() {
        return randomY;
    }

    public void setRandomY(Integer randomY) {
        this.randomY = randomY;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public AnimatorSet getRandomSet() {
        return randomSet;
    }

    public void setRandomSet(AnimatorSet randomSet) {
        this.randomSet = randomSet;
    }

    public Boolean getClick() {
        return isClick;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }

    public Integer getColorID() {
        return colorID;
    }


}
