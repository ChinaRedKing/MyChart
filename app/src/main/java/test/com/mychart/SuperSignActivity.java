package test.com.mychart;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pc-WangJincheng on 2018/5/24.
 */

public class SuperSignActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMon, tvTues, tvWed, tvThur, tvFri, tvSat, tvSun;
    private TextView tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;

    private ArrayList<SignDataBean> mSignDataBean = new ArrayList<SignDataBean>();
    private ArrayList<MyDataBean> mMyDataBean = new ArrayList<MyDataBean>();
    private int[] resList = new int[]{
            R.drawable.shape_circle_monday,
            R.drawable.shape_circle_tuesday,
            R.drawable.shape_circle_wednesday,
            R.drawable.shape_circle_thursday,
            R.drawable.shape_circle_friday,
            R.drawable.shape_circle_saturday,
            R.drawable.shape_circle_sunday
    };

    //翻转动画
    private Animation back_anim;
    private Animation front_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_sign);
        initUI();
        initData();
        initAnim();
    }

    public void initUI() {
        tvMon = (TextView) findViewById(R.id.tvMon);
        tvTues = (TextView) findViewById(R.id.tvTues);
        tvWed = (TextView) findViewById(R.id.tvWed);
        tvThur = (TextView) findViewById(R.id.tvThur);
        tvFri = (TextView) findViewById(R.id.tvFri);
        tvSat = (TextView) findViewById(R.id.tvSat);
        tvSun = (TextView) findViewById(R.id.tvSun);

        tvMonday = (TextView) findViewById(R.id.tvMonday);
        tvTuesday = (TextView) findViewById(R.id.tvTuesday);
        tvWednesday = (TextView) findViewById(R.id.tvWednesday);
        tvThursday = (TextView) findViewById(R.id.tvThursday);
        tvFriday = (TextView) findViewById(R.id.tvFriday);
        tvSaturday = (TextView) findViewById(R.id.tvSaturday);
        tvSunday = (TextView) findViewById(R.id.tvSunday);

        tvMonday.setOnClickListener(this);
        tvTuesday.setOnClickListener(this);
        tvWednesday.setOnClickListener(this);
        tvThursday.setOnClickListener(this);
        tvFriday.setOnClickListener(this);
        tvSaturday.setOnClickListener(this);
        tvSunday.setOnClickListener(this);
    }

    public void initData() {
        mSignDataBean.add(new SignDataBean(tvMonday, "2018-05-21", "星期一", true, false));
        mSignDataBean.add(new SignDataBean(tvTuesday, "2018-05-22", "星期二", false, false));
        mSignDataBean.add(new SignDataBean(tvWednesday, "2018-05-23", "星期三", true, false));
        mSignDataBean.add(new SignDataBean(tvThursday, "2018-05-24", "星期四", true, false));
        mSignDataBean.add(new SignDataBean(tvFriday, "2018-05-25", "星期五", true, false));
        mSignDataBean.add(new SignDataBean(tvSaturday, "2018-05-26", "星期六", true, false));
        mSignDataBean.add(new SignDataBean(tvSunday, "2018-05-27", "星期日", false, true));

        mMyDataBean.add(new MyDataBean(tvMon));
        mMyDataBean.add(new MyDataBean(tvTues));
        mMyDataBean.add(new MyDataBean(tvWed));
        mMyDataBean.add(new MyDataBean(tvThur));
        mMyDataBean.add(new MyDataBean(tvFri));
        mMyDataBean.add(new MyDataBean(tvSat));
        mMyDataBean.add(new MyDataBean(tvSun));
        for (int i = 0; i < mSignDataBean.size(); i++) {
            boolean isSign = mSignDataBean.get(i).isSign();
            mMyDataBean.get(i).getTv().setText(mSignDataBean.get(i).getWeekDay());
            if (isSign) {
                mMyDataBean.get(i).getTv().setBackground(getResources().getDrawable(R.drawable.shape_circle_is_sign));
            } else {
                mMyDataBean.get(i).getTv().setBackground(getResources().getDrawable(resList[i]));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMonday:
                setSignClick(0);
                break;
            case R.id.tvTuesday:
                setSignClick(1);
                break;
            case R.id.tvWednesday:
                setSignClick(2);
                break;
            case R.id.tvThursday:
                setSignClick(3);
                break;
            case R.id.tvFriday:
                setSignClick(4);
                break;
            case R.id.tvSaturday:
                setSignClick(5);
                break;
            case R.id.tvSunday:
                setSignClick(6);
                break;
            default:
                break;
        }
    }

    /**
     * 当前不能点击,下一个可以点击,动画替换位置
     */
    public void setSignClick(int currentIndex) {
        mSignDataBean.get(currentIndex).getTextView().setClickable(false);
        mMyDataBean.get(currentIndex).getRandomSet().cancel(); //动画效果先取消
        imageViewFlip(mSignDataBean.get(currentIndex), mMyDataBean.get(currentIndex), currentIndex);//传入主次View
    }

    //动画初始化
    private void initAnim() {
        //翻转动画
        back_anim = AnimationUtils.loadAnimation(this, R.anim.back_scale);
        front_anim = AnimationUtils.loadAnimation(this, R.anim.front_scale);
        //第一个图片
        for (int i = 0; i < mSignDataBean.size(); i++) {
            if (mSignDataBean.get(i).isEnable() || mSignDataBean.get(i).getDate().equals("2018-05-27")) {
                doAnimateZoom(mSignDataBean.get(i));
            }
        }

        int imageView = 1;
        //周围比较大的动画(不包括第一一天的)
        for (int i = 0; i < mMyDataBean.size(); i++) {
            doAnimateOpen(mMyDataBean.get(i), i + (imageView++), 15, 260);
        }
        randomAnim();
    }

    /**
     * 初始化时，第一天的就在中间，直接放大就好
     *
     * @param data
     */
    private void doAnimateZoom(SignDataBean data) {
        AnimatorSet set = new AnimatorSet();
        //包含缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(data.getTextView(), "scaleX", 0f, 2f),
                ObjectAnimator.ofFloat(data.getTextView(), "scaleY", 0f, 2f),
                ObjectAnimator.ofFloat(data.getTextView(), "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1000).start();
    }

    /**
     * @param data   需要位移的View
     * @param index  位置
     * @param total  平分多少份
     * @param radius 圆的半径
     */
    private void doAnimateOpen(MyDataBean data, int index, int total, int radius) {
        if (data.getTv().getVisibility() != View.VISIBLE) {
            data.getTv().setVisibility(View.VISIBLE);//TextView显示
        }
        double degree = Math.toRadians(360) / (total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        data.setMoveX(translationX);
        data.setMoveY(translationY);
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(data.getTv(), "translationX", 0, translationX),
                ObjectAnimator.ofFloat(data.getTv(), "translationY", 0, translationY),
                ObjectAnimator.ofFloat(data.getTv(), "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(data.getTv(), "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(data.getTv(), "alpha", 0f, 1)
        );
        //动画周期为500ms
        set.setDuration(500).start();
    }

    private void randomAnim() {
        //随机值范围
        int min = 10;
        int max = 20;
        Random random = new Random();
        for (int i = 0; i < mMyDataBean.size(); i++) {
            TextView roleImageView = mMyDataBean.get(i).getTv();
            //取随机值
            int x = random.nextInt(max) % (max - min + 1) + min;
            int y = random.nextInt(max) % (max - min + 1) + min;

            int originalX = mMyDataBean.get(i).getMoveX();
            int originalY = mMyDataBean.get(i).getMoveY();

            mMyDataBean.get(i).setRandomX(x);
            mMyDataBean.get(i).setRandomY(y);

            AnimatorSet set = new AnimatorSet();
            //设置图片的动画
            ObjectAnimator moveX = ObjectAnimator.ofFloat(roleImageView, "translationX",
                    originalX, originalX - x, originalX,//X坐标的运动轨迹为:原点，左，原点，右，原点
                    originalX + x, originalX);
            ObjectAnimator moveY = ObjectAnimator.ofFloat(roleImageView, "translationY",
                    originalY, originalY - y, originalY,//Y坐标的运动轨迹为:原点，上，原点，下，原点
                    originalY + y, originalY);

            //设置动画循环
            moveX.setRepeatCount(-1);
            moveY.setRepeatCount(-1);
            set.playTogether(moveX, moveY);
            set.setDuration(4000);
            mMyDataBean.get(i).setRandomSet(set);
        }
        //利用线程制造时间差，实现周围圆的不同步
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < mMyDataBean.size(); i++) {
                        Thread.sleep(500 / 7);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新UI
                                AnimatorSet set = mMyDataBean.get(finalI).getRandomSet();
                                set.start();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 图片交换位置动画
     *
     * @param mainTextImageMap
     * @param minorTextImageMap
     */
    private void imageViewFlip(final SignDataBean mainTextImageMap, final MyDataBean minorTextImageMap, final int position) {
        final TextView MainImage = mainTextImageMap.getTextView();
        //动画绑定
        MainImage.startAnimation(back_anim);
        //动画监听
        back_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //翻转动画开始，签到下面的提示词，渐渐消失
                AnimatorSet set = new AnimatorSet();
                //包含缩放和透明度动画
                //动画周期为500ms
                set.setDuration(500).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MainImage.startAnimation(front_anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        //翻转动画监听
        front_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView tv_integral = mainTextImageMap.getTextView();
                tv_integral.setVisibility(View.VISIBLE);
                doAnimateMoveSwapPlaces(mainTextImageMap, minorTextImageMap, position);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 交换位置
     *
     * @param mainTextImageMap  中间大圆
     * @param minorTextImageMap 要替换的周围小圆
     */
    private void doAnimateMoveSwapPlaces(final SignDataBean mainTextImageMap, final MyDataBean minorTextImageMap, final int position) {
        final TextView MainImage = mainTextImageMap.getTextView();
        final TextView MinorImage = minorTextImageMap.getTv();

        final TextView MainText = mainTextImageMap.getTextView();

        final int moveX = minorTextImageMap.getMoveX();
        final int moveY = minorTextImageMap.getMoveY();

        final int RandomX = minorTextImageMap.getRandomX();
        final int RandomY = minorTextImageMap.getRandomY();

        final AnimatorSet set = new AnimatorSet();

        //缩小，平移到周围
        ObjectAnimator anim_roundeX = ObjectAnimator.ofFloat(MainImage, "translationX", 0, moveX);
        ObjectAnimator anim_roundeY = ObjectAnimator.ofFloat(MainImage, "translationY", 0, moveY);
        ObjectAnimator anim_scale_rounde_X = ObjectAnimator.ofFloat(MainImage, "scaleX", 2f, 1f);
        ObjectAnimator anim_scale_rounde_Y = ObjectAnimator.ofFloat(MainImage, "scaleY", 2f, 1f);
        //平移TextView
        ObjectAnimator anim_move_textview_X = ObjectAnimator.ofFloat(MainText, "translationX", 0, moveX);
        ObjectAnimator anim_move_textview_Y = ObjectAnimator.ofFloat(MainText, "translationY", 0, moveY);
        //放大，平移到中间
        ObjectAnimator anim_centerX = ObjectAnimator.ofFloat(MinorImage, "translationX", moveX, 0);
        ObjectAnimator anim_centerY = ObjectAnimator.ofFloat(MinorImage, "translationY", moveY, 0);
        ObjectAnimator anim_scale_center_X = ObjectAnimator.ofFloat(MinorImage, "scaleX", 1f, 2f);
        ObjectAnimator anim_scale_center_Y = ObjectAnimator.ofFloat(MinorImage, "scaleY", 1f, 2f);

        //动画同时启动
        set.playTogether(
                anim_roundeX, anim_roundeY, anim_scale_rounde_X, anim_scale_rounde_Y,//中间的大圆
                anim_move_textview_X, anim_move_textview_Y,//要移动的TextView
                anim_centerX, anim_centerY, anim_scale_center_X, anim_scale_center_Y//周围的小圆
        );
        //设置动画完成的时间
        set.setDuration(2000);
        //启动动画
        set.start();

        anim_centerX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                AnimatorSet set = new AnimatorSet();
                //ImageView
                ObjectAnimator Image_moveX = ObjectAnimator.ofFloat(MainImage, "translationX",
                        moveX - RandomX, moveX, moveX + RandomX, moveX, moveX - RandomX);
                ObjectAnimator Image_moveY = ObjectAnimator.ofFloat(MainImage, "translationY",
                        moveY - RandomY, moveY, moveY + RandomY, moveY, moveY - RandomY);
                //TextView
                ObjectAnimator text_moveX = ObjectAnimator.ofFloat(MainText, "translationX",
                        moveX - RandomX, moveX, moveX + RandomX, moveX, moveX - RandomX);
                ObjectAnimator text_moveY = ObjectAnimator.ofFloat(MainText, "translationY",
                        moveY - RandomY, moveY, moveY + RandomY, moveY, moveY - RandomY);
                //设置动画循环
                Image_moveX.setRepeatCount(-1);
                Image_moveY.setRepeatCount(-1);
                text_moveX.setRepeatCount(-1);
                text_moveY.setRepeatCount(-1);
                set.playTogether(
                        Image_moveX, Image_moveY,
                        text_moveX, text_moveY
                );
                set.setDuration(4000).start();
                //更换显示的图片和从中间到周围去的大圆的背景
                TextView tv_integral = mainTextImageMap.getTextView();
                tv_integral.setVisibility(View.VISIBLE);
                if (position != -1) {
//                    tv_integral.setText(myData[position]);
                }
                mMyDataBean.get(position).setClick(false);
                mSignDataBean.get(position).getTextView().setClickable(false);
                MainImage.setBackgroundResource(R.drawable.shape_circle_is_sign);
                mSignDataBean.get(position).getTextView().setText(mSignDataBean.get(position).getWeekDay());
                minorTextImageMap.getTv().setText("已签到");
                mainTextImageMap.getTextView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }


}
