package personal.nekopalyer.ewhat;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by 智杰 on 2016/11/9.
 *
 */

public class BreakfastFragment extends Fragment {
    private Context context;
    private int screenWidth;
    private int screenHeight;
    String[] arrFood = {"皮蛋瘦肉粥", "油条", "豆浆", "大饼", "酱香饼", "山东煎饼", "小米粥",
            "燕麦片", "菜包子", "肉包子", "馒头", "花卷", "肉夹馍", "小笼包", "生煎", "锅贴",
            "烧卖", "黑米粥", "烧饼", "小馄饨", "粢饭团", "水果", "粢饭糕", "手抓饼", "汉堡",
            "豆腐花", "糕点", "糯米团", "面包", "三明治", "饭团", "蛋饼","还是要吃早饭的！"};
    private FrameLayout mMainLayoutFl;
    private TextView mShowTv;
    private ObjectAnimator Alpha;
    private FrameLayout.LayoutParams animatorTextParams;
    private Handler handler = new Handler();
    Runnable runSetText = new Runnable() {
        @Override
        public void run() {
            mShowTv.setText(randomFood());
            handler.postDelayed(runSetText, 80);
        }
    };

    Runnable runSetAnimationFirst = new Runnable() {
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
            int time = Tools.ram(5000, 1000);
            textView.invalidate();
            Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);
//            scale = ObjectAnimator.ofFloat(textView,"scale",1,3,1,3);
            Alpha.setDuration(time - 1).start();
//            scale.setDuration(time - 1).start();
//            animatorSet = new AnimatorSet();
//            animatorSet.setDuration(time-1);
//            animatorSet.play(scale).with(Alpha);
//            animatorSet.start();
            handler.postDelayed(this, Tools.ram(300, 200));
        }
    };

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
            textView.invalidate();
            Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);
            Alpha.setDuration(time - 1).start();
            handler.postDelayed(this, Tools.ram(200, 100));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        context = getContext();
        mMainLayoutFl = (FrameLayout) view.findViewById(R.id.id_main_layout_fl);
        mShowTv = (TextView) view.findViewById(R.id.id_show_tv);
        ToggleButton mCheckTb = (ToggleButton) view.findViewById(R.id.id_click_tb);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels - 7;
        screenWidth = dm.widthPixels - 50;
        mCheckTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,RemoveRecyclerActivity.class);
                startActivity(intent);

//                Toast.makeText(context, "此处为食物菜单设置功能！即将推出！\n敬请期待>_<", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private String randomFood() {
        return arrFood[Tools.ram(arrFood.length, 0)];
    }
}
