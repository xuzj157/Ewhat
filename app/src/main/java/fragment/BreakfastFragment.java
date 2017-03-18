package fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import oteher.DbHelper;
import oteher.FoodInfo;
import personal.nekopalyer.ewhat.R;
import personal.nekopalyer.ewhat.RemoveRecyclerActivity;
import oteher.Tools;

import static oteher.Tools.intoList;


/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 * <p>
 * 本界面为早餐选择界面，嵌入MainActivity中
 */

public class BreakfastFragment extends Fragment {
    private Context context;
    private DbHelper dbHelper;
    private int screenWidth;             //字面意思
    private int screenHeight;

    //    String[] arrFood = {"皮蛋瘦肉粥", "油条", "豆浆", "大饼", "酱香饼", "山东煎饼", "小米粥",
//            "燕麦片", "菜包子", "肉包子", "馒头", "花卷", "肉夹馍", "小笼包", "生煎", "锅贴",
//            "烧卖", "黑米粥", "烧饼", "小馄饨", "粢饭团", "水果", "粢饭糕", "手抓饼", "汉堡",
//            "豆腐花", "糕点", "糯米团", "面包", "三明治", "饭团", "蛋饼","还是要吃早饭的！"};

    private ArrayList<String> mListFood = new ArrayList<>();   //用于存放食物名称
    private FrameLayout mMainLayoutFl;
    private TextView mShowTv;
    private ObjectAnimator Alpha;           //设置透明度时所使用的参数，可以不用看现在
    private FrameLayout.LayoutParams animatorTextParams;
    private Handler handler = new Handler();

    //设置一个线程，该线程将每隔80毫秒（0.08秒）执行一次
    //改变主内容框的内容，也就是在屏幕中不停改变的，并不是背景的淡入淡出效果
    Runnable runSetText = new Runnable() {
        @Override
        public void run() {
            mShowTv.setText(randomFood());
            handler.postDelayed(runSetText, 80);             //延迟80毫秒执行
        }
    };

    //淡入淡出效果

    Runnable runSetAnimationFirst = new Runnable() {
        @Override
        public void run() {
            TextView textView = new TextView(context);
            mMainLayoutFl.addView(textView);

            textView.setText(randomFood());
            animatorTextParams = (FrameLayout.LayoutParams) textView.getLayoutParams();         //用于设置产生位置的数据类型
            animatorTextParams.leftMargin = (Tools.ram(screenWidth, 0));
            animatorTextParams.topMargin = (Tools.ram(screenHeight, 0));
            textView.setTextSize(Tools.ram(33, 20));              //随机字体大小
            textView.setLayoutParams(animatorTextParams);         //设置产生的位置
            int time = Tools.ram(5000, 1000);                     //随机产生到消失的时间
            textView.setTextColor(Color.BLACK);                   //字体颜色
            textView.invalidate();                                  //比加
            Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);       //淡入淡出效果
//            scale = ObjectAnimator.ofFloat(textView,"scale",1,3,1,3);
            Alpha.setDuration(time - 1).start();              //发出指令开始效果
//            scale.setDuration(time - 1).start();
//            animatorSet = new AnimatorSet();
//            animatorSet.setDuration(time-1);
//            animatorSet.play(scale).with(Alpha);
//            animatorSet.start();
            handler.postDelayed(this, Tools.ram(300, 200));         //重复执行该线程
        }
    };
    //同上
    Runnable runSetAnimationSecond = new Runnable() {
        @Override
        public void run() {
            TextView textView = new TextView(context);
            mMainLayoutFl.addView(textView);
            textView.setText(randomFood());
            animatorTextParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            animatorTextParams.leftMargin = (Tools.ram(screenWidth, 0));
            animatorTextParams.topMargin = (Tools.ram(screenHeight, 0));
            textView.setTextSize(Tools.ram(33, 20));
            textView.setLayoutParams(animatorTextParams);
            int time = Tools.ram(4000, 2000);
            textView.setTextColor(Color.BLACK);
            textView.invalidate();
            Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);
            Alpha.setDuration(time - 1).start();
            handler.postDelayed(this, Tools.ram(200, 100));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        context = getContext();
        dbHelper = new DbHelper(context, "food.db3", 1);
        mMainLayoutFl = (FrameLayout) view.findViewById(R.id.id_main_layout_fl);
        mShowTv = (TextView) view.findViewById(R.id.id_show_tv);
        ToggleButton mCheckTb = (ToggleButton) view.findViewById(R.id.id_click_tb);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels - 7;
        screenWidth = dm.widthPixels - 50;

        //控制停止开始的开关的点击事件
        mCheckTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mListFood = intoList(FoodInfo.BREAKFAST, dbHelper);            //将数据库中早餐数据全部加入list
                    handler.post(runSetText);
                    handler.post(runSetAnimationFirst);
                    handler.post(runSetAnimationSecond);
                } else {
                    handler.removeCallbacks(runSetText);
                    handler.removeCallbacks(runSetAnimationFirst);
                    handler.removeCallbacks(runSetAnimationSecond);
                }
            }
        });

        //右上角设置按钮的点击事件
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle kind = new Bundle();
                kind.putSerializable("kind", FoodInfo.BREAKFAST);           //将数据包打包传递值 RemoveRecyclerActivity
                Intent intent = new Intent(context, RemoveRecyclerActivity.class);
                intent.putExtras(kind);
                startActivity(intent);                                      //打开一个新的activity界面
//                Toast.makeText(context, "此处为食物菜单设置功能！即将推出！\n敬请期待>_<", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private String randomFood() {
        return mListFood.get(Tools.ram(mListFood.size(), 0));
    }
}
